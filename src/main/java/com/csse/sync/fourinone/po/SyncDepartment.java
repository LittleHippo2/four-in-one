package com.csse.sync.fourinone.po;

import java.io.Serializable;

public class SyncDepartment implements Serializable {

    //部门ID，不超过 64 字节
    private String id;
    //部门名称，不超过 64 字节
    private String name;
    //上级部门
    private String pid;
    //部门描述，不超过 256 字节
    private String desc;
    //是否为单位，0 表示不是单位，1 表示该部门具有单位属性
    private String isorg;
    //0 启用 1 禁用 2 删除
    private String status;
    //单位保密编码，16 字节
    private String unitcode;

    //无参构造方法
    public SyncDepartment() {
    }

    //全参构造方法
    public SyncDepartment(String id, String name, String pid, String desc, String isorg, String status, String unitcode) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.desc = desc;
        this.isorg = isorg;
        this.status = status;
        this.unitcode = unitcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIsorg() {
        return isorg;
    }

    public void setIsorg(String isorg) {
        this.isorg = isorg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }
}
