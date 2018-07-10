## AndMqtt
android mqttclient


在项目级别build.gradle添加：
```
maven { url "https://repo.eclipse.org/content/repositories/paho-snapshots/" }
```
在module级别build.gradle添加：
```
implementation 'com.rairmmd:andmqtt:1.0.5'
implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0'
implementation 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'
```

## 如何使用
### 1、初始化
建议在Application中初始化，避免持有Activity导致内存泄露

```
AndMqtt.getInstance().init(this);
```

### 2.设置消息监听
```
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
        })
```

对收到的消息进行处理

### 3.连接服务器
```
 AndMqtt.getInstance().connect(new MqttConnect().setServer("服务器地址")
                .setPort(端口号), new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.i("Rair", "(MainActivity.java:51)-onSuccess:->连接成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.i("Rair", "(MainActivity.java:56)-onFailure:->连接失败");
            }
        });
```

也可以直接链式调用
```
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
                 .setAutoReconnect(true)
                 .setCleanSession(true)
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
```
ClientId用于标识设备，取设备唯一值或和服务器约定。

### 订阅
```
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
```
### 取消订阅
```
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
```

### 发布
```
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
进行任何操作前都需保证MQTT是连接的  
可用isConnect()判断一下



