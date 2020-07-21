package com.keeplearn.shichunjia.controller;


import com.keeplearn.shichunjia.utils.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/rabbitmq")
public class RabbitController {

    @Autowired
    private MsgProducer msgProducer;


    @GetMapping("/sent")
    public String sentMessage(String message){
        msgProducer.sendMsg("-------------111------------");
        return "消息已发送";
    }
    @GetMapping("/test")
    public String sentMessageTest(String message){
        return "消息已发送";
    }

}
