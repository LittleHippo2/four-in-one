package com.csse.sync.fourinone.po;

import java.util.Map;

public class CsseDepartment {

    //部门简拼
    private String code;
    //上级部门ID
    private String fatherId;
    //部门名称
    private String organName;
    //额外属性
    private Map<String, Object> extAttribute;
    //组织机构ID。若指定，则按照指定id创建部门；若不指定则由平台统一生成36位唯一id
    private String organId;
    //是否为临时 0为否，1为是   默认0
    private Integer isTemporary;
    //部门开始时间
    private String startDate;
    //结束时间
    private String endDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Map<String, Object> getExtAttribute() {
        return extAttribute;
    }

    public void setExtAttribute(Map<String, Object> extAttribute) {
        this.extAttribute = extAttribute;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Integer getIsTemporary() {
        return isTemporary;
    }

    public void setIsTemporary(Integer isTemporary) {
        this.isTemporary = isTemporary;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
