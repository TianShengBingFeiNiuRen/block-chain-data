package com.andon.blockchaindata.domain;

import org.web3j.protocol.core.methods.response.Log;

public class LogMe {

    public static String toStr(Log log) {
        return "Log{" +
                ", logIndex='" + log.getLogIndex() + '\'' +
                ", transactionIndex='" + log.getTransactionIndex() + '\'' +
                ", transactionHash='" + log.getTransactionHash() + '\'' +
                ", blockHash='" + log.getBlockHash() + '\'' +
                ", blockNumber='" + log.getBlockNumber() + '\'' +
                ", address='" + log.getAddress() + '\'' +
                ", data='" + log.getData() + '\'' +
                ", type='" + log.getType() + '\'' +
                ", topics=" + log.getTopics() +
                '}';
    }
}
