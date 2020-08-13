package com.csse.sync.fourinone.initialization;

import com.alibaba.fastjson.JSONObject;
import com.csse.sync.fourinone.service.SyncService;
import com.csse.sync.fourinone.utils.HttpUtils;
import com.csse.sync.fourinone.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Initialization implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${sysName}")
    private String sysName;
    @Value("${syncUrl}")
    private String syncUrl;
    @Value("${registerUrl}")
    private String registerUrl;
    @Value("${deptusersyncUrl}")
    private String deptusersyncUrl;

    @Resource
    private SyncService syncService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目启动，注册应用，然后将注册返回的app_id和app_sercet写入本地配置文件");
        //注册应用
        String rootPath = System.getProperty("user.dir");
        //String classPath = ResourceUtils.getURL("classpath:").getPath();
        String deployPath = rootPath+"/deploy.properties";
        JSONObject paramObj = new JSONObject();
        paramObj.put("sysName",sysName);
        paramObj.put("syncUrl",syncUrl);
        //String result = HttpUtils.doPost(registerUrl,paramObj.toString(),"application/json;charset=utf8");
        String result ="{\"status\" : \"0\",\"msg\" : \"成功\",\"data\" : {\"app_id\" : \"6000000713866655\",\"app_secret\": \"Jbhp6iBJ\"}}";
        logger.info("应用注册返回报文："+result);
        JSONObject resJson = JSONObject.parseObject(result);
        if("0".equals(resJson.getString("status"))){
            logger.info("应用注册成功");
            JSONObject data = JSONObject.parseObject(resJson.getString("data"));

            Map<String,String> param = new HashMap<String,String>();

            param.put("app_id",data.getString("app_id"));
            param.put("app_secret",data.getString("app_secret"));

            PropertiesUtil.outputFile(deployPath,param);
        }else {
            logger.info("应用注册失败："+resJson.get("msg"));
        }
        logger.info("开始同步机构和人员信息");

        //同步机构信息
        String departRes = syncService.sync(deptusersyncUrl,"","1","1","0");
        logger.info("组织机构同步结果："+departRes);
        if("success".equals(departRes)){
            //同步用户信息
            String userRes =  syncService.sync(deptusersyncUrl,"","0","1","0");
            logger.info("同步用户信息结果："+userRes);
        }else{
            logger.info("机构同步失败，不继续同步人员信息");
        }

    }

}
