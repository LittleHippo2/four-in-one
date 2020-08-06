package com.csse.sync.fourinone.po;

import java.io.Serializable;

public class SyncUser implements Serializable{

    //用户 id，不超过 64 字节
    private String id;
    //用户登录账号，不超过 64 字节
    private String pwduser;
    //用户姓名，不超过 64 字节
    private String name;
    //部门 id
    private String did;
    //sys 系统管理员 sec 安全管理员 audit 审计管理员 user 普通用户
    private String role;
    //智能卡账号，不超过 64 字节
    private String keyuser;
    //签名证书（PEM 格式），不超过 2048 字节
    private String signcert;
    //加密证书（PEM 格式），不超过 2048 字节
    private String enccert;
    //证书级别，1 一级证书 2 二级证书 3 三级证书
    private String certlevel;
    //密级 1 绝密 2 机密 3 秘密 4 内部 5 公开
    private String mlevel;
    //性别 0 女 1 男
    private String sex;
    //职务，不超过 64 字节
    private String post;
    //军官证号，不超过 64 字节
    private String militarynum;
    //电话，不超过 16 字节
    private String telephone;
    //状态 0 启用 1 禁用 2 删除
    private String status;
    //单位保密编码，16 字节
    private String unitcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwduser() {
        return pwduser;
    }

    public void setPwduser(String pwduser) {
        this.pwduser = pwduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getKeyuser() {
        return keyuser;
    }

    public void setKeyuser(String keyuser) {
        this.keyuser = keyuser;
    }

    public String getSigncert() {
        return signcert;
    }

    public void setSigncert(String signcert) {
        this.signcert = signcert;
    }

    public String getEnccert() {
        return enccert;
    }

    public void setEnccert(String enccert) {
        this.enccert = enccert;
    }

    public String getCertlevel() {
        return certlevel;
    }

    public void setCertlevel(String certlevel) {
        this.certlevel = certlevel;
    }

    public String getMlevel() {
        return mlevel;
    }

    public void setMlevel(String mlevel) {
        this.mlevel = mlevel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMilitarynum() {
        return militarynum;
    }

    public void setMilitarynum(String militarynum) {
        this.militarynum = militarynum;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
