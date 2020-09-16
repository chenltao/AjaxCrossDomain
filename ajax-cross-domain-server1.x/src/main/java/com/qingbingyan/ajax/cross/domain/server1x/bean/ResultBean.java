package com.qingbingyan.ajax.cross.domain.server1x.bean;

import lombok.Data;

/**
 * @author bear
 * @Date 2020/8/27
 */
@Data
public class ResultBean {
    private String data;

    public ResultBean(String data) {
        this.data = data;
    }


}
