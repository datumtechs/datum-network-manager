package com.platon.datum.admin.service.web3j;

import com.platon.contracts.ppos.StakingContract;
import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.service.function.ExceptionSupplier;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.*;
import com.platon.protocol.core.methods.response.PlatonBlock;
import com.platon.protocol.core.methods.response.Transaction;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class PlatONClient {

    @Resource
    private Web3jManager web3jManager;

    private AtomicReference<Web3j> web3jContainer;

    // 委托合约接口
    private StakingContract stakingContract;

    @PostConstruct
    public void init() {
        web3jContainer = web3jManager.subscribe(this);
        stakingContract = StakingContract.load(getWeb3j());
    }


    public synchronized Web3j getWeb3j() {
        return web3jContainer.get();
    }

    public BigInteger getAvgPackTime() {
        return queryPPOS(() -> stakingContract.getAvgPackTime());
    }

    public BigInteger platonGetBalance(String address) {
        return queryRpc(() -> getWeb3j().platonGetBalance(address, DefaultBlockParameterName.LATEST))
                .getBalance();
    }

    public BigInteger platonBlockNumber() {
        return queryRpc(() -> getWeb3j().platonBlockNumber())
                .getBlockNumber();
    }

    public PlatonBlock.Block platonGetBlockByNumber(BigInteger bn) {
        return queryRpc(() -> getWeb3j().platonGetBlockByNumber(DefaultBlockParameter.valueOf(bn), false))
                .getBlock();
    }

    public Optional<TransactionReceipt> platonGetTransactionReceipt(String hash) {
        return queryRpc(() -> getWeb3j().platonGetTransactionReceipt(hash))
                .getTransactionReceipt();
    }

    public Optional<Transaction> platonGetTransactionByHash(String hash) {
        return queryRpc(() -> getWeb3j().platonGetTransactionByHash(hash))
                .getTransaction();
    }

    private <R extends Response<?>> R queryRpc(ExceptionSupplier<Request<?, R>> supplier) {
        R response;
        try {
            response = supplier.get().send();
        } catch (SocketTimeoutException e) {
            throw new BizException(Errors.CallRpcReadTimeout, e);
        } catch (IOException e) {
            throw new BizException(Errors.CallRpcNetError, e);
        } catch (Exception e) {
            throw new BizException(Errors.CallRpcError, e);
        }

        if (response == null) {
            throw new ValidateException("response is null");
        }

        if (response.hasError()) {
            int rpcCode = response.getError().getCode();
            String rpcMessage = response.getError().getMessage();
            if (rpcCode == -32000) {
                if ("nonce too low".equalsIgnoreCase(rpcMessage)) {
                    throw new BizException(Errors.NonceTooLow, rpcMessage);
                } else if ("insufficient funds for gas * price + value".equalsIgnoreCase(rpcMessage)) {
                    throw new BizException(Errors.InsufficientWalletBalance, rpcMessage);
                } else if ("exceeds block gas limit".equalsIgnoreCase(rpcMessage)) {
                    throw new BizException(Errors.TxExceedsBlockGasLimit, rpcMessage);
                } else {
                    throw new BizException(Errors.TxKnownTx, rpcMessage);
                }
            }
            throw new BizException(Errors.CallRpcBizError, rpcMessage);
        }
        return response;
    }

    private <T> T queryPPOS(ExceptionSupplier<RemoteCall<CallResponse<T>>> supplier) {
        CallResponse<T> response;

        try {
            response = supplier.get().send();
        } catch (SocketTimeoutException e) {
            throw new BizException(Errors.CallRpcReadTimeout, e);
        } catch (IOException e) {
            throw new BizException(Errors.CallRpcNetError, e);
        } catch (Exception e) {
            throw new BizException(Errors.CallRpcError, e);
        }

        if (response == null) {
            throw new ValidateException("response is null");
        }

        if (!response.isStatusOk()) {
            throw new BizException(Errors.CallRpcBizError, response.getErrMsg());
        }
        return response.getData();
    }

}
