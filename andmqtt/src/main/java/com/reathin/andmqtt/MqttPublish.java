package com.reathin.andmqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:
 */

public class MqttPublish implements IMqtt {

    private String mTopic;
    private int mQos;
    private String mMsg;
    private boolean mRetained;

    /**
     * 设置主题
     *
     * @param topic 主题
     * @return MqttPublish
     */
    public MqttPublish setTopic(String topic) {
        this.mTopic = topic;
        return this;
    }

    /**
     * QoS=0：最多一次，有可能重复或丢失。
     * QoS=1：至少一次，有可能重复。
     * QoS=2：只有一次，确保消息只到达一次。
     *
     * @param qos 服务质量
     * @return MqttPublish
     */
    public MqttPublish setQos(int qos) {
        this.mQos = qos;
        return this;
    }

    /**
     * 设置消息
     *
     * @param msg 消息
     * @return MqttPublish
     */
    public MqttPublish setMsg(String msg) {
        this.mMsg = msg;
        return this;
    }

    /**
     * 设置是否在服务器中保存消息体
     *
     * @param retained boolean
     * @return MqttPublish
     */
    public MqttPublish setRetained(boolean retained) {
        this.mRetained = retained;
        return this;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        AndMqtt.getInstance().getMqttClient().publish(mTopic, mMsg.getBytes(), mQos, mRetained, null, listener);
    }
}
