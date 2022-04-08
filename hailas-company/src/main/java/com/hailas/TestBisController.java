package com.hailas;

import com.hailas.framework.mqtt.MqttConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jose
 * @date 2022/3/22 4:22 下午
 * @description
 */
@RestController
@RequestMapping("test")
public class TestBisController {


    @Autowired
    MqttConfig.MyGateway gateway;

    @GetMapping("/send")
    @ResponseBody
    public String send(){
        gateway.sendToMqtt("mqtt_input_test","bbbb");
        return "aaaa";
    }

}
