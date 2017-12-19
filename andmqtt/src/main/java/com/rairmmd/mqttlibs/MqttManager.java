package com.rairmmd.mqttlibs;

import android.content.Context;
import android.util.Log;

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

public class MqttManager {

    public static final String TAG = "AndMqtt";

    private static MqttManager instance;
    private Context mContext;
    private ConnectBuilder mConnectBuilder;
    private MqttCallbackExtended mMessageListener;
    private MqttTraceHandler mTraceListener;
    private boolean mTraceEnable;

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
    public MqttManager setMessageListener(MqttCallbackExtended listener) {
        this.mMessageListener = listener;
        return this;
    }

    /**
     * 日志监听
     *
     * @param mTraceListener MqttTraceHandler
     *                       需先调用 setTraceEnable() 打开
     * @return MqttManager
     */
    public MqttManager setTraceListener(MqttTraceHandler mTraceListener) {
        this.mTraceListener = mTraceListener;
        return this;
    }

    /**
     * 日志开关
     *
     * @param mTraceEnable 开关
     * @return MqttManager
     */
    public MqttManager setTraceEnable(boolean mTraceEnable) {
        this.mTraceEnable = mTraceEnable;
        mConnectBuilder.setTraceEnabled(mTraceEnable);
        return this;
    }

    /**
     * 获取context
     *
     * @return context
     */
    public Context getContext() {
        if (mContext == null) {
            Log.e(TAG, "Context is null,you need To initialize the MqttManager first!");
        }
        return mContext;
    }

    /**
     * 获取连接对象
     *
     * @return ConnectBuilder
     */
    public ConnectBuilder getConnectBuilder() {
        return mConnectBuilder;
    }

    /**
     * 连接服务器
     *
     * @param builder  服务器连接间建造者
     * @param listener 监听
     */
    public void connect(ConnectBuilder builder, IMqttActionListener listener) {
        mConnectBuilder = builder;
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
     * @param builder  订阅建造者
     * @param listener 监听
     */
    public void subscribe(SubscribeBuilder builder, IMqttActionListener listener) {
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 发布
     *
     * @param builder  发布建造者
     * @param listener 监听
     */
    public void publish(PublishBuilder builder, IMqttActionListener listener) {
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 取消订阅
     *
     * @param builder  建造者
     * @param listener 监听
     */
    public void unSubscribe(UnSubscribeBuilder builder, IMqttActionListener listener) {
        try {
            builder.execute(listener);
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 是否连接
     *
     * @return
     */
    public boolean isConneect() {
        if (mConnectBuilder == null) {
            return false;
        }
        if (mConnectBuilder.getClient() == null) {
            return false;
        }
        try {
            return mConnectBuilder.getClient().isConnected();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

}
