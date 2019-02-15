package com.rairmmd.andmqtt;

import android.content.Context;
import android.support.annotation.RawRes;
import android.text.TextUtils;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttTraceHandler;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.io.InputStream;

import javax.net.ssl.SSLSocketFactory;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:连接建造者
 */

public class MqttConnect implements IMqtt {

    private final String TAG = "AndMqtt";
    private Context mContext;
    private String mClientId;
    private String mServer;
    private int mPort;
    private String lastWillMsg;
    private String lastWillTopic;
    private int mTimeout;
    private int mKeepAlive;
    private String mUserName;
    private String mUserPassword;
    private boolean mCleanSession;
    private int mSslKeyRawId;
    private String mSslKeyPassword;
    private SSLSocketFactory mSslSocketFactory;
    private MqttAndroidClient mClient;
    private int lastWillQos;
    private boolean lastWillRetained;
    private MqttCallback mMessageListener;
    private boolean traceEnabled = false;
    private boolean mAutoReconnect = true;
    private MqttTraceHandler mTraceCallback;

    public MqttConnect() {
        mContext = AndMqtt.getInstance().getContext();
    }

    /**
     * 设置clientId
     *
     * @param mClientId clientId 设备唯一表示
     * @return MqttConnect
     */
    public MqttConnect setClientId(String mClientId) {
        this.mClientId = mClientId;
        return this;
    }

    /**
     * 设置服务器地址
     *
     * @param mServer 服务器地址 ：  tcp://111.111.111.111
     * @return MqttConnect
     */
    public MqttConnect setServer(String mServer) {
        this.mServer = mServer;
        return this;
    }

    /**
     * 设置端口
     *
     * @param mPort 端口号
     * @return MqttConnect
     */
    public MqttConnect setPort(int mPort) {
        this.mPort = mPort;
        return this;
    }

    /**
     * 设置超时时间
     *
     * @param mTimeout 超时时间
     * @return MqttConnect
     */
    public MqttConnect setTimeout(int mTimeout) {
        this.mTimeout = mTimeout;
        return this;
    }

    /**
     * 保持连接 ，心跳时间
     *
     * @param keepAlive 心跳时间
     * @return MqttConnect
     */
    public MqttConnect setKeepAlive(int keepAlive) {
        this.mKeepAlive = keepAlive;
        return this;
    }

    /**
     * 是否清除会话
     * <p>
     * 为true时会移除这个client所有的subscriptions
     * false时会在重连时继续未完成的subscriptions
     * <p>
     * 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
     * 这里设置为true表示每次连接到服务器都以新的身份连接
     *
     * @param cleanSession Boolean
     * @return MqttConnect
     */
    public MqttConnect setCleanSession(boolean cleanSession) {
        this.mCleanSession = cleanSession;
        return this;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     * @return MqttConnect
     */
    public MqttConnect setUserName(String userName) {
        this.mUserName = userName;
        return this;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     * @return MqttConnect
     */
    public MqttConnect setUserPassword(String password) {
        this.mUserPassword = password;
        return this;
    }

    /**
     * 设置ssl
     *
     * @param mSslKeyRawId   ssl key RawId
     * @param sslKeyPassword 密码
     * @return MqttConnect
     */
    public MqttConnect setSsl(@RawRes int mSslKeyRawId, String sslKeyPassword) {
        this.mSslKeyRawId = mSslKeyRawId;
        this.mSslKeyPassword = sslKeyPassword;
        return this;
    }

    /**
     * 自定义SslSocketFactoryl
     *
     * @param mSslSocketFactory mSslSocketFactory
     * @return MqttConnect
     */
    public MqttConnect setSslSocketFactory(SSLSocketFactory mSslSocketFactory) {
        this.mSslSocketFactory = mSslSocketFactory;
        return this;
    }

    /**
     * 是否自动重连
     *
     * @param mAutoReconnect 是否自动重连
     * @return MqttConnect
     */
    public MqttConnect setAutoReconnect(boolean mAutoReconnect) {
        this.mAutoReconnect = mAutoReconnect;
        return this;
    }

    /**
     * 是否显示相关日志
     *
     * @param traceEnabled Boolean
     * @return MqttConnect
     */
    public MqttConnect setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
        return this;
    }

    /**
     * 相关日志回调
     *
     * @param traceCallback 回调
     * @return MqttConnect
     */
    public MqttConnect setTraceCallback(MqttTraceHandler traceCallback) {
        this.mTraceCallback = traceCallback;
        return this;
    }

    /**
     * 设置遗留消息
     * <p>
     * 当设备断开连接时会主动publish的消息
     *
     * @param lastWillMsg      消息
     * @param lastWillTopic    主题
     * @param lastWillQos      服务质量
     * @param lastWillRetained 设置是否在服务器中保存消息体
     * @return
     */
    public MqttConnect setLastWill(String lastWillMsg, String lastWillTopic, int lastWillQos, boolean lastWillRetained) {
        this.lastWillMsg = lastWillMsg;
        this.lastWillTopic = lastWillTopic;
        this.lastWillQos = lastWillQos;
        this.lastWillRetained = lastWillRetained;
        return this;
    }

    /**
     * 设置消息回调监听
     *
     * @param listener 监听
     */
    public MqttConnect setMessageListener(MqttCallbackExtended listener) {
        this.mMessageListener = listener;
        return this;
    }

    /**
     * 获取客户端
     *
     * @return MqttAndroidClient
     */
    public MqttAndroidClient getClient() {
        return mClient;
    }

    @Override
    public void execute(IMqttActionListener listener) throws MqttException {
        //拼接服务器地址
        String uri = mServer.concat(":").concat(String.valueOf(mPort));
        if (mClient == null) {
            mClient = new MqttAndroidClient(mContext, uri, mClientId);
        }
        mClient.setCallback(mMessageListener);
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(mCleanSession);
        connectOptions.setConnectionTimeout(mTimeout);
        connectOptions.setKeepAliveInterval(mKeepAlive);
        connectOptions.setAutomaticReconnect(mAutoReconnect);
        mClient.connect(connectOptions);
        if (mSslKeyRawId != 0) {
            try {
                InputStream key = mContext.getResources().openRawResource(mSslKeyRawId);
                connectOptions.setSocketFactory(mClient.getSSLSocketFactory(key, mSslKeyPassword));
            } catch (MqttSecurityException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (mSslSocketFactory != null) {
            connectOptions.setSocketFactory(mSslSocketFactory);
        }
        if (!TextUtils.isEmpty(mUserName)) {
            connectOptions.setUserName(mUserName);
        }
        if (!TextUtils.isEmpty(mUserPassword)) {
            connectOptions.setPassword(mUserPassword.toCharArray());
        }
        boolean isConnect = true;
        if ((!TextUtils.isEmpty(lastWillMsg)) || (!TextUtils.isEmpty(lastWillTopic))) {
            try {
                connectOptions.setWill(lastWillTopic, lastWillMsg.getBytes(), lastWillQos, lastWillRetained);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                isConnect = false;
            }
        }
        if (traceEnabled) {
            mClient.setTraceEnabled(traceEnabled);
            mClient.setTraceCallback(mTraceCallback);
        }
        if (isConnect) {
            mClient.connect(connectOptions, null, listener);
        }
    }
}
