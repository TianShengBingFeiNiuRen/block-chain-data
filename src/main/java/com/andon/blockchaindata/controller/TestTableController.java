package com.andon.blockchaindata.controller;

import com.andon.blockchaindata.dao.TestTableRepository;
import com.andon.blockchaindata.domain.TestTable;
import com.andon.blockchaindata.util.DomainResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestTableController {

    private static final Logger LOG = LoggerFactory.getLogger(TestTableRepository.class);

    @Resource
    private TestTableRepository testTableRepository;

    @GetMapping(value = "/testTable/testTable")
    public DomainResponse<TestTable> getAll(){
        List<TestTable> testTables = null;
        try {
            testTables = testTableRepository.findAll();
            LOG.info("TestTables = {}", testTables);
            return new DomainResponse<>("1", "success", testTables);
        } catch (Exception e) {
            LOG.error("TestTableRepository getAll failure, error = {}", e.getMessage());
            return new DomainResponse<>("-1", e.getMessage(), testTables);
        }
    }
}
