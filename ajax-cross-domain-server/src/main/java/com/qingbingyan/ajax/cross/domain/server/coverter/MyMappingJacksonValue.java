package com.qingbingyan.ajax.cross.domain.server.coverter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;

/**
 * @author bear
 * @Date 2020/9/6
 */
public class MyMappingJacksonValue extends MappingJacksonValue {
    /**
     * Create a new instance wrapping the given POJO to be serialized.
     *
     * @param value the Object to be serialized
     */
    public MyMappingJacksonValue(Object value) {
        super(value);
        this.setValue(value);
    }

    private Object value;

    private Class<?> serializationView;

    private FilterProvider filters;

    private String jsonpFunction;


    /**
     * Modify the POJO to serialize.
     */
    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Return the POJO that needs to be serialized.
     */
    @Override
    public Object getValue() {
        return this.value;
    }

    /**
     * Set the serialization view to serialize the POJO with.
     * @see com.fasterxml.jackson.databind.ObjectMapper#writerWithView(Class)
     * @see com.fasterxml.jackson.annotation.JsonView
     */
    @Override
    public void setSerializationView(Class<?> serializationView) {
        this.serializationView = serializationView;
    }

    /**
     * Return the serialization view to use.
     * @see com.fasterxml.jackson.databind.ObjectMapper#writerWithView(Class)
     * @see com.fasterxml.jackson.annotation.JsonView
     */
    @Override
    public Class<?> getSerializationView() {
        return this.serializationView;
    }

    /**
     * Set the Jackson filter provider to serialize the POJO with.
     * @since 4.2
     * @see com.fasterxml.jackson.databind.ObjectMapper#writer(FilterProvider)
     * @see com.fasterxml.jackson.annotation.JsonFilter
     * @see Jackson2ObjectMapperBuilder#filters(FilterProvider)
     */
    @Override
    public void setFilters(FilterProvider filters) {
        this.filters = filters;
    }

    /**
     * Return the Jackson filter provider to use.
     * @since 4.2
     * @see com.fasterxml.jackson.databind.ObjectMapper#writer(FilterProvider)
     * @see com.fasterxml.jackson.annotation.JsonFilter
     */
    @Override
    public FilterProvider getFilters() {
        return this.filters;
    }

    /**
     * Set the name of the JSONP function name.
     */
    public void setJsonpFunction(String functionName) {
        this.jsonpFunction = functionName;
    }

    /**
     * Return the configured JSONP function name.
     */
    public String getJsonpFunction() {
        return this.jsonpFunction;
    }

}
