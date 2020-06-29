package com.example.demo.controller;


import com.example.demo.service.ESLogService;
import com.example.demo.service.impl.ESLogServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/commmon")
@Validated
@RestController
public class ESLogController {
    private Log log = LogFactory.getLog(ESLogController.class);

    @Resource
    ESLogService esLogService;

    @GetMapping("/testlog")
    public ResponseEntity ForwardByEAMEquipmentCode(String equipment_code){
        log.info(equipment_code+"controller打印信息哈哈哈");
        return ResponseEntity.ok(esLogService.getTest(equipment_code));
    }
}
