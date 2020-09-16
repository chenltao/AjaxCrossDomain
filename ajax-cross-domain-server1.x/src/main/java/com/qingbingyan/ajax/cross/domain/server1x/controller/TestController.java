package com.qingbingyan.ajax.cross.domain.server1x.controller;

import com.qingbingyan.ajax.cross.domain.server1x.bean.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bear
 * @Date 2020/8/27
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get1")
    private ResultBean get1() {
        log.info("TestController.get1()");
        return new ResultBean("get1 ok");
    }
}
