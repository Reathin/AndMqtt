package com.rairmmd.mqttlibs;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.rairmmd.mqttlibs.android.MqttAndroidClient;
import com.rairmmd.mqttlibs.android.MqttTraceHandler;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.io.InputStream;

import static android.content.ContentValues.TAG;

/**
 * @author Rair
 * @date 2017/12/13
 * <p>
 * desc:连接建造者
 */

public class ConnectBuilder implements IBuilder {

    private final Context mContext;
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
    private String mSslKeyPath;
    private String mSslKeyPassword;
    private MqttAndroidClient mClient;
    private int lastWillQos;
    private boolean lastWillRetained;
    private MqttCallback mMessageListener;
    private boolean traceEnabled = false;
    private MqttTraceHandler mTraceCallback;
    private static ConnectBuilder instance;

    private ConnectBuilder() {
        mContext = MqttManager.getInstance().getContext();
    }

    public static ConnectBuilder getInstance() {
        if (instance == null) {
            synchronized (ConnectBuilder.class) {
                if (instance == null) {
                    instance = new ConnectBuilder();
                }
            }
        }
        return instance;
    }

    /**
     * 设置clientId
     *
     * @param mClientId clientId 设备唯一表示
     * @return ConnectBuilder
     */
    public ConnectBuilder setClientId(String mClientId) {
        this.mClientId = mClientId;
        return this;
    }

    /**
     * 设置服务器地址
     *
     * @param mServer 服务器地址 ：  tcp://111.111.111.111
     * @return ConnectBuilder
     */
    public ConnectBuilder setServer(String mServer) {
        this.mServer = mServer;
        return this;
    }

    /**
     * 设置端口
     *
     * @param mPort 端口号
     * @return ConnectBuilder
     */
    public ConnectBuilder setPort(int mPort) {
        this.mPort = mPort;
        return this;
    }

    /**
     * 设置超时时间
     *
     * @param mTimeout 超时时间
     * @return ConnectBuilder
     */
    public ConnectBuilder setTimeout(int mTimeout) {
        this.mTimeout = mTimeout;
        return this;
    }

    /**
     * 保持连接 ，心跳时间
     *
     * @param keepAlive 心跳时间
     * @return ConnectBuilder
     */
    public ConnectBuilder setKeepAlive(int keepAlive) {
        this.mKeepAlive = keepAlive;
        return this;
    }

    /**
     * 是否清除会话
     * <p>
     * 为true时会移除这个client所有的subscriptions
     * false时会在重连时继续未完成的subscriptions
     *
     * @param cleanSession Boolean
     * @return ConnectBuilder
     */
    public ConnectBuilder setCleanSession(boolean cleanSession) {
        this.mCleanSession = cleanSession;
        return this;
    }

    /**
     * 设置用户名和密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return ConnectBuilder
     */
    public ConnectBuilder setUserNameAndPassword(String userName, String password) {
        this.mUserName = userName;
        this.mUserPassword = password;
        return this;
    }

    /**
     * 设置ssl
     *
     * @param sslKeyPath     证书路径
     * @param sslKeyPassword 密码
     * @return
     */
    public ConnectBuilder setSsl(String sslKeyPath, String sslKeyPassword) {
        this.mSslKeyPath = sslKeyPath;
        this.mSslKeyPassword = sslKeyPassword;
        return this;
    }

    /**
     * 是否显示相关日志
     *
     * @param traceEnabled Boolean
     * @return ConnectBuilder
     */
    public ConnectBuilder setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
        return this;
    }

    /**
     * 相关日志回调
     *
     * @param traceCallback 回调
     * @return ConnectBuilder
     */
    public ConnectBuilder setTraceCallback(MqttTraceHandler traceCallback) {
        this.mTraceCallback = traceCallback;
        return this;
    }

    public ConnectBuilder setLastWill(String lastWillMsg, String lastWillTopic, int lastWillQos, boolean lastWillRetained) {
        this.lastWillMsg = lastWillMsg;
        this.lastWillTopic = lastWillTopic;
        this.lastWillQos = lastWillQos;
        this.lastWillRetained = lastWillRetained;
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
        String uri = mServer.concat(":").concat(String.valueOf(mPort));
        MqttAndroidClient client = new MqttAndroidClient(mContext, uri, mClientId);
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        client.connect(connectOptions);
        if (!TextUtils.isEmpty(mSslKeyPath)) {
            try {
                InputStream key = this.getClass().getResourceAsStream(mSslKeyPath);
                connectOptions.setSocketFactory(client.getSSLSocketFactory(key, mSslKeyPassword));
            } catch (MqttSecurityException e) {
                Log.e(this.getClass().getCanonicalName(), "MqttException Occured: ", e);
            }
        }
        connectOptions.setCleanSession(mCleanSession);
        connectOptions.setConnectionTimeout(mTimeout);
        connectOptions.setKeepAliveInterval(mKeepAlive);
        if (!TextUtils.isEmpty(mUserName)) {
            connectOptions.setUserName(mUserName);
        }
        if (!TextUtils.isEmpty(mUserPassword)) {
            connectOptions.setPassword(mUserPassword.toCharArray());
        }

        boolean doConnect = true;

        if ((!TextUtils.isEmpty(lastWillMsg)) || (!TextUtils.isEmpty(lastWillTopic))) {
            try {
                connectOptions.setWill(lastWillTopic, lastWillMsg.getBytes(), lastWillQos, lastWillRetained);
            } catch (Exception e) {
                Log.d("", "connect() Exception Occured：" + e.toString());
                doConnect = false;
            }
        }
        client.setCallback(mMessageListener);
        if (traceEnabled) {
            client.setTraceCallback(mTraceCallback);
        }
        if (doConnect) {
            try {
                client.connect(connectOptions, null, listener);
            } catch (MqttException e) {
                Log.d(TAG, "connect() MqttException Occured:" + e.toString());
            }
        }
    }
}
