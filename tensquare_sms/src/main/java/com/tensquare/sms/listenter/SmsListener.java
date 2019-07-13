package com.tensquare.sms.listenter;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "tensquare_sms")
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;
    @Value("${aliyun.sms.signName}")
    private String signName;
    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    @RabbitHandler
    public void receiveMessage(Map<String,String> map){
        String mobile =  map.get("mobile");
        String checkCode = map.get("checkCode");

        try {
            smsUtils.sendSms(mobile, signName, templateCode,
                    "{\"code\":\""+checkCode+"\"}");
        } catch (ClientException e) {
            System.out.println("短信发送失败!");
        }
    }

}
