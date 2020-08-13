package com.csse.sync.fourinone.timing;

import com.csse.sync.fourinone.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimingService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${deptusersyncUrl}")
    private String deptusersyncUrl;
    @Resource
    private SyncService syncService;

    @Scheduled(cron = "0 0/2 * * * *")
    public void timedTasks() throws Exception {
//        syncService.test("");
        logger.info("定时同步组织机构信息开始。");

        String timeStr1= LocalDateTime.now().plusMinutes(-2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("同步时间："+timeStr1);

        //同步机构信息
        String departRes = syncService.sync(deptusersyncUrl,timeStr1,"1","1","0");
        logger.info("组织机构同步结果："+departRes);
        if("success".equals(departRes)){
            //同步用户信息
            String userRes =  syncService.sync(deptusersyncUrl,timeStr1,"2","1","0");
            logger.info("同步用户信息结果："+userRes);
        }else{
            logger.info("机构同步失败，不继续同步人员信息");
        }
        logger.info("定时同步机构和用户信息完成。");
    }


}
