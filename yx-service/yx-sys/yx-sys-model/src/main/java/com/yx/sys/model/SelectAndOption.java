package com.yx.sys.model;

import java.io.Serializable;

public class SelectAndOption implements Serializable {

    private Integer optionValue;
    private String optionDesc;


    public Integer getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(Integer optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionDesc() {
        return optionDesc;
    }

    public void setOptionDesc(String optionDesc) {
        this.optionDesc = optionDesc;
    }




}
