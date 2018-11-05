package com.andon.blockchaindata.util;

import java.io.Serializable;
import java.util.List;

public class DomainResponse<T> implements Serializable {

    private String code;
    private String message;
    private List<T> list;

    public DomainResponse() {
    }

    public DomainResponse(String code, String message, List<T> list) {
        this.code = code;
        this.message = message;
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public void seatCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DomainResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", list=" + list +
                '}';
    }
}
