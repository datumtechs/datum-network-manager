package com.platon.datum.admin.service.impl;

import com.platon.abi.solidity.EventEncoder;
import com.platon.abi.solidity.EventValues;
import com.platon.bech32.Bech32;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.AddressChangeUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.service.VoteContract;
import com.platon.datum.admin.service.evm.Vote;
import com.platon.datum.admin.service.function.ExceptionFunction;
import com.platon.datum.admin.service.web3j.Web3jManager;
import com.platon.protocol.core.DefaultBlockParameter;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.RemoteCall;
import com.platon.protocol.core.methods.request.PlatonFilter;
import com.platon.protocol.core.methods.response.Log;
import com.platon.tuples.generated.Tuple2;
import com.platon.tuples.generated.Tuple3;
import com.platon.tx.Contract;
import com.platon.tx.ReadonlyTransactionManager;
import com.platon.tx.gas.ContractGasProvider;
import org.springframework.stereotype.Component;
import rx.Observable;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class VoteContractImpl implements VoteContract {

    @Resource
    private Web3jManager web3jManager;
//    private VoteConfigDto voteConfigDto;

    public final static String newProposalSignature = EventEncoder.encode(Vote.NEWPROPOSAL_EVENT);
    public final static String proposalResultSignature = EventEncoder.encode(Vote.PROPOSALRESULT_EVENT);
    public final static String voteProposalSignature = EventEncoder.encode(Vote.VOTEPROPOSAL_EVENT);
    public final static String withdrawProposalSignature = EventEncoder.encode(Vote.WITHDRAWPROPOSAL_EVENT);
    public final static String authorityDeleteSignature = EventEncoder.encode(Vote.AUTHORITYDELETE_EVENT);
    public final static String authorityAddSignature = EventEncoder.encode(Vote.AUTHORITYADD_EVENT);

    @Override
    public void init() {
        BigInteger rangeBeginVote = query(contract -> contract.getInterval(BigInteger.valueOf(1)), "platONProperties.getVoteAddress()", Optional.empty());
        BigInteger rangeVote = query(contract -> contract.getInterval(BigInteger.valueOf(2)), "platONProperties.getVoteAddress()", Optional.empty());
        BigInteger rangeQuit = query(contract -> contract.getInterval(BigInteger.valueOf(4)), "platONProperties.getVoteAddress()", Optional.empty());
//        voteConfigDto = new VoteConfigDto();
//        voteConfigDto.setBeginVote(rangeBeginVote);
//        voteConfigDto.setVote(rangeVote);
//        voteConfigDto.setQuit(rangeQuit);
    }

//    @Override
//    public VoteConfigDto getConfig() {
//        return voteConfigDto;
//    }

    @Override
    public List<Authority> getAllAuthority() {
        Tuple3<List<String>, List<String>, List<BigInteger>> result = query(contract -> contract.getAllAuthority(), "platONProperties.getVoteAddress()", Optional.empty());
        List<Authority> resultList = new ArrayList<>();
        for (int i = 0; i < result.getValue1().size(); i++) {
            Authority authorityDto = new Authority();
            authorityDto.setIdentityId(Bech32.addressDecodeHex(result.getValue1().get(i)));
            authorityDto.setUrl(result.getValue2().get(i));
            authorityDto.setJoinTime(LocalDateTimeUtil.getLocalDateTime(result.getValue3().get(i).longValue()));
            resultList.add(authorityDto);
        }
        return resultList;
    }

    @Override
    public Observable<Optional<Tuple2<Log, Object>>> subscribe(BigInteger beginBN) {
        PlatonFilter filter = new PlatonFilter(DefaultBlockParameter.valueOf(beginBN), DefaultBlockParameterName.LATEST, "platONProperties.getVoteAddress()");
        filter.addOptionalTopics(newProposalSignature, proposalResultSignature, voteProposalSignature, withdrawProposalSignature, authorityAddSignature, authorityDeleteSignature);
        return web3jManager.getWeb3j().platonLogObservable(filter).map(log -> {
            List<String> topics = log.getTopics();
            if (topics == null || topics.size() == 0) {
                return Optional.empty();
            }
            if (topics.get(0).equals(newProposalSignature)) {
                EventValues eventValues = Contract.staticExtractEventParameters(Vote.NEWPROPOSAL_EVENT, log);
                Vote.NewProposalEventResponse typedResponse = new Vote.NewProposalEventResponse();
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.proposalType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.submitter = Bech32.addressDecodeHex((String) eventValues.getIndexedValues().get(2).getValue());
                typedResponse.candidate = Bech32.addressDecodeHex((String) eventValues.getNonIndexedValues().get(0).getValue());
                typedResponse.candidateServiceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.proposalUrl = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.submitBlockNo = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return Optional.of(new Tuple2<>(log, typedResponse));
            } else if (topics.get(0).equals(proposalResultSignature)) {
                EventValues eventValues = Contract.staticExtractEventParameters(Vote.PROPOSALRESULT_EVENT, log);
                Vote.ProposalResultEventResponse typedResponse = new Vote.ProposalResultEventResponse();
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.result = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return Optional.of(new Tuple2<>(log, typedResponse));
            } else if (topics.get(0).equals(voteProposalSignature)) {
                EventValues eventValues = Contract.staticExtractEventParameters(Vote.VOTEPROPOSAL_EVENT, log);
                Vote.VoteProposalEventResponse typedResponse = new Vote.VoteProposalEventResponse();
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.voter = Bech32.addressDecodeHex((String) eventValues.getNonIndexedValues().get(0).getValue());
                return Optional.of(new Tuple2<>(log, typedResponse));
            } else if (topics.get(0).equals(withdrawProposalSignature)) {
                EventValues eventValues = Contract.staticExtractEventParameters(Vote.WITHDRAWPROPOSAL_EVENT, log);
                Vote.WithdrawProposalEventResponse typedResponse = new Vote.WithdrawProposalEventResponse();
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.blockNo = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return Optional.of(new Tuple2<>(log, typedResponse));
            } else if (topics.get(0).equals(authorityDeleteSignature)) {
                EventValues eventValues = Contract.staticExtractEventParameters(Vote.AUTHORITYDELETE_EVENT, log);
                Vote.AuthorityDeleteEventResponse typedResponse = new Vote.AuthorityDeleteEventResponse();
                typedResponse.addr = Bech32.addressDecodeHex((String) eventValues.getNonIndexedValues().get(0).getValue());
                typedResponse.serviceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.joinTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return Optional.of(new Tuple2<>(log, typedResponse));
            } else if (topics.get(0).equals(authorityAddSignature)) {
                EventValues eventValues = Contract.staticExtractEventParameters(Vote.AUTHORITYADD_EVENT, log);
                Vote.AuthorityAddEventResponse typedResponse = new Vote.AuthorityAddEventResponse();
                typedResponse.addr = Bech32.addressDecodeHex((String) eventValues.getNonIndexedValues().get(0).getValue());
                typedResponse.serviceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.joinTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return Optional.of(new Tuple2<>(log, typedResponse));
            } else {
                return Optional.empty();
            }
        });
    }

    @Override
    public Integer sizeOfAllAuthority(BigInteger bigInteger) {
        Tuple3<List<String>, List<String>, List<BigInteger>> result = query(contract -> contract.getAllAuthority(), "platONProperties.getVoteAddress()", Optional.of(bigInteger));
        return result.getValue1().size();
    }

    private <R> R query(ExceptionFunction<Vote, RemoteCall<R>> supplier, String contractAddress, Optional<BigInteger> queryBlockNumber) {
        contractAddress = AddressChangeUtil.hexToBech32(contractAddress);
        ReadonlyTransactionManager transactionManager = new ReadonlyTransactionManager(web3jManager.getWeb3j(), contractAddress);
        try {
            Vote dataTokenTemplate = Vote.load(contractAddress, web3jManager.getWeb3j(), transactionManager, new ContractGasProvider(BigInteger.ZERO, BigInteger.ZERO));
            queryBlockNumber.ifPresent(bigInteger -> {
                dataTokenTemplate.setDefaultBlockParameter(DefaultBlockParameter.valueOf(bigInteger));
            });
            return supplier.apply(dataTokenTemplate).send();
        } catch (SocketTimeoutException e) {
            throw new BizException(Errors.CALL_RPC_READ_TIMEOUT, e);
        } catch (IOException e) {
            throw new BizException(Errors.CALL_RPC_NET_ERROR, e);
        } catch (Exception e) {
            throw new BizException(Errors.CALL_RPC_ERROR, e);
        }
    }
}
