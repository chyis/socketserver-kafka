# socketserver-kafka
用Java netty 实现简单的socket 通讯，消费kafka消息队列
appserver 目录是netty的socket监听启动。
ServiceOrderConsumerAPI.java 是kafka的主要消费程序。

程序写的很简单。只是做个小演示希望各位有用。

以appserver为入口打成jar包，然后执行
java -jar yourjarfile.jar
执行后端口即可被监听。
