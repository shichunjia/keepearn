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


    public static void main(String[] args) {
        System.out.println(2+3.9+5+5+11.12+6+6+54.74+4+49+18+5+5+9.6+30+12+7.9+40.56+6237+13+13.88+5+1.5+14.99+13+49+5+2.9+15.9+10+12.6+2.3+40+15+17+6.19+3.5+1.9+12.9+22+15.88+18+8+26.9+2.5+13.9+120+20+5+2+11.88+15+17.8+12+5+3.9+14+6+55+6+15+15+50+15+7.5);
    }
}
