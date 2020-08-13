package com.csse.sync.fourinone.controller;

import com.alibaba.fastjson.JSONObject;
import com.csse.sync.fourinone.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/")
public class SyncController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${deptusersyncUrl}")
    private String deptusersyncUrl;
    @Resource
    private SyncService syncService;

    @PostMapping("restapi/deptusernotify")
    @ResponseBody
    public String deptusernotify(@RequestParam(value="type")String type) throws Exception {
        logger.info("接收到组织机构变更请求：");
        String timeStr1= LocalDateTime.now().plusMinutes(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("同步时间："+timeStr1);

        if ("1".equals(type)){
            logger.info("同步机构信息。");
            //同步机构信息
            String departRes = syncService.sync(deptusersyncUrl,timeStr1,"1","1","0");
            logger.info("机构同步结果："+departRes);
        }else if ("1".equals(type)){
            logger.info("同步用户信息。");
            //同步用户信息
            String userRes = syncService.sync(deptusersyncUrl,timeStr1,"2","1","0");
            logger.info("用户同步结果："+userRes);
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("status","0");
        rtnJson.put("msg","成功");
        rtnJson.put("data",new JSONObject());
        return rtnJson.toString();
    }


}
