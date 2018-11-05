package com.andon.blockchaindata.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "test_table")
public class TestTable implements Serializable {

    @Id
    private Integer id;

    @Column
    private String status;

    public TestTable() {
    }

    public TestTable(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TestTable{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
