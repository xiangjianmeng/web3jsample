import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;


public class Sample {

    static public void main(String[] args) throws Exception {
        // Connect Web3j to the Blockchain
        String rpcEndpoint = "https://exchaintestrpc.okex.org";
        Web3j web3j = Web3j.build(new HttpService(rpcEndpoint));

        // Prepare a wallet
        String pk = "0x5bbbef76458bf30511c9ee6ed56783644eb339258d02656755c68098c4809130";
        // Account address: 0x1583c05d6304b6651a7d9d723a5c32830f53a12f
        Credentials credentials = Credentials.create(pk);

        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials, Byte.valueOf(web3j.netVersion().send().getNetVersion()));

        // Load the contract
        String contractAddress = "0xe579156f9decc4134b5e3a30a24ac46bb8b01281";
        ERC20 erc20 = ERC20.load(contractAddress, web3j, txManager, new DefaultGasProvider());


        String symbol = erc20.symbol().send();
        String name = erc20.name().send();
        BigInteger decimal = erc20.decimals().send();

        System.out.println("symbol: " + symbol);
        System.out.println("name: " + name);
        System.out.println("decimal: " + decimal.intValueExact());

        // transfer
        TransactionReceipt receipt = erc20.transfer("0x0db6b797e64666d4b36b13e5dc6fcd4661893ac8", new BigInteger("25")).send();
        System.out.println("Transaction hash: "+receipt.getTransactionHash());

        BigInteger balance1 = erc20.balanceOf("0x1583c05d6304b6651a7d9d723a5c32830f53a12f").send();
        System.out.println("balance (0x1583c05d6304b6651a7d9d723a5c32830f53a12f)="+balance1.toString());

        BigInteger balance2 = erc20.balanceOf("0x0db6b797e64666d4b36b13e5dc6fcd4661893ac8").send();
        System.out.println("balance (0x0db6b797e64666d4b36b13e5dc6fcd4661893ac8)="+balance2.toString());

        // approve
        TransactionReceipt receipt1 = erc20.approve("0x0db6b797e64666d4b36b13e5dc6fcd4661893ac8", new BigInteger("100000")).send();
        System.out.println("Transaction hash: "+receipt1.getTransactionHash());

        BigInteger approveAmt = erc20.allowance("0x1583c05d6304b6651a7d9d723a5c32830f53a12f","0x0db6b797e64666d4b36b13e5dc6fcd4661893ac8").send();
        System.out.println("allowance (0x0db6b797e64666d4b36b13e5dc6fcd4661893ac8)="+approveAmt.toString());
    }
}
