package com.rairmmd.mqttlibs;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttTraceHandler;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Rair
 * @date 2017/12/11
 * <p>
 * desc:Mqtt管理器
 */

public class AndMqtt {

    public static final String TAG = "AndMqtt";

    private static AndMqtt instance;
    private Context mContext;
    private MqttConnect mMqttConnect;
    private MqttCallbackExtended mMessageListener;
    private MqttTraceHandler mTraceListener;
    private boolean mTraceEnable;

    private AndMqtt() {

    }

    public static AndMqtt getInstance() {
        if (instance == null) {
            synchronized (AndMqtt.class) {
                if (instance == null) {
                    instance = new AndMqtt();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context mContext
     */
    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 接受消息监听
     *
     * @param listener MqttCallbackExtended
     */
    public AndMqtt setMessageListener(MqttCallbackExtended listener) {
        this.mMessageListener = listener;
        return this;
    }

    /**
     * 日志监听
     *
     * @param mTraceListener MqttTraceHandler
     *                       需先调用 setTraceEnable() 打开
     * @return AndMqtt
     */
    public AndMqtt setTraceListener(MqttTraceHandler mTraceListener) {
        this.mTraceListener = mTraceListener;
        return this;
    }

    /**
     * 日志开关
     *
     * @param mTraceEnable 开关
     * @return AndMqtt
     */
    public AndMqtt setTraceEnable(boolean mTraceEnable) {
        this.mTraceEnable = mTraceEnable;
        mMqttConnect.setTraceEnabled(mTraceEnable);
        return this;
    }

    /**
     * 获取context
     *
     * @return context
     */
    public Context getContext() {
        if (mContext == null) {
            Log.e(TAG, "Context is null,you need To initialize the AndMqtt first!");
        }
        return mContext;
    }

    /**
     * 获取连接对象
     *
     * @return MqttConnect
     */
    public MqttConnect getMqttConnect() {
        return mMqttConnect;
    }

    /**
     * 连接服务器
     *
     * @param builder  服务器连接MqttConnect
     * @param listener 监听
     */
    public void connect(MqttConnect builder, IMqttActionListener listener) {
        mMqttConnect = builder;
        if (mMessageListener != null) {
            builder.setMessageListener(mMessageListener);
        }
        if (mTraceListener != null) {
            builder.setTraceCallback(mTraceListener);
        }
        builder.setTraceEnabled(mTraceEnable);
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
     * @return
     */
    public boolean isConneect() {
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
