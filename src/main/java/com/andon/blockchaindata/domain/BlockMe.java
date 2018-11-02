package com.andon.blockchaindata.domain;

import org.web3j.protocol.core.methods.response.EthBlock;

public class BlockMe {

    public static String toStr(EthBlock.Block block){
        return "ethBlock{" +
            "number='" + block.getNumber() + '\'' +
                    ", hash='" + block.getHash() + '\'' +
                    ", parentHash='" + block.getParentHash() + '\'' +
                    ", nonce='" + block.getNonce() + '\'' +
                    ", sha3Uncles='" + block.getSha3Uncles() + '\'' +
                    ", logsBloom='" + block.getLogsBloom() + '\'' +
                    ", transactionsRoot='" + block.getTransactionsRoot() + '\'' +
                    ", stateRoot='" + block.getStateRoot() + '\'' +
                    ", receiptsRoot='" + block.getReceiptsRoot() + '\'' +
                    ", author='" + block.getAuthor() + '\'' +
                    ", miner='" + block.getMiner() + '\'' +
                    ", mixHash='" + block.getMixHash() + '\'' +
                    ", difficulty='" + block.getDifficulty() + '\'' +
                    ", totalDifficulty='" + block.getTotalDifficulty() + '\'' +
                    ", extraData='" + block.getExtraData() + '\'' +
                    ", size='" + block.getSize() + '\'' +
                    ", gasLimit='" + block.getGasLimit() + '\'' +
                    ", gasUsed='" + block.getGasUsed() + '\'' +
                    ", timestamp='" + block.getTimestamp() + '\'' +
                    ", transactions=" + block.getTransactions() +
                    ", uncles=" + block.getUncles() +
                    ", sealFields=" + block.getSealFields() +
                    '}';
    }
}
