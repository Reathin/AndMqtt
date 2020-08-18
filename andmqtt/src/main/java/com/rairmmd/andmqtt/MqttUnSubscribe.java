package com.rairmmd.andmqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:
 */

public class MqttUnSubscribe implements IMqtt {

    private String mTopic;

    /**
     * 设置主题
     *
     * @param mTopic 主题
     * @return MqttUnSubscribe
     */
    public MqttUnSubscribe setTopic(String mTopic) {
        this.mTopic = mTopic;
        return this;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        AndMqtt.getInstance().getMqttClient().unsubscribe(mTopic, null, listener);
    }
}
