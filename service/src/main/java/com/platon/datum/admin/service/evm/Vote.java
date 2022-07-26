package com.platon.datum.admin.service.evm;

import com.platon.abi.solidity.EventEncoder;
import com.platon.abi.solidity.TypeReference;
import com.platon.abi.solidity.datatypes.*;
import com.platon.abi.solidity.datatypes.generated.Uint256;
import com.platon.abi.solidity.datatypes.generated.Uint8;
import com.platon.crypto.Credentials;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameter;
import com.platon.protocol.core.RemoteCall;
import com.platon.protocol.core.methods.request.PlatonFilter;
import com.platon.protocol.core.methods.response.Log;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import com.platon.tuples.generated.Tuple3;
import com.platon.tuples.generated.Tuple7;
import com.platon.tx.Contract;
import com.platon.tx.TransactionManager;
import com.platon.tx.gas.GasProvider;
import rx.Observable;
import rx.functions.Func1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://github.com/PlatONnetwork/client-sdk-java/releases">platon-web3j command line tools</a>,
 * or the com.platon.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/PlatONnetwork/client-sdk-java/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.1.0.0.
 */
public class Vote extends Contract {
    private static final String BINARY = "";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_CHANGERANGE = "changeRange";

    public static final String FUNC_SUBMITPROPOSAL = "submitProposal";

    public static final String FUNC_WITHDRAWPROPOSAL = "withdrawProposal";

    public static final String FUNC_VOTEPROPOSAL = "voteProposal";

    public static final String FUNC_EFFECTPROPOSAL = "effectProposal";

    public static final String FUNC_GETADMIN = "getAdmin";

    public static final String FUNC_GETALLAUTHORITY = "getAllAuthority";

    public static final String FUNC_GETALLPROPOSALID = "getAllProposalId";

    public static final String FUNC_GETPROPOSALID = "getProposalId";

    public static final String FUNC_GETPROPOSAL = "getProposal";

