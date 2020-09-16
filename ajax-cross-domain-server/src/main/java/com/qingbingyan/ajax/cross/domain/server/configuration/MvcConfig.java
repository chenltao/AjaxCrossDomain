package com.qingbingyan.ajax.cross.domain.server.configuration;

import com.qingbingyan.ajax.cross.domain.server.coverter.Jackson2HttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * @author bear
 */
@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final Jackson2HttpMessageConverter converter;

    public MvcConfig(Jackson2HttpMessageConverter converter) {
        this.converter = converter;
    }
/*

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");

        viewResolver.setSuffix(".html");
        registry.viewResolver(viewResolver);

    }
*/

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converterList) {
        log.info("converterList size:{}", converterList.size());
        LinkedList<MediaType> mediaTypes = new LinkedList<>();
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converterList.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converterList.add(converter);
    }
}