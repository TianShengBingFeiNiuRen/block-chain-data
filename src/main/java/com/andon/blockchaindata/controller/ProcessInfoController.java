package com.andon.blockchaindata.controller;

import com.andon.blockchaindata.util.ProcessInfoBean;
import com.andon.blockchaindata.util.ProcessInfoTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessInfoController.class);

    @GetMapping(value = "/processInfo/processInfo")
    public String processInfo(){
        try {
            ProcessInfoBean processInfoBean = ProcessInfoTools.getProcessInfoBean();
            return processInfoBean.toString();
        } catch (Exception e) {
            LOG.error("ProcessInfoBean get failure", e.getMessage());
            return "ERROR";
        }
    }
}
