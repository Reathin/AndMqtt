package com.rair.mqttsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttConnect;
import com.rairmmd.andmqtt.MqttPublish;
import com.rairmmd.andmqtt.MqttSubscribe;
import com.rairmmd.andmqtt.MqttUnSubscribe;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndMqtt.getInstance().init(this);
        AndMqtt.getInstance().setMessageListener(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.i("Rair", "(MainActivity.java:29)-connectComplete:->连接完成");
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.i("Rair", "(MainActivity.java:34)-connectionLost:->连接丢失");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.i("Rair", "(MainActivity.java:39)-messageArrived:->收到的消息");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i("Rair", "(MainActivity.java:44)-deliveryComplete:->消息已送达");
            }
        }).connect(new MqttConnect().setClientId("android")
                .setPort(1884)
                .setServer("tcp://xx.xx.xx.xx"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(MainActivity.java:51)-onSuccess:->连接成功");
                subscribe();
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:56)-onFailure:->连接失败");
            }
        });
    }

    private void subscribe() {
        //订阅
        AndMqtt.getInstance().subscribe(new MqttSubscribe()
                .setTopic("主题")
                .setQos(0), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(MainActivity.java:63)-onSuccess:->订阅成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:68)-onFailure:->订阅失败");
            }
        });

        //发布
        AndMqtt.getInstance().publish(new MqttPublish()
                .setMsg("消息")
                .setQos(0)
                .setTopic("主题"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(MainActivity.java:79)-onSuccess:->发布成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:84)-onFailure:->发布失败");
            }
        });

        //取消订阅
        AndMqtt.getInstance().unSubscribe(new MqttUnSubscribe()
                .setTopic("主题"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(MainActivity.java:93)-onSuccess:->取消订阅成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:98)-onFailure:->取消订阅失败");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
