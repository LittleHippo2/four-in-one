package com.csse.sync.fourinone.po;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CsseUser implements Serializable{

    //人员id
    private String userid;
    //目录服务认证授权中心
    private String ca;
    //职务
    private String duty;
    //用户有效期截止时间
    private String endDate;
    //额外属性
    private Map<String, Object> extAttribute;
    //全名
    private String fullname;
    //手机
    private String mobile;
    //管理员类型（0：普通用户；1：安全审计员；2：安全管理员；4：系统管理员）
    private Integer isManager;
    //IP
    private String ip;
    //部门ID
    private String organId;
    //职级
    private String position;
    //密级（-1：非涉密；1：一般；2：重要；3：核心）
    private String secLevel;
    //性别（0：男；1：女）
    private String sex;
    //用户有效期开始时间
    private String startDate;
    //座机
    private String tel;
    //邮箱
    private String userEmail;
    //证件编号
    private String idstring;
    //用户类别
    private String userType;
    //单位地址
    private String address;
    //用户机构关系机构id集合
    private List<String> organIds;
    //用户的权限集合
    private List<String> manageOrgan;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Map<String, Object> getExtAttribute() {
        return extAttribute;
    }

    public void setExtAttribute(Map<String, Object> extAttribute) {
        this.extAttribute = extAttribute;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getIsManager() {
        return isManager;
    }

    public void setIsManager(Integer isManager) {
        this.isManager = isManager;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSecLevel() {
        return secLevel;
    }

    public void setSecLevel(String secLevel) {
        this.secLevel = secLevel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getIdstring() {
        return idstring;
    }

    public void setIdstring(String idstring) {
        this.idstring = idstring;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getOrganIds() {
        return organIds;
    }

    public void setOrganIds(List<String> organIds) {
        this.organIds = organIds;
    }

    public List<String> getManageOrgan() {
        return manageOrgan;
    }

    public void setManageOrgan(List<String> manageOrgan) {
        this.manageOrgan = manageOrgan;
    }
}
