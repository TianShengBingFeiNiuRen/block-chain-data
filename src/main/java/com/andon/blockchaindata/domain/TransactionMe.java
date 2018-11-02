package com.andon.blockchaindata.domain;

import org.web3j.protocol.core.methods.response.Transaction;

public class TransactionMe {

    public static String toStr(Transaction transaction) {
        return "Transaction{" +
                "hash='" + transaction.getHash() + '\'' +
                ", nonce='" + transaction.getNonce() + '\'' +
                ", blockHash='" + transaction.getBlockHash() + '\'' +
                ", blockNumber='" + transaction.getBlockNumber() + '\'' +
                ", transactionIndex='" + transaction.getTransactionIndex() + '\'' +
                ", from='" + transaction.getFrom() + '\'' +
                ", to='" + transaction.getTo() + '\'' +
                ", value='" + transaction.getValue() + '\'' +
                ", gasPrice='" + transaction.getGasPrice() + '\'' +
                ", gas='" + transaction.getGas() + '\'' +
                ", input='" + transaction.getInput() + '\'' +
                ", creates='" + transaction.getCreates() + '\'' +
                ", publicKey='" + transaction.getPublicKey() + '\'' +
                ", raw='" + transaction.getRaw() + '\'' +
                ", r='" + transaction.getR() + '\'' +
                ", s='" + transaction.getS() + '\'' +
                ", v=" + transaction.getV() +
                '}';
    }
}
