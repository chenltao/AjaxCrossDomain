package com.qingbingyan.ajax.cross.domain.server.controllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bear
 * @Date 2020/8/29
 */
//@RestControllerAdvice(basePackageClasses = TestController.class)
@Slf4j
public class JsonpAdvice implements ResponseBodyAdvice {
    private ObjectMapper mapper = new ObjectMapper();
    private String callback = "callback1";
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        log.info("class:{}",aClass);
        return true;
    }

    /**
     *
     * 在此处对返回值进行处理，需要特别注意如果是非String类型，会被Json序列化，从而添加了双引号，解决办法见
     * @param body        返回值
     * @param methodParameter  方法参数
     * @param mediaType     当前contentType，非String类型为json
     * @param aClass       convert的class
     * @param serverHttpRequest request，暂时支持是ServletServerHttpRequest类型，其余类型将会原样返回
     * @param serverHttpResponse response
     * @return 如果body是String类型，加上方法头后返回，如果是其他类型，序列化后返回
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body == null){
            return null;
        }

        // 如果返回String类型，media是plain，否则是json，将会经过json序列化，在下方返回纯字符串之后依然会被序列化，就会添上多余的双引号
        log.info("body={},request={},response={},media={}", body, serverHttpRequest, serverHttpResponse, mediaType.getSubtype());


        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();

            String callback = request.getParameter(this.callback);

            if (!StringUtils.isEmpty(callback)) {
                //使用了jsonp
                if (body instanceof String) {
                    return callback + "(" + body + ")";
                } else {
                    try {
                        String res = this.mapper.writeValueAsString(body);
                        log.info("转化后的返回值={},{}", res, callback + "(" + res + ")");
                        log.info("serverHttpResponse:{}",serverHttpResponse.getClass());
                        return callback + "(" + res + ")";
                    } catch (JsonProcessingException e) {
                        log.warn("【jsonp支持】数据body序列化失败", e);
                        return body;
                    }
                }
            }
        } else {
            log.warn("【jsonp支持】不支持的request class ={}", serverHttpRequest.getClass());
        }
        return body;
    }
}
