package com.hailas.framework.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.AbstractMqttMessageHandler;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Header;


/**
 * @author jose
 * @date 2022/3/22 10:25 上午
 * @description
 */
//@EnableIntegration
//@Configuration
//@ConditionalOnProperty("mqtt.services")
public class MqttConfig{
//    private static Logger logger = LoggerFactory.getLogger(MqttConfig.class);

//    private final MsgSendService msgSendService;//发布消息到消息中间件接口

    @Value("${mqtt.appid:mqtt_id}")
    private String appid;//客户端ID

    @Value("${mqtt.input.topic:mqtt_input_topic}")
    private String[] inputTopic;//订阅主题，可以是多个主题

    @Value("${mqtt.out.topic:mqtt_out_topic}")
    private String[] outTopic;//发布主题，可以是多个主题

    @Value("${mqtt.services:#{null}}")
    private String[] mqttServices;//服务器地址以及端口

    @Value("${mqtt.user:#{null}}")
    private String user;//用户名

    @Value("${mqtt.password:#{null}}")
    private String password;//密码

    @Value("${mqtt.KeepAliveInterval:300}")
    private Integer KeepAliveInterval;//心跳时间,默认为5分钟

    @Value("${mqtt.CleanSession:false}")
    private Boolean CleanSession;//是否不保持session,默认为session保持

    @Value("${mqtt.AutomaticReconnect:true}")
    private Boolean AutomaticReconnect;//是否自动重联，默认为开启自动重联

    @Value("${mqtt.CompletionTimeout:30000}")
    private Long CompletionTimeout;//连接超时,默认为30秒

    @Value("${mqtt.Qos:1}")
    private Integer Qos;//通信质量，详见MQTT协议




    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(MqttPahoClientFactory mqttPahoClientFactory) {


        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(appid, mqttPahoClientFactory, inputTopic);//建立订阅连接
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
//        converter.setPayloadAsBytes(true);//bytes类型接收
        adapter.setCompletionTimeout(CompletionTimeout);//连接超时的时间
        adapter.setConverter(converter);
        adapter.setQos(Qos);//消息质量
        adapter.setOutputChannelName("mqttInputChannel");//输入管道名称


//        MqttPahoMessageDrivenChannelAdapter adapter =
//                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient",
//                        "topic1", "topic2");
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(1);
//        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getPayload());
            }

        };
    }




    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttServices);
        options.setUserName(user);
        options.setPassword(password.toCharArray());
        options.setKeepAliveInterval(KeepAliveInterval);//心跳时间
        options.setAutomaticReconnect(AutomaticReconnect);//断开是否自动重联
        options.setCleanSession(CleanSession);//保持session
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory connectionFactory) {
//        MqttPahoMessageHandler messageHandler =
//                new MqttPahoMessageHandler("testClient", mqttClientFactory());
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic("testTopic");


        MqttPahoMessageHandler outGate = new MqttPahoMessageHandler(appid + "_put", connectionFactory);
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
//        converter.setPayloadAsBytes(true);//bytes类型接收
        outGate.setAsync(true);
        outGate.setCompletionTimeout(CompletionTimeout);//设置连接超时时时
        outGate.setDefaultQos(Qos);//设置通信质量
        outGate.setConverter(converter);
        return outGate;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MyGateway {

        void sendToMqtt(@Header(MqttHeaders.TOPIC) String a, String data);

    }



//
//
//    public MQTTConfig(MsgSendService msgSendService) {
//        this.msgSendService = msgSendService;
//    }
//
//    /**
//     * MQTT连接配置
//     *
//     * @return 连接工厂
//     */
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();//连接工厂类
//        MqttConnectOptions options = new MqttConnectOptions();//连接参数
//        options.setServerURIs(mqttServices);//连接地址
//        if (null != user) {
//            options.setUserName(user);//用户名
//        }
//        if (null != password) {
//            options.setPassword(password.toCharArray());//密码
//        }
//        options.setKeepAliveInterval(KeepAliveInterval);//心跳时间
//        options.setAutomaticReconnect(AutomaticReconnect);//断开是否自动重联
//        options.setCleanSession(CleanSession);//保持session
//        factory.setConnectionOptions(options);
//        return factory;
//    }
//
//    /**
//     * 入站管道
//     *
//     * @param mqttPahoClientFactory
//     * @return
//     */
//    @Bean
//    public MessageProducerSupport mqttInput(MqttPahoClientFactory mqttPahoClientFactory) {
//        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(appid, mqttPahoClientFactory, inputTopic);//建立订阅连接
//        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
//        converter.setPayloadAsBytes(true);//bytes类型接收
//        adapter.setCompletionTimeout(CompletionTimeout);//连接超时的时间
//        adapter.setConverter(converter);
//        adapter.setQos(Qos);//消息质量
//        adapter.setOutputChannelName(ChannelName.INPUT_DATA);//输入管道名称
//        return adapter;
//    }
//
//    /**
//     * 向服务器发送数据管道绑定
//     *
//     * @param connectionFactory tcp连接工厂类
//     * @return 消息管道对象
//     */
//    @Bean
//    @ServiceActivator(inputChannel = ChannelName.OUTPUT_DATA_MQTT)
//    public AbstractMqttMessageHandler MQTTOutAdapter(MqttPahoClientFactory connectionFactory) {
//        //创建一个新的出站管道，由于MQTT的发布与订阅是两个独立的连接，因此客户端的ID(即APPID）不能与订阅时所使用的ID一样，否则在服务端会认为是同一个客户端，而造成连接失败
//        MqttPahoMessageHandler outGate = new MqttPahoMessageHandler(appid + "_put", connectionFactory);
//        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
//        converter.setPayloadAsBytes(true);//bytes类型接收
//        outGate.setAsync(true);
//        outGate.setCompletionTimeout(CompletionTimeout);//设置连接超时时时
//        outGate.setDefaultQos(Qos);//设置通信质量
//        outGate.setConverter(converter);
//        return outGate;
//    }
//
//    /**
//     * MQTT连接时调用的方法
//     *
//     * @param event
//     */
//    @Override
//    public void onApplicationEvent(ApplicationEvent event) {
//        if (event instanceof MqttSubscribedEvent) {
//            String msg = "OK";
//            /**------------------连接时需要发送起始消息，写在这里-------------**/
//            msgSendService.send(MessageBuilder.withPayload(msg.getBytes()).build());
//        }
//    }
}