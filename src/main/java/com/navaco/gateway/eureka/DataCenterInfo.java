package com.navaco.gateway.eureka;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

@JsonRootName("dataCenterInfo")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonTypeIdResolver(DataCenterTypeInfoResolver.class)
public interface DataCenterInfo {

    enum Name {Netflix, Amazon, MyOwn}

    Name getName();
}
