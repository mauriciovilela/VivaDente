package com.odonto.converter;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectMapperContextResolver() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.disable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

}