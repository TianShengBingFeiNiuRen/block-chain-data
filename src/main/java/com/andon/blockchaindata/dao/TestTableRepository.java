package com.andon.blockchaindata.dao;

import com.andon.blockchaindata.domain.TestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTableRepository extends JpaRepository<TestTable, Integer> {

    TestTable findByIdAndStatus(Integer id, String status);
}
