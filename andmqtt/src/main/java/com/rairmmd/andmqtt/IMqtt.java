package com.rairmmd.andmqtt;

import com.rairmmd.andmqtt.mqttv3.IMqttActionListener;
import com.rairmmd.andmqtt.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:
 */

public interface IMqtt {

    void execute(IMqttActionListener listener) throws MqttException;
}
