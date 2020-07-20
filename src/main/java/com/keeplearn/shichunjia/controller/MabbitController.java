package com.keeplearn.shichunjia.controller;


import com.keeplearn.shichunjia.utils.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/RabbitMq/api")
public class MabbitController {

    @Autowired
    private MsgProducer msgProducer;


    @RequestMapping()
    public String sentMessage(String message){
        msgProducer.sendMsg("-------------111------------");
        return "消息已发送";
    }

}
