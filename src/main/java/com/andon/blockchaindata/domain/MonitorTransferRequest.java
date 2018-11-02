package com.andon.blockchaindata.domain;

import java.io.Serializable;

public class MonitorTransferRequest implements Serializable {

    private String contractAddress; //合约地址

    private String firstBlock; //区块高度

    public MonitorTransferRequest() {
    }

    public MonitorTransferRequest(String contractAddress, String firstBlock) {
        this.contractAddress = contractAddress;
        this.firstBlock = firstBlock;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(String firstBlock) {
        this.firstBlock = firstBlock;
    }

    @Override
    public String toString() {
        return "MonitorTransferRequest{" +
                "contractAddress='" + contractAddress + '\'' +
                ", firstBlock='" + firstBlock + '\'' +
                '}';
    }
}
