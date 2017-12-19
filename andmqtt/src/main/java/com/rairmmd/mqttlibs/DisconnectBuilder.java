package com.rairmmd.mqttlibs;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:
 */

public class DisconnectBuilder implements IBuilder {

    private long mQuiesceTimeout;

    /**
     * 延时多久断开连接
     *
     * @param quiesceTimeout 时间
     * @return DisconnectBuilder
     */
    public DisconnectBuilder setQuiesceTimeout(long quiesceTimeout) {
        this.mQuiesceTimeout = quiesceTimeout;
        return this;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        MqttManager.getInstance().getConnectBuilder().getClient()
                .disconnect(mQuiesceTimeout, null, listener);
    }
}
