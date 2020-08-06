package com.csse.sync.fourinone.service.impl;

import com.csse.sync.fourinone.service.SyncService;
import com.csse.sync.fourinone.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 所有调用http请求的方法，在httpUtils里都用调用的例子，在类的最下方
 */
@Service
public class SyncServiceImpl implements SyncService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String test(String url){
        logger.info("hello，info");
        logger.error("hello，error");
        logger.warn("hello，warn");
        //使用debug的时候，会发现debug并不打印在控制台
        //根据springboot的特点“约定大于配置”
        // spingboot只默认配置了info，error，warn三种输出，我们打印的时候，也使用这三种方法
        logger.debug("hello，debug");
        return "success";
    }
}
