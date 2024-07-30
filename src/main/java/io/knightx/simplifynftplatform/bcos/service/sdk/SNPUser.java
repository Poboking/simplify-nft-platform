package io.knightx.simplifynftplatform.bcos.service.sdk;

import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.*;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class SNPUser extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506106a6806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063704f1b941461007257806385f42e34146100db578063b7a4982d14610164578063b8b2da4f146101d1578063bf40fac11461027a575b600080fd5b34801561007e57600080fd5b506100d9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610323565b005b3480156100e757600080fd5b50610162600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610330565b005b34801561017057600080fd5b5061018f60048036038101908080359060200190929190505050610439565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101dd57600080fd5b50610238600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061046c565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561028657600080fd5b506102e1600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610550565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61032d8133610330565b50565b600061033b83610599565b90508160008083815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff16836040518082805190602001908083835b6020831015156103db57805182526020820191506020810190506020830392506103b6565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207f1e59c52994c66d721689438dd975601abb818ca4ab4caff05b27a26922cf59e060405160405180910390a3505050565b60006020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600081426040516020018083805190602001908083835b6020831015156104a85780518252602082019150602081019050602083039250610483565b6001836020036101000a038019825116818451168082178552505050505050905001828152602001925050506040516020818303038152906040526040518082805190602001908083835b60208310151561051857805182526020820191506020810190506020830392506104f3565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600190049050919050565b60008061055c83610599565b905060008082815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050919050565b600080826040516020018082805190602001908083835b6020831015156105d557805182526020820191506020810190506020830392506105b0565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310151561063e5780518252602082019150602081019050602083039250610619565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020905080600190049150509190505600a165627a7a72305820567cd8cfddc856110e987508cd0d294b9e41e332db6a527c16e1faf20aeb70ae0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506106a6806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806307afbf3a146100725780632106b7891461011b57806322f26c74146101a457806360de7ce71461024d578063a8f635be146102ba575b600080fd5b34801561007e57600080fd5b506100d9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610323565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561012757600080fd5b506101a2600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061036c565b005b3480156101b057600080fd5b5061020b600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610475565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561025957600080fd5b5061027860048036038101908080359060200190929190505050610559565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102c657600080fd5b50610321600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061058c565b005b60008061032f83610599565b905060008082815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050919050565b600061037783610599565b90508160008083815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff16836040518082805190602001908083835b60208310151561041757805182526020820191506020810190506020830392506103f2565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207faaac1ce3cb985ec805d086426537e2c6d829c31370d86c27ec62e5d262b9d35260405160405180910390a3505050565b600081426040516020018083805190602001908083835b6020831015156104b1578051825260208201915060208101905060208303925061048c565b6001836020036101000a038019825116818451168082178552505050505050905001828152602001925050506040516020818303038152906040526040518082805190602001908083835b60208310151561052157805182526020820191506020810190506020830392506104fc565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600190049050919050565b60006020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b610596813361036c565b50565b600080826040516020018082805190602001908083835b6020831015156105d557805182526020820191506020810190506020830392506105b0565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310151561063e5780518252602082019150602081019050602083039250610619565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020905080600190049150509190505600a165627a7a72305820dfed359ae485a39b091de68e776a84baf0bb8313b8f489dc4dda2c3fb33425c30029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"registerUser\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"defaultRegisterUser\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"nameToAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"getRandomAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"getAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"name\",\"type\":\"string\"},{\"indexed\":true,\"name\":\"account\",\"type\":\"address\"}],\"name\":\"register\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_REGISTERUSER = "registerUser";

    public static final String FUNC_DEFAULTREGISTERUSER = "defaultRegisterUser";

    public static final String FUNC_NAMETOADDRESS = "nameToAddress";

    public static final String FUNC_GETRANDOMADDRESS = "getRandomAddress";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final Event REGISTER_EVENT = new Event("register", 
            Arrays.asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Address>(true) {}));

    protected SNPUser(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt registerUser(String name) {
        final Function function = new Function(
                FUNC_REGISTERUSER,
                List.of(new Utf8String(name)), 
                Collections.emptyList());
        return executeTransaction(function);
    }

    public byte[] registerUser(String name, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERUSER,
                List.of(new Utf8String(name)), 
                Collections.emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegisterUser(String name) {
        final Function function = new Function(
                FUNC_REGISTERUSER,
                List.of(new Utf8String(name)), 
                Collections.emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getRegisterUserInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTERUSER,
                List.of(),
                List.of(new TypeReference<Utf8String>() {
                }));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public TransactionReceipt defaultRegisterUser(String name, String account) {
        final Function function = new Function(
                FUNC_DEFAULTREGISTERUSER, 
                Arrays.asList(new Utf8String(name), 
                new Address(account)), 
                Collections.emptyList());
        return executeTransaction(function);
    }

    public byte[] defaultRegisterUser(String name, String account, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_DEFAULTREGISTERUSER, 
                Arrays.asList(new Utf8String(name), 
                new Address(account)), 
                Collections.emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForDefaultRegisterUser(String name, String account) {
        final Function function = new Function(
                FUNC_DEFAULTREGISTERUSER, 
                Arrays.asList(new Utf8String(name), 
                new Address(account)), 
                Collections.emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getDefaultRegisterUserInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_DEFAULTREGISTERUSER,
                List.of(), 
                Arrays.asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public String nameToAddress(BigInteger param0) throws ContractException {
        final Function function = new Function(FUNC_NAMETOADDRESS,
                List.of(new Uint256(param0)),
                List.of(new TypeReference<Address>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String getRandomAddress(String name) throws ContractException {
        final Function function = new Function(FUNC_GETRANDOMADDRESS,
                List.of(new Utf8String(name)),
                List.of(new TypeReference<Address>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String getAddress(String name) throws ContractException {
        final Function function = new Function(FUNC_GETADDRESS,
                List.of(new Utf8String(name)),
                List.of(new TypeReference<Address>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public List<RegisterEventResponse> getRegisterEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTER_EVENT, transactionReceipt);
        ArrayList<RegisterEventResponse> responses = new ArrayList<RegisterEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RegisterEventResponse typedResponse = new RegisterEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeRegisterEvent(String fromBlock, String toBlock, List<String> otherTopics,
            EventCallback callback) {
        String topic0 = eventEncoder.encode(REGISTER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeRegisterEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(REGISTER_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static SNPUser load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new SNPUser(contractAddress, client, credential);
    }

    public static SNPUser deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(SNPUser.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class RegisterEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] name;

        public String account;
    }
}
