package com.ctrip.gs.common.mq;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ctrip.freeway.logging.ILog;
import com.ctrip.freeway.logging.LogManager;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author: wgji
 * @date：2014年4月1日 下午3:07:55
 * @comment:
 */

public class RabbitMQ {
    private static final ILog logger = LogManager.getLogger(RabbitMQ.class);

    @SuppressWarnings("deprecation")
    public static void setDeltaData(String mqUrl, String routingKey, String exchangeName, String queueName,
            String type, HashMap<String, String> data) {
        if (StringUtils.isBlank(mqUrl)) {
            return;
        }
        ConnectionFactory factory = new ConnectionFactory();
        // String mqUri = "amqp://tg:travelguide@dst56369:5672/gstest";
        Connection conn = null;
        Channel channel = null;
        try {
            DeltaEntity entity = new DeltaEntity();
            entity.setTargetId("1");
            entity.setOperatorType(9); // 表示EDIT状态
            entity.setTargetType(1);
            entity.setData(data);
            factory.setUri(mqUrl);
            conn = factory.newConnection();
            channel = conn.createChannel();
            channel.queueBind(queueName, exchangeName, routingKey);
            String jsonString = JSON.toJSONString(entity);
            AMQP.BasicProperties props = new AMQP.BasicProperties();
            props.setType(type);
            channel.basicPublish(exchangeName, routingKey, props, jsonString.getBytes());
        } catch (Exception ex) {
            logger.error("error happen in setting rabbit mq delta with mqUrl:" + mqUrl + ",routingKey:" + routingKey
                    + ",exchangeName:" + exchangeName + ",queueName" + queueName, ex);
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    logger.error("error happen in set closing mq channel:", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                    logger.error("error happen in set closing mq connection:", e);
                }
            }
        }
    }
}
