package com.qingbingyan.ajax.cross.domain.server.controllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingbingyan.ajax.cross.domain.server.controller.TestController;
import com.qingbingyan.ajax.cross.domain.server.coverter.MyMappingJacksonValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author bear
 * @Date 2020/9/5
 */
@RestControllerAdvice()
public class MyJsonpAdvice extends AbstractMappingJacksonResponseBodyAdvice {
    /**
     * Pattern for validating jsonp callback parameter values.
     */
    private static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_\\.]*");


    private final Log logger = LogFactory.getLog(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    private final String[] jsonpQueryParamNames = {"callback","callback1"};

    MyJsonpAdvice() {
    }


    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        for (String name : this.jsonpQueryParamNames) {
            String value = servletRequest.getParameter(name);
            if (value != null) {
                if (!isValidJsonpQueryParam(value)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Ignoring invalid jsonp parameter value: " + value);
                    }
                    continue;
                }
                MediaType contentTypeToUse = getContentType(contentType, request, response);
                response.getHeaders().setContentType(contentTypeToUse);
                Object bodyValue = bodyContainer.getValue();
                if(bodyValue instanceof String){
                    bodyContainer.setValue(value+"("+ bodyValue +");");
                }else if(bodyContainer instanceof MyMappingJacksonValue){
                    ((MyMappingJacksonValue)bodyContainer).setJsonpFunction(value);
                }
                break;
            }
        }
    }

    /**
     * Validate the jsonp query parameter value. The default implementation
     * returns true if it consists of digits, letters, or "_" and ".".
     * Invalid parameter values are ignored.
     * @param value the query param value, never {@code null}
     * @since 4.1.8
     */
    protected boolean isValidJsonpQueryParam(String value) {
        return CALLBACK_PARAM_PATTERN.matcher(value).matches();
    }

    /**
     * Return the content type to set the response to.
     * This implementation always returns "application/javascript".
     * @param contentType the content type selected through content negotiation
     * @param request the current request
     * @param response the current response
     * @return the content type to set the response to
     */
    protected MediaType getContentType(MediaType contentType, ServerHttpRequest request, ServerHttpResponse response) {
        return new MediaType("application", "javascript");
    }

    @Override
    protected MappingJacksonValue getOrCreateContainer(Object body) {
        return (body instanceof MyMappingJacksonValue ? (MyMappingJacksonValue) body : new MyMappingJacksonValue(body));
    }
}
