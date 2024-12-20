package com.reathin.mqttsample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.reathin.andmqtt.AndMqtt;
import com.reathin.andmqtt.MqttConnect;
import com.reathin.andmqtt.MqttDisconnect;
import com.reathin.andmqtt.MqttPublish;
import com.reathin.andmqtt.MqttSubscribe;
import com.reathin.andmqtt.MqttUnSubscribe;

import org.eclipse.paho.android.service.MqttTraceHandler;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MqttConnect mqttConnect = new MqttConnect().setClientId("android")
                .setPort(1883).setAutoReconnect(true)
                .setCleanSession(true).setServer("test.mosquitto.org")
                .setMessageListener(new MqttCallbackExtended() {
                    @Override
                    public void connectComplete(boolean reconnect, String serverURI) {
                        Log.d(TAG, "connectComplete:->连接完成 " + serverURI + " reconnect:" + reconnect);
                    }

                    @Override
                    public void connectionLost(Throwable cause) {
                        Log.d(TAG, "connectionLost:->连接丢失 " + cause.getMessage());
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        Log.d(TAG, "messageArrived:->接收消息 " + "主题:" + topic + " 消息" + new String(message.getPayload()));
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        Log.d(TAG, "deliveryComplete:消息送达");
                    }
                });

        AndMqtt.getInstance().connect(mqttConnect, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i(TAG, "onSuccess:->连接成功");
                subscribe();
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i(TAG, "onFailure:->连接失败");
            }
        });
    }

    private void subscribe() {
        //订阅
        AndMqtt.getInstance().subscribe(new MqttSubscribe()
                .setTopic("#").setQos(0), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i(TAG, "onSuccess:->订阅成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i(TAG, "onFailure:->订阅失败");
            }
        });

        //发布
        AndMqtt.getInstance().publish(new MqttPublish()
                .setMsg("消息").setQos(0).setTopic("#"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i(TAG, "onSuccess:->发布成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i(TAG, "onFailure:->发布失败");
            }
        });

        //取消订阅
        AndMqtt.getInstance().unSubscribe(new MqttUnSubscribe()
                .setTopic("#"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i(TAG, "onSuccess:->取消订阅成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i(TAG, "onFailure:->取消订阅失败");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AndMqtt.getInstance().isConnect()) {
            AndMqtt.getInstance().disConnect(new MqttDisconnect()
                    .setQuiesceTimeout(0), new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "onFailure:->断开连接成功");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "onFailure:->断开连接失败");
                }
            });
        }
    }
}
