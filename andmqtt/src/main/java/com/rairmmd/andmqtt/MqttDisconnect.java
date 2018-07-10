package com.rairmmd.andmqtt;

import com.rairmmd.andmqtt.mqttv3.IMqttActionListener;
import com.rairmmd.andmqtt.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:
 */

public class MqttDisconnect implements IMqtt {

    private long mQuiesceTimeout;

    /**
     * 延时多久断开连接
     *
     * @param quiesceTimeout 时间
     * @return MqttDisconnect
     */
    public MqttDisconnect setQuiesceTimeout(long quiesceTimeout) {
        this.mQuiesceTimeout = quiesceTimeout;
        return this;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        AndMqtt.getInstance().getMqttConnect().getClient()
                .disconnect(mQuiesceTimeout, null, listener);
    }
}
