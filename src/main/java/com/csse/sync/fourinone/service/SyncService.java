package com.csse.sync.fourinone.service;


public interface SyncService {
    /**
     * 同步组织结构或用户信息
     * @param url 同步url地址
     * @param synctime 组织机构信息的最后变更时间（指基础平台时间）。时间格式为yyyy-MM-dd HH:mm:ss，如果时间为""，则请求服务器返回所有部门信息。
     * @param type 1 表示拉取部门，2 表示拉取用户
     * @param start 起始记录编号，从 1 开始
     * @param count 要拉取的记录数量，0表示拉取从起始记录编号开始的所有记录
     * @return
     */
    String sync(String url, String synctime, String type, String start, String count) throws Exception;

}
