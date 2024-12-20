## AndMqtt
Android MQTT Client

在项目级别build.gradle或setting.gradle添加：
```
maven { url "https://repo.eclipse.org/content/repositories/paho-snapshots/" }
maven { url "https://jitpack.io" }
```
在module级别build.gradle添加：
```
implementation 'com.github.Reathin:AndMqtt:1.0.6'
implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.0'
implementation 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'
```

## 如何使用
### 1.创建连接对象，设置消息监听
```java
MqttConnect mqttConnect = new MqttConnect();
mqttConnect.setMessageListener(new MqttCallbackExtended() {
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        Log.i("MQTT", "connectComplete:->连接完成");
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.i("MQTT", "connectionLost:->连接丢失");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.i("MQTT", "messageArrived:->收到的消息");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.i("MQTT", "deliveryComplete:->消息已送达");
    }
})
```

对收到的消息进行处理

### 2.连接服务器
```java
 AndMqtt.getInstance().connect(mqttConnect.setServer("服务器地址")
                .setPort(端口号), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("MQTT", "onSuccess:->连接成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("MQTT", "onFailure:->连接失败");
            }
        });
```

也可以直接链式调用
```java
AndMqtt.getInstance().connect(new MqttConnect().setClientId("android")
    .setPort(1884).setAutoReconnect(true)
    .setCleanSession(true).setServer("tcp://119.3.27.191")
    .setTraceCallback(new MqttTraceHandler() {
        @Override
        public void traceDebug(String tag, String message) {

        }

        @Override
        public void traceError(String tag, String message) {

        }

        @Override
        public void traceException(String tag, String message, Exception e) {

        }
    }).setMessageListener(new MqttCallbackExtended() {
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {

        }

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
        }
    }), new IMqttActionListener() {
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("MQTT", "onSuccess:->连接成功");
        subscribe();
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("MQTT", "onFailure:->连接失败");
    }
});
```
ClientId用于标识设备，取设备唯一值或和服务器约定。

### 订阅
```java
AndMqtt.getInstance().subscribe(new MqttSubscribe()
    .setTopic("主题").setQos(0), new IMqttActionListener() {
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("MQTT", "onSuccess:->订阅成功");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("MQTT", "onFailure:->订阅失败");
    }
});
```
### 取消订阅
```java
AndMqtt.getInstance().unSubscribe(new MqttUnSubscribe()
    .setTopic("主题"), new IMqttActionListener() {
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("MQTT", "onSuccess:->取消订阅成功");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("MQTT", "onFailure:->取消订阅失败");
    }
});
```

### 发布
```java
AndMqtt.getInstance().publish(new MqttPublish()
    .setMsg("消息").setQos(0).setTopic("主题"), new IMqttActionListener() {
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("MQTT", "onSuccess:->发布成功");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("MQTT", "onFailure:->发布失败");
    }
});
```
### 断开连接
```
AndMqtt.getInstance().disConnect();
```

### 是否连接
```
boolean isConnect = AndMqtt.getInstance().isConnect();
```
**注意**
进行任何操作前都需保证MQTT是连接的，调用`isConnect()`方法获取是否连接



