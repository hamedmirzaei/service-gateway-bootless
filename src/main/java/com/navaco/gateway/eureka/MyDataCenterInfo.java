package com.navaco.gateway.eureka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyDataCenterInfo implements DataCenterInfo {

    private Name name;

    @JsonCreator
    public MyDataCenterInfo(@JsonProperty("name") Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }


}
