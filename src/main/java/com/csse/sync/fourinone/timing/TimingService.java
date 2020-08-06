package com.csse.sync.fourinone.timing;

import com.csse.sync.fourinone.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TimingService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SyncService syncService;

    @Scheduled(cron = "0/5 * * * * *")
    public void timedTasks(){
        syncService.test("");
        logger.info(this.getClass().getName()+".class  类是用来处理定时任务的");

    }


}
