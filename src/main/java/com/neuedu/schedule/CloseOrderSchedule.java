package com.neuedu.schedule;


import com.neuedu.service.IOrderService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;



@Component
public class CloseOrderSchedule {

    @Value("${order.close.timeout}")
    private int orderTimeout;

    @Autowired
    IOrderService orderService;

    //5s
    @Scheduled(cron = "*/50 * * * * *")
    public void closeOrder(){

        //1.
        Date closeOrderTime=DateUtils.addHours(new Date(),-orderTimeout);
        orderService.closeOrder(com.neuedu.utils.DateUtils.dateToStr(closeOrderTime));

    }

}
