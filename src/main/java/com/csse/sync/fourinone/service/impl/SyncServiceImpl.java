package com.csse.sync.fourinone.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csse.sync.fourinone.service.SyncService;
import com.csse.sync.fourinone.utils.HttpUtils;
import com.csse.sync.fourinone.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有调用http请求的方法，在httpUtils里都用调用的例子，在类的最下方
 */
@Service
public class SyncServiceImpl implements SyncService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${tokenUrl}")
    private String tokenUrl;
    @Value("${baseUrl}")
    private String BASEURL;
    @Value("${client_id}")
    private String CLIENT_ID;
    @Value("${client_secret}")
    private String CLIENT_SECRET;

    @Override
    public String sync(String url, String synctime, String type, String start, String count) throws Exception {
        logger.info("同步机构或人员信息");
        JSONObject request = new JSONObject();
        request.put("access_token",getToken());
        request.put("synctime",synctime);
        request.put("type",type);
        request.put("start",start);
        request.put("count",count);
        String result = HttpUtils.doPost(url,request.toString(),"application/json;charset=utf8");
        logger.info("返回数据报文:"+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        if("0".equals(resultJson.getString("status"))){
            logger.info("获取数据成功");
            JSONObject dataJson = JSONObject.parseObject(resultJson.getString("data"));
            logger.info("获取的数据数量："+dataJson.getString("total"));
            JSONArray infoArray = JSONArray.parseArray(dataJson.getString("info"));
            //同步机构数据
            if("1".equals(type)){
                logger.info("同步部门数据");
                for (int i=0;i<infoArray.size();i++){
                    JSONObject departJson = JSONObject.parseObject((String)infoArray.get(i));

                    JSONObject requestJson = new JSONObject();
                    requestJson.put("code",departJson.getString("id"));
                    requestJson.put("fatherId",departJson.getString("pid"));
                    requestJson.put("organName",departJson.getString("name"));

                    Map<String,String> extAttribute = new HashMap<String,String>();
                    extAttribute.put("EXT_DESC",departJson.getString("desc"));
                    if("0".equals(departJson.getString("isorg"))){
                        extAttribute.put("EXT_ISORG","单位");
                    }else if ("1".equals(departJson.getString("isorg"))){
                        extAttribute.put("EXT_ISORG","非单位");
                    }
                    if("0".equals(departJson.getString("status"))){
                        extAttribute.put("EXT_STATUS","启用");
                    }else if ("1".equals(departJson.getString("status"))){
                        extAttribute.put("EXT_STATUS","禁用");
                    }else if ("2".equals(departJson.getString("status"))){
                        extAttribute.put("EXT_STATUS","删除");
                    }
                    extAttribute.put("EXT_UNITCODE",departJson.getString("unitcode"));

                    requestJson.put("extAttribute",extAttribute);
                    requestJson.put("organId",departJson.getString("id"));
                    requestJson.put("isTemporary",0);
                    /*requestJson.put("startDate","");
                    requestJson.put("endDate","");*/
                    String departInfo = getDepartment(departJson.getString("id"));
                    //如果部门存在则更新部门，如果部门不存在则新增部门
                    if("success".equals(departInfo)){
                        logger.info("部门存在，开始更新部门");
                        String rtnUpdate = updateDepartment(departJson.getString("id"),requestJson.toString());
                        logger.info("更新部门结果："+rtnUpdate);
                    }else{
                        logger.info("部门不存在，开始创建部门");
                        String rtnAdd = addDepartment(requestJson.toString());
                        logger.info("创建部门结果："+rtnAdd);
                    }
                }
                //同步用户数据
            }else if("2".equals(type)){
                logger.info("同步用户数据");
                for (int i=0;i<infoArray.size();i++){
                    JSONObject userJson = JSONObject.parseObject((String)infoArray.get(i));

                    JSONObject requestJson = new JSONObject();
                    //必输
                    requestJson.put("userid",userJson.getString("id"));
                    requestJson.put("account",userJson.getString("pwduser"));
                    //加密证书
                    requestJson.put("ca",userJson.getString("enccert"));
                    requestJson.put("fullname",userJson.getString("name"));
                    if("user".equals(userJson.getString("role"))){
                        requestJson.put("isManager",0);
                    }else if ("audit".equals(userJson.getString("role"))){
                        requestJson.put("isManager",1);
                    }else if("sec".equals(userJson.getString("role"))){
                        requestJson.put("isManager",2);
                    }else if("sys".equals(userJson.getString("role"))){
                        requestJson.put("isManager",4);
                    }
                    requestJson.put("organId",userJson.getString("did"));
                    requestJson.put("password","rXEKCz8LyZaeRar4RDG64QJn576Jyc98Y5wrpptbvp8=");
                    if("4".equals(userJson.getString("mlevel"))||"5".equals(userJson.getString("mlevel"))){
                        requestJson.put("secLevel","-1");
                    }else if ("3".equals(userJson.getString("mlevel"))){
                        requestJson.put("secLevel","1");
                    }else if ("2".equals(userJson.getString("mlevel"))){
                        requestJson.put("secLevel","2");
                    }else if ("1".equals(userJson.getString("mlevel"))){
                        requestJson.put("secLevel","3");
                    }
                    if ("0".equals(userJson.getString("sex"))){
                        requestJson.put("sex","1");
                    }else if ("1".equals(userJson.getString("sex"))){
                        requestJson.put("sex","0");
                    }
                    List<String> organids = new ArrayList<String>();
                    organids.add(userJson.getString("did"));
                    requestJson.put("organIds",organids);

                    //非必输
                    requestJson.put("duty",userJson.getString("post"));
                    requestJson.put("mobile",userJson.getString("telephone"));
                    requestJson.put("userEmail","");
                    requestJson.put("idString",userJson.getString("militarynum"));
                    // requestJson.put("endDate",departJson.getString("id"));
                   // requestJson.put("ip",departJson.getString("pid"));
                   // requestJson.put("position",departJson.getString("id"));
                   // requestJson.put("startDate",departJson.getString("id"));
                  //  requestJson.put("tel",departJson.getString("pid"));

                   // requestJson.put("userType",departJson.getString("pid"));
                   // requestJson.put("address",departJson.getString("pid"));
                   // requestJson.put("manageOrgan",departJson.getString("pid"));

                    Map<String,String> extAttribute = new HashMap<String,String>();
                    extAttribute.put("EXT_KEYUSER",userJson.getString("keyuser"));
                    extAttribute.put("EXT_SIGNCERT",userJson.getString("signcert"));
                    extAttribute.put("EXT_CERTLEVEL",userJson.getString("certlevel"));

                    if("0".equals(userJson.getString("status"))){
                        extAttribute.put("EXT_STATUS","启用");
                    }else if ("1".equals(userJson.getString("status"))){
                        extAttribute.put("EXT_STATUS","禁用");
                    }else if ("2".equals(userJson.getString("status"))){
                        extAttribute.put("EXT_STATUS","删除");
                    }

                    extAttribute.put("EXT_UNITCODE2",userJson.getString("unitcode"));

                    requestJson.put("extAttribute",extAttribute);
                    //更新人员信息，没有则新增
                    String updateUser = updateUser(userJson.getString("pwduser"),requestJson.toString());

                    if("success".equals(updateUser)){
                        logger.info("更新用户信息成功");
                    }else{
                        logger.info("更新用户信息失败");
                    }
                }
            }

        }else{
            logger.info("获取数据失败："+resultJson.getString("msg"));
            return "同步组织机构和用户信息失败："+resultJson.getString("msg");
        }

        return "success";
    }

    /**
     * 获取基础平台token
     * @return
     */
    public String getToken(){
        logger.info("获取基础平台token");
        String rootPath = System.getProperty("user.dir");
        String token = "";
        String result = "";
        try{

            String app_id = PropertiesUtil.getProperties(rootPath+"/deploy.properties","app_id");
            String app_secret = PropertiesUtil.getProperties(rootPath+"/deploy.properties","app_secret");

            JSONObject request = new JSONObject();
            request.put("app_id",app_id);
            request.put("app_secret",app_secret);
            request.put("grant_typ","desktop");
            //{"status" : "0","msg" : "成功","data" : {"access_token":"5C097DE0-F38E-4802-900C-B54DF1EB2B59"}}
            // String result = "{\"status\" : \"0\",\"msg\" : \"成功\",\"data\" : {\"access_token\":\"5C097DE0-F38E-4802-900C-B54DF1EB2B59\"}}";
            result = HttpUtils.doPost(tokenUrl,request.toString(),"application/json;charset=utf8");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取token出错："+e.getMessage());
            return "";
        }

        JSONObject resJson = JSONObject.parseObject(result);
        if("0".equals(resJson.getString("status"))){
            logger.info("token获取成功");
            JSONObject dataJson = JSONObject.parseObject(resJson.getString("data"));
            token = dataJson.getString("access_token");
            logger.info("token:"+token);
        }else{
            logger.info("token获取失败");
            return "";
        }

        return token;
    }
    /**
     * 获取办公平台token
     * @return
     */
    /**
     * 获取微服务access_token
     **/
    public String getAccessToken() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(BASEURL);    //(SOFT_IP_PORT_URL);
        sb.append("/api/uaa/oauth/token?client_id=");
        sb.append(CLIENT_ID);
        sb.append("&client_secret=");
        sb.append(CLIENT_SECRET);
        sb.append("&grant_type=client_credentials");
        String contentType = "application/json";

        String url = sb.toString();
        String token = HttpUtils.doPost(url, "", contentType);
        //String token = HttpUtil.postRequest(url, "post");
        JSONObject jsonObj = JSONObject.parseObject(token);
        String key = "access_token";
        String access_token;
        if (token != null && token.length() > 0) {
            access_token = jsonObj.getString(key);
        } else {
            access_token = "获取微服务token失败！";
        }

        return access_token;
    }

    /**
     * 创建部门
     * @param params 请求参数报文
     * @return
     * @throws Exception
     */
    public String addDepartment(String params) throws Exception {
        logger.info("增加部门");
        StringBuffer sb = new StringBuffer();
        sb.append(BASEURL);
        sb.append("/api/org/department?access_token=");
        sb.append(getAccessToken());

        String result = HttpUtils.doPost(sb.toString(),params,"application/json;charset=utf8");
        logger.info("创建部门返回报文："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        if("0".equals(resultJson.getString("rsltcode"))){
            logger.info("创建部门成功");
        }else {
            logger.info("创建部门失败："+resultJson.getString("rsltmsg"));
            return "fail";
        }
        return "success";
    }
    /**
     * 获取部门详情
     * @param departId 部门id
     * @return
     * @throws Exception
     */
    public String getDepartment(String departId) throws Exception {
        logger.info("获取部门详情");
        StringBuffer sb = new StringBuffer();
        sb.append(BASEURL);
        sb.append("/api/org/department/");
        sb.append(departId);
        sb.append("?access_token=");
        sb.append(getAccessToken());

        String result = HttpUtils.doGet(sb.toString(),"","application/json;charset=utf8");
        logger.info("获取部门详情返回报文："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        if(!"".equals(resultJson.getString("rsltcode"))&&resultJson.getString("rsltcode")!=null){
            logger.info("存在部门");
        }else {
            logger.info("部门不存在："+resultJson.getString("rsltmsg"));
            return "fail";
        }
        return "success";
    }
    /**
     * 更新部门
     * @param params 请求参数报文
     * @return
     * @throws Exception
     */
    public String updateDepartment(String departId, String params) throws Exception {
        logger.info("更新部门");
        StringBuffer sb = new StringBuffer();
        sb.append(BASEURL);
        sb.append("/api/org/department/");
        sb.append(departId);
        sb.append("?access_token=");
        sb.append(getAccessToken());
        Map<String,String> haderMap = new HashMap<String,String>();
        haderMap.put("Content-Type","application/json;charset=utf8");
        String result = HttpUtils.doPut(sb.toString(),haderMap,params);
        logger.info("更新部门返回报文："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        if("0".equals(resultJson.getString("rsltcode"))){
            logger.info("更新部门成功");
        }else {
            logger.info("更新部门失败："+resultJson.getString("rsltmsg"));
            return "fail";
        }
        return "success";
    }
    /**
     * 创建人员
     * @param params 请求参数报文
     * @return
     * @throws Exception
     */
    public String addUser(String params) throws Exception {
        logger.info("创建人员");
        StringBuffer sb = new StringBuffer();
        sb.append(BASEURL);
        sb.append("/api/org/userinfo?access_token=");
        sb.append(getAccessToken());

        String result = HttpUtils.doPost(sb.toString(),params,"application/json;charset=utf8");
        logger.info("创建人员返回报文："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        if("0".equals(resultJson.getString("rsltcode"))){
            logger.info("创建人员成功");
        }else {
            logger.info("创建人员失败："+resultJson.getString("rsltmsg"));
            return "fail";
        }
        return "success";
    }
    /**
     * 获取人员详情
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    public String getUser(String userId) throws Exception {
        logger.info("获取人员详情");
        StringBuffer sb = new StringBuffer();
        sb.append(BASEURL);
        sb.append("/api/org/userinfo/");
        sb.append(userId);
        sb.append("?access_token=");
        sb.append(getAccessToken());

        String result = HttpUtils.doGet(sb.toString(),"","application/json;charset=utf8");
        logger.info("获取部门详情返回报文："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        if(!"".equals(resultJson.getString("rsltcode"))&&resultJson.getString("rsltcode")!=null){
            logger.info("存在部门");
        }else {
            logger.info("部门不存在："+resultJson.getString("rsltmsg"));
            return "fail";
        }
        return "success";
    }
    /**
     * 更新人员信息
     * @param account 用户账号
     * @param params 请求参数报文
     * @return
     * @throws Exception
     */
    public String updateUser(String account, String params) throws Exception {
        logger.info("更新人员信息");
        StringBuffer sb = new StringBuffer();
        sb.append(BASEURL);
        sb.append("/api/org/userinfoext/");
        sb.append(account);
        sb.append("?access_token=");
        sb.append(getAccessToken());
        Map<String,String> haderMap = new HashMap<String,String>();
        haderMap.put("Content-Type","application/json;charset=utf8");
        String result = HttpUtils.doPut(sb.toString(),haderMap,params);
        logger.info("更新人员返回报文："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        if("0".equals(resultJson.getString("rsltcode"))){
            logger.info("更新人员成功");
        }else {
            logger.info("更新人员失败："+resultJson.getString("rsltmsg"));
            return "fail";
        }
        return "success";
    }

    public static void main(String[] args) {

    }

}
