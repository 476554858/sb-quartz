package com.zjx.sbquartz.entity;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_cron")
public class CronEntity {

    private Integer id;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
