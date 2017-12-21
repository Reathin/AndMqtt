package com.rair.mqttsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rairmmd.mqttlibs.ConnectBuilder;
import com.rairmmd.mqttlibs.MqttManager;
import com.rairmmd.mqttlibs.PublishBuilder;
import com.rairmmd.mqttlibs.SubscribeBuilder;
import com.rairmmd.mqttlibs.UnSubscribeBuilder;

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
        MqttManager.getInstance().init(this);
        MqttManager.getInstance().setMessageListener(new MqttCallbackExtended() {
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
        }).connect(new ConnectBuilder().setClientId("android")
                .setPort(1884)
                .setServer("tcp://xx.xx.xx.235"), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(MainActivity.java:51)-onSuccess:->连接成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:56)-onFailure:->连接失败");
            }
        });

        //订阅
        MqttManager.getInstance().subscribe(new SubscribeBuilder()
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
        MqttManager.getInstance().publish(new PublishBuilder()
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
        MqttManager.getInstance().unSubscribe(new UnSubscribeBuilder()
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
