package com.example.demo.service.impl;

import com.example.demo.commene.MyException;
import com.example.demo.service.ESLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


@Service
public class ESLogServiceImpl implements ESLogService {
    private Log log = LogFactory.getLog(ESLogServiceImpl.class);
    @Override
    public String getTest(String string) {
        log.info(string+"service层打印日志!!!!!!!!");
        return string;
    }

    @Override
    public String getEx(String code) {
        throw new MyException("101", code);

    }
}
