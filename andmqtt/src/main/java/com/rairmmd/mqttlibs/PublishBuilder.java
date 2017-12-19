package com.rairmmd.mqttlibs;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:
 */

public class PublishBuilder implements IBuilder {

    private String mTopic;
    private int mQos;
    private String mMsg;
    private boolean mRetained;

    /**
     * 设置主题
     *
     * @param mTopic 主题
     * @return PublishBuilder
     */
    public PublishBuilder setTopic(String mTopic) {
        this.mTopic = mTopic;
        return this;
    }

    /**
     * QoS=0：最多一次，有可能重复或丢失。
     * QoS=1：至少一次，有可能重复。
     * QoS=2：只有一次，确保消息只到达一次。
     *
     * @param mQos 服务质量
     * @return PublishBuilder
     */
    public PublishBuilder setQos(int mQos) {
        this.mQos = mQos;
        return this;
    }

    /**
     * 设置消息
     *
     * @param mMsg 消息
     * @return PublishBuilder
     */
    public PublishBuilder setMsg(String mMsg) {
        this.mMsg = mMsg;
        return this;
    }

    /**
     * 设置是否在服务器中保存消息体
     *
     * @param mRetained boolean
     * @return PublishBuilder
     */
    public PublishBuilder setRetained(boolean mRetained) {
        this.mRetained = mRetained;
        return this;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        MqttManager.getInstance().getConnectBuilder().getClient()
                .publish(mTopic, mMsg.getBytes(), mQos, mRetained, null, listener);
    }
}
