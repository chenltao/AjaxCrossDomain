package com.qingbingyan.ajax.cross.domain.server.controller;

import com.qingbingyan.ajax.cross.domain.server.bean.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author bear
 * @Date 2020/8/27
 */
@Slf4j
@Controller
//@RequestMapping("/test")
public class TestController {

    @GetMapping("/get1")
    @ResponseBody
    private ResultBean get1() {
        log.info("TestController.get1()");
        return new ResultBean("get1 ok");
    }

    @GetMapping("/getCookie")
    @ResponseBody
    private ResultBean getCookie(@CookieValue(value = "myCookie")String myCookie) {
        log.info("TestController.getCookie()");
        return new ResultBean("get1 "+myCookie);
    }

    @GetMapping("/getProxyIframe")
    private String getProxyIframe() {
        log.info("TestController.getProxyIframe()");
        return "proxyIframe";
    }
}
