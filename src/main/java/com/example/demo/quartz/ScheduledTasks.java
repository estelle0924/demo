package com.example.demo.quartz;

import com.example.demo.mapper.IDocMapper;
import com.example.demo.service.IDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: estelle
 * @Description:
 * @Date: Created in 16:16 2019/1/3
 * @Modified By:
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private IDocService docService;
    @Autowired
    private IDocMapper mapper;

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron() {
        Date date = new Date();
        System.out.println("任务执行开始:{}" + date);
        Integer result = 0;
        try {
            result = docService.OpenTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name="任务执行结束:{}" + new Date() + "开始时间{}" + date + "数据条数:{}" + result;
        System.out.println(name);
        mapper.into(name);
    }

    //@Scheduled(cron = "0 */1 *  * * * ")
    public void update() {
        System.out.println("任务保存执行开始:{}");
        for (int i = 0; i < 10000; i++) {
            mapper.insertDate();
        }
        mapper.update();
        System.out.println("任务保存执行结束:{}");
    }
}