    public static final Event AUTHORITYADD_EVENT = new Event("AuthorityAdd",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUTHORITYDELETE_EVENT = new Event("AuthorityDelete",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event INITIALIZED_EVENT = new Event("Initialized",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
    ;

    public static final Event NEWPROPOSAL_EVENT = new Event("NewProposal",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint8>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PROPOSALRESULT_EVENT = new Event("ProposalResult",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event VOTEPROPOSAL_EVENT = new Event("VoteProposal",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}));
    ;

    public static final Event WITHDRAWPROPOSAL_EVENT = new Event("WithdrawProposal",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}));
    ;

    protected Vote(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected Vote(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AuthorityAddEventResponse> getAuthorityAddEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(AUTHORITYADD_EVENT, transactionReceipt);
        ArrayList<AuthorityAddEventResponse> responses = new ArrayList<AuthorityAddEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AuthorityAddEventResponse typedResponse = new AuthorityAddEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.serviceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.joinTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AuthorityAddEventResponse> authorityAddEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, AuthorityAddEventResponse>() {
            @Override
            public AuthorityAddEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(AUTHORITYADD_EVENT, log);
                AuthorityAddEventResponse typedResponse = new AuthorityAddEventResponse();
                typedResponse.log = log;
                typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.serviceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.joinTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AuthorityAddEventResponse> authorityAddEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUTHORITYADD_EVENT));
        return authorityAddEventObservable(filter);
    }

    public List<AuthorityDeleteEventResponse> getAuthorityDeleteEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(AUTHORITYDELETE_EVENT, transactionReceipt);
        ArrayList<AuthorityDeleteEventResponse> responses = new ArrayList<AuthorityDeleteEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AuthorityDeleteEventResponse typedResponse = new AuthorityDeleteEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.serviceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.joinTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AuthorityDeleteEventResponse> authorityDeleteEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, AuthorityDeleteEventResponse>() {
            @Override
            public AuthorityDeleteEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(AUTHORITYDELETE_EVENT, log);
                AuthorityDeleteEventResponse typedResponse = new AuthorityDeleteEventResponse();
                typedResponse.log = log;
                typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.serviceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.joinTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AuthorityDeleteEventResponse> authorityDeleteEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUTHORITYDELETE_EVENT));
        return authorityDeleteEventObservable(filter);
    }

    public List<InitializedEventResponse> getInitializedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
        ArrayList<InitializedEventResponse> responses = new ArrayList<InitializedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            InitializedEventResponse typedResponse = new InitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<InitializedEventResponse> initializedEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, InitializedEventResponse>() {
            @Override
            public InitializedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(INITIALIZED_EVENT, log);
                InitializedEventResponse typedResponse = new InitializedEventResponse();
                typedResponse.log = log;
                typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<InitializedEventResponse> initializedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INITIALIZED_EVENT));
        return initializedEventObservable(filter);
    }

    public List<NewProposalEventResponse> getNewProposalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPROPOSAL_EVENT, transactionReceipt);
        ArrayList<NewProposalEventResponse> responses = new ArrayList<NewProposalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            NewProposalEventResponse typedResponse = new NewProposalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proposalType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.submitter = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.candidate = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.candidateServiceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.proposalUrl = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.submitBlockNo = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NewProposalEventResponse> newProposalEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, NewProposalEventResponse>() {
            @Override
            public NewProposalEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(NEWPROPOSAL_EVENT, log);
                NewProposalEventResponse typedResponse = new NewProposalEventResponse();
                typedResponse.log = log;
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.proposalType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.submitter = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.candidate = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.candidateServiceUrl = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.proposalUrl = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.submitBlockNo = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<NewProposalEventResponse> newProposalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPROPOSAL_EVENT));
        return newProposalEventObservable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventObservable(filter);
    }

    public List<ProposalResultEventResponse> getProposalResultEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(PROPOSALRESULT_EVENT, transactionReceipt);
        ArrayList<ProposalResultEventResponse> responses = new ArrayList<ProposalResultEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ProposalResultEventResponse typedResponse = new ProposalResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.result = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ProposalResultEventResponse> proposalResultEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, ProposalResultEventResponse>() {
            @Override
            public ProposalResultEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PROPOSALRESULT_EVENT, log);
                ProposalResultEventResponse typedResponse = new ProposalResultEventResponse();
                typedResponse.log = log;
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.result = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ProposalResultEventResponse> proposalResultEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROPOSALRESULT_EVENT));
        return proposalResultEventObservable(filter);
    }

    public List<VoteProposalEventResponse> getVoteProposalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(VOTEPROPOSAL_EVENT, transactionReceipt);
        ArrayList<VoteProposalEventResponse> responses = new ArrayList<VoteProposalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            VoteProposalEventResponse typedResponse = new VoteProposalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.voter = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VoteProposalEventResponse> voteProposalEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, VoteProposalEventResponse>() {
            @Override
            public VoteProposalEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(VOTEPROPOSAL_EVENT, log);
                VoteProposalEventResponse typedResponse = new VoteProposalEventResponse();
                typedResponse.log = log;
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.voter = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<VoteProposalEventResponse> voteProposalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTEPROPOSAL_EVENT));
        return voteProposalEventObservable(filter);
    }

    public List<WithdrawProposalEventResponse> getWithdrawProposalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWPROPOSAL_EVENT, transactionReceipt);
        ArrayList<WithdrawProposalEventResponse> responses = new ArrayList<WithdrawProposalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WithdrawProposalEventResponse typedResponse = new WithdrawProposalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.blockNo = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawProposalEventResponse> withdrawProposalEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, WithdrawProposalEventResponse>() {
            @Override
            public WithdrawProposalEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWPROPOSAL_EVENT, log);
                WithdrawProposalEventResponse typedResponse = new WithdrawProposalEventResponse();
                typedResponse.log = log;
                typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.blockNo = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<WithdrawProposalEventResponse> withdrawProposalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWPROPOSAL_EVENT));
        return withdrawProposalEventObservable(filter);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> initialize(String adminAddress, String serviceUrl) {
        final Function function = new Function(
                FUNC_INITIALIZE,
                Arrays.<Type>asList(new Address(adminAddress),
                new Utf8String(serviceUrl)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeRange(BigInteger flag, BigInteger range) {
        final Function function = new Function(
                FUNC_CHANGERANGE,
                Arrays.<Type>asList(new Uint8(flag),
                new Uint256(range)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> submitProposal(BigInteger proposalType, String proposalUrl, String candidate, String candidateServiceUrl) {
        final Function function = new Function(
                FUNC_SUBMITPROPOSAL,
                Arrays.<Type>asList(new Uint8(proposalType),
                new Utf8String(proposalUrl),
                new Address(candidate),
                new Utf8String(candidateServiceUrl)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawProposal(BigInteger proposalId) {
        final Function function = new Function(
                FUNC_WITHDRAWPROPOSAL,
                Arrays.<Type>asList(new Uint256(proposalId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> voteProposal(BigInteger proposalId) {
        final Function function = new Function(
                FUNC_VOTEPROPOSAL,
                Arrays.<Type>asList(new Uint256(proposalId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> effectProposal(BigInteger proposalId) {
        final Function function = new Function(
                FUNC_EFFECTPROPOSAL,
                Arrays.<Type>asList(new Uint256(proposalId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> getAdmin() {
        final Function function = new Function(FUNC_GETADMIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<Tuple3<List<String>, List<String>, List<BigInteger>>> getAllAuthority() {
        final Function function = new Function(FUNC_GETALLAUTHORITY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<Tuple3<List<String>, List<String>, List<BigInteger>>>(
                new Callable<Tuple3<List<String>, List<String>, List<BigInteger>>>() {
                    @Override
                    public Tuple3<List<String>, List<String>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<String>, List<String>, List<BigInteger>>(
                                convertToNative((List<Address>) results.get(0).getValue()),
                                convertToNative((List<Utf8String>) results.get(1).getValue()),
                                convertToNative((List<Uint256>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteCall<List> getAllProposalId() {
        final Function function = new Function(FUNC_GETALLPROPOSALID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<List> getProposalId(BigInteger blockNo) {
        final Function function = new Function(FUNC_GETPROPOSALID,
                Arrays.<Type>asList(new Uint256(blockNo)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<Tuple7<BigInteger, String, String, String, String, BigInteger, List<String>>> getProposal(BigInteger proposalId) {
        final Function function = new Function(FUNC_GETPROPOSAL,
                Arrays.<Type>asList(new Uint256(proposalId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<Tuple7<BigInteger, String, String, String, String, BigInteger, List<String>>>(
                new Callable<Tuple7<BigInteger, String, String, String, String, BigInteger, List<String>>>() {
                    @Override
                    public Tuple7<BigInteger, String, String, String, String, BigInteger, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, String, String, String, BigInteger, List<String>>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue(),
                                (String) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                convertToNative((List<Address>) results.get(6).getValue()));
                    }
                });
    }

    public static RemoteCall<Vote> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        return deployRemoteCall(Vote.class, web3j, credentials, contractGasProvider, BINARY,  "");
    }

    public static RemoteCall<Vote> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        return deployRemoteCall(Vote.class, web3j, transactionManager, contractGasProvider, BINARY,  "");
    }

    public static Vote load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        return new Vote(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Vote load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        return new Vote(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class AuthorityAddEventResponse {
        public Log log;

        public String addr;

        public String serviceUrl;

        public BigInteger joinTime;
    }

    public static class AuthorityDeleteEventResponse {
        public Log log;

        public String addr;

        public String serviceUrl;

        public BigInteger joinTime;
    }

    public static class InitializedEventResponse {
        public Log log;

        public BigInteger version;
    }

    public static class NewProposalEventResponse {
        public Log log;

        public BigInteger proposalId;

        public BigInteger proposalType;

        public String submitter;

        public String candidate;

        public String candidateServiceUrl;

        public String proposalUrl;

        public BigInteger submitBlockNo;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }

    public static class ProposalResultEventResponse {
        public Log log;

        public BigInteger proposalId;

        public Boolean result;
    }

    public static class VoteProposalEventResponse {
        public Log log;

        public BigInteger proposalId;

        public String voter;
    }

    public static class WithdrawProposalEventResponse {
        public Log log;

        public BigInteger proposalId;

        public BigInteger blockNo;
    }
}
