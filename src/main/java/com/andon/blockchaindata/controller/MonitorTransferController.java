package com.andon.blockchaindata.controller;

import com.andon.blockchaindata.domain.MonitorTransferRequest;
import com.andon.blockchaindata.service.MonitorTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MonitorTransferController {

    private static final Logger LOG = LoggerFactory.getLogger(MonitorTransferController.class);

    @Resource
    private MonitorTransferService monitorTransferService;

    @GetMapping(value = "/monitorContractFilter")
    public String MonitorContractFilter(MonitorTransferRequest monitorContractRequest) {
        LOG.info("MonitorTransferRequest : {}", monitorContractRequest);
        monitorTransferService.contractFilterOfStep(monitorContractRequest);
        return "SUCCESS";
    }
}
