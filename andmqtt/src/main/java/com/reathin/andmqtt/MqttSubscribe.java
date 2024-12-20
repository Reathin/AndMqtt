package com.reathin.andmqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:
 */

public class MqttSubscribe implements IMqtt {

    private String mTopic;
    private int mQos;

    /**
     * 设置主题
     *
     * @param topic 主题
     * @return MqttSubscribe
     */
    public MqttSubscribe setTopic(String topic) {
        this.mTopic = topic;
        return this;
    }

    /**
     * QoS=0：最多一次，有可能重复或丢失。
     * QoS=1：至少一次，有可能重复。
     * QoS=2：只有一次，确保消息只到达一次。
     *
     * @param qos 服务质量
     * @return MqttSubscribe
     */
    public MqttSubscribe setQos(int qos) {
        this.mQos = qos;
        return this;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        AndMqtt.getInstance().getMqttClient().subscribe(mTopic, mQos, null, listener);
    }
}
