package com.rairmmd.andmqtt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttTraceHandler;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Objects;


/**
 * @author Rair
 * @date 2017/12/11
 * <p>
 * desc:Mqtt管理器
 */

public class AndMqtt {

    private static final String TAG = AndMqtt.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static volatile AndMqtt sInstance;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private MqttConnect mMqttConnect;

    private AndMqtt() {

    }

    public static AndMqtt getInstance() {
        if (sInstance == null) {
            synchronized (AndMqtt.class) {
                if (sInstance == null) {
                    sInstance = new AndMqtt();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化
     *
     * @param context mContext
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 获取context
     *
     * @return context
     */
    public Context getContext() {
        if (mContext == null) {
            throw new IllegalArgumentException("Context is null,you need to initialize the AndMqtt first!");
        }
        return mContext;
    }

    /**
     * 连接服务器
     *
     * @param builder  服务器连接MqttConnect
     * @param listener 监听
     */
    public void connect(MqttConnect builder, IMqttActionListener listener) {
        mMqttConnect = builder;
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 订阅
     *
     * @param builder  MqttSubscribe
     * @param listener 监听
     */
    public void subscribe(MqttSubscribe builder, IMqttActionListener listener) {
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 发布
     *
     * @param builder  MqttPublish
     * @param listener 监听
     */
    public void publish(MqttPublish builder, IMqttActionListener listener) {
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 取消订阅
     *
     * @param builder  MqttUnSubscribe
     * @param listener 监听
     */
    public void unSubscribe(MqttUnSubscribe builder, IMqttActionListener listener) {
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 断开连接
     *
     * @param builder  MqttDisconnect
     * @param listener 监听
     */
    public void disConnect(MqttDisconnect builder, IMqttActionListener listener) {
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 获取MqttAndroidClient
     *
     * @return MqttAndroidClient
     */
    public MqttAndroidClient getMqttClient() {
        if (mMqttConnect != null && mMqttConnect.getClient() != null) {
            return mMqttConnect.getClient();
        }
        return null;
    }

    /**
     * 是否连接
     *
     * @return boolean
     */
    public boolean isConnect() {
        if (mMqttConnect == null) {
            return false;
        }
        if (mMqttConnect.getClient() == null) {
            return false;
        }
        try {
            return mMqttConnect.getClient().isConnected();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }
}
