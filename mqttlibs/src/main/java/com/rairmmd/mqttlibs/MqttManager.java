package com.rairmmd.mqttlibs;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/11
 * <p>
 * desc:Mqtt管理
 */

public class MqttManager {

    private static MqttManager instance;
    private Context context;

    private MqttManager() {

    }

    public static MqttManager getInstance() {
        if (instance == null) {
            synchronized (MqttManager.class) {
                if (instance == null) {
                    instance = new MqttManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 连接服务器
     *
     * @param builder  服务器连接间建造者
     * @param listener 监听
     */
    public void connectServer(ConnectBuilder builder, IMqttActionListener listener) throws MqttException {
        builder.execute(listener);
    }

    /**
     * 接受消息监听
     *
     * @param listener MqttCallback
     */
    public void receiveMessageListener(MqttCallback listener) {

    }

    /**
     * 订阅
     *
     * @param builder  订阅建造者
     * @param listener 监听
     */
    public void subscribe(SubscribeBuilder builder, IMqttActionListener listener) {

    }

    /**
     * 发布
     *
     * @param builder  发布建造者
     * @param listener 监听
     */
    public void publish(PublishBuilder builder, IMqttActionListener listener) {

    }

    /**
     * 取消订阅
     *
     * @param builder  建造者
     * @param listener 监听
     */
    public void unSubscribe(UnSubscribeBuilder builder, IMqttActionListener listener) {

    }

}
