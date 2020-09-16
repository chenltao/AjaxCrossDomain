package com.qingbingyan.ajax.cross.domain.server1x.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * @author bear
 * @Date 2020/9/5
 */
@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    private String callback = "callback";

    public JsonpAdvice() {
        super("callback","callback1");
    }
}
