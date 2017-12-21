## AndMqtt
android mqttclient

## 如何使用
### 1、初始化
建议在Application中初始化，避免持有Activity导致内存泄露

```java
MqttManager.getInstance().init(this);
```

### 2.设置消息监听
```java
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
        })
```

对收到的消息进行处理

### 3.连接服务器
```java
 MqttManager.getInstance().connect(new ConnectBuilder().setServer("服务器地址")
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
```java
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
```
ClientId用于标识设备，取设备唯一值或和服务器约定。

### 订阅
```java
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
```
### 取消订阅
```java
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
```

### 发布
```java
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
```





