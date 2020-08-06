package com.csse.sync.fourinone.controller;

import com.csse.sync.fourinone.service.SyncService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sync")
public class SyncController {

    @Resource
    private SyncService syncService;

    @RequestMapping("/test")
    public String test() {
        String url = "http://192.168.42.11:10005/api/org/department/-1/subdepartments?access_token=e48bf7d1-9d6d-4460-b37f-101e641309ea&sublevel";
        return syncService.test(url);
    }


}
