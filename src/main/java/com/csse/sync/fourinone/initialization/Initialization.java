package com.csse.sync.fourinone.initialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Initialization implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info(this.getClass().getName()+".class  类的作用是：在项目启动完成时会自动调用该方法");
        logger.info("该方法可以用于项目第一次部署启动时，同步数据使用");
    }
}
