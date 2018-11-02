package com.andon.blockchaindata.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferRecord implements Serializable {

    private String fromAddress;

    private String toAddress;

    private BigDecimal value;

    private String timeStamp;

    public TransferRecord() {
    }

    public TransferRecord(String fromAddress, String toAddress, BigDecimal value, String timeStamp) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.value = value;
        this.timeStamp = timeStamp;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "TransferRecord{" +
                "fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", value=" + value +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
