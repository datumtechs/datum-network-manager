package com.platon.datum.admin.service.evm;

import com.platon.abi.solidity.EventEncoder;
import com.platon.abi.solidity.FunctionEncoder;
import com.platon.abi.solidity.TypeReference;
import com.platon.abi.solidity.datatypes.Address;
import com.platon.abi.solidity.datatypes.Event;
import com.platon.abi.solidity.datatypes.Function;
import com.platon.abi.solidity.datatypes.Type;
import com.platon.abi.solidity.datatypes.Utf8String;
import com.platon.abi.solidity.datatypes.generated.Uint8;
import com.platon.crypto.Credentials;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameter;
import com.platon.protocol.core.RemoteCall;
import com.platon.protocol.core.methods.request.PlatonFilter;
import com.platon.protocol.core.methods.response.Log;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import com.platon.tx.Contract;
import com.platon.tx.TransactionManager;
import com.platon.tx.gas.GasProvider;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://github.com/PlatONnetwork/client-sdk-java/releases">platon-web3j command line tools</a>,
 * or the com.platon.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/PlatONnetwork/client-sdk-java/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.1.0.0.
 */
public class ERC721Factory extends Contract {
    private static final String BINARY = "";

    public static final String FUNC_DEPLOYERC721CONTRACT = "deployERC721Contract";

    public static final Event INSTANCEDEPLOYED_EVENT = new Event("InstanceDeployed",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event NFTCONTRACTCREATED_EVENT = new Event("NFTContractCreated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}));
    ;

    protected ERC721Factory(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected ERC721Factory(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ERC721Factory> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, String _template) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_template)));
        return deployRemoteCall(ERC721Factory.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ERC721Factory> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, String _template) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_template)));
        return deployRemoteCall(ERC721Factory.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    public List<InstanceDeployedEventResponse> getInstanceDeployedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(INSTANCEDEPLOYED_EVENT, transactionReceipt);
        ArrayList<InstanceDeployedEventResponse> responses = new ArrayList<InstanceDeployedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            InstanceDeployedEventResponse typedResponse = new InstanceDeployedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.instance = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<InstanceDeployedEventResponse> instanceDeployedEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, InstanceDeployedEventResponse>() {
            @Override
            public InstanceDeployedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(INSTANCEDEPLOYED_EVENT, log);
                InstanceDeployedEventResponse typedResponse = new InstanceDeployedEventResponse();
                typedResponse.log = log;
                typedResponse.instance = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<InstanceDeployedEventResponse> instanceDeployedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INSTANCEDEPLOYED_EVENT));
        return instanceDeployedEventObservable(filter);
    }

    public List<NFTContractCreatedEventResponse> getNFTContractCreatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(NFTCONTRACTCREATED_EVENT, transactionReceipt);
        ArrayList<NFTContractCreatedEventResponse> responses = new ArrayList<NFTContractCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            NFTContractCreatedEventResponse typedResponse = new NFTContractCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.templateAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.admin = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.symbol = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.proof = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.cipherFlag = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NFTContractCreatedEventResponse> nFTContractCreatedEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, NFTContractCreatedEventResponse>() {
            @Override
            public NFTContractCreatedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(NFTCONTRACTCREATED_EVENT, log);
                NFTContractCreatedEventResponse typedResponse = new NFTContractCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.newTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.templateAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.admin = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.symbol = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.proof = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.cipherFlag = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<NFTContractCreatedEventResponse> nFTContractCreatedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NFTCONTRACTCREATED_EVENT));
        return nFTContractCreatedEventObservable(filter);
    }

    public RemoteCall<TransactionReceipt> deployERC721Contract(String name, String symbol, String proof, BigInteger cipherFlag) {
        final Function function = new Function(
                FUNC_DEPLOYERC721CONTRACT,
                Arrays.<Type>asList(new Utf8String(name),
                new Utf8String(symbol),
                new Utf8String(proof),
                new Uint8(cipherFlag)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static ERC721Factory load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        return new ERC721Factory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ERC721Factory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        return new ERC721Factory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class InstanceDeployedEventResponse {
        public Log log;

        public String instance;
    }

    public static class NFTContractCreatedEventResponse {
        public Log log;

        public String newTokenAddress;

        public String templateAddress;

        public String admin;

        public String name;

        public String symbol;

        public String proof;

        public BigInteger cipherFlag;
    }
}
