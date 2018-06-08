package com.jdy.appserver;

import io.netty.channel.ChannelHandlerContext;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.jdy.collections.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import com.jdy.collections.Actions;
import com.jdy.serviceorder.KafkaBrokerInfo;
import com.jdy.serviceorder.KafkaTopicPartitionInfo;
import com.jdy.serviceorder.ServiceOrderConsumerAPI;
import com.jdy.collections.LogOut;

import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.apache.logging.log4j.core.config.ConfigurationSource;  
import org.apache.logging.log4j.core.config.Configurator;
/*
 * 处理TextWebSocketFrame
 * 
 */

public class AppServerSocketFrameHandler extends ChannelInboundHandlerAdapter {
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Active");
        //super.channelActive(ctx);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext responseHandle, Object msg) throws Exception {
        String txt = msg.toString();
		System.out.println("Client:"+txt+" reviced");
		//初始化消息队列
		if(JsonUtils.isJsonString(txt)){
				Actions action = JsonUtils.fromJson(txt,Actions.class );
				String actionType = action.getactiontype();
				Object data = action.getdata();
				String hotelid = action.gethotelid();
				String roomid = action.getroomid();
				
				if(actionType.equals("heartbeat")) {
					String topic = "ROOM-"+hotelid+"-"+roomid;
					ServiceOrderConsumerAPI example = new ServiceOrderConsumerAPI();
			        long maxReads = 1;
			        int partitionID = 0;
					KafkaTopicPartitionInfo topicPartitionInfo = new KafkaTopicPartitionInfo(topic, partitionID);
				    List<KafkaBrokerInfo> seeds = new ArrayList<KafkaBrokerInfo>();
				    seeds.add(new KafkaBrokerInfo("127.0.0.1", 9092));
				    //seeds.add(new KafkaBrokerInfo("172.*.*.*", 9092));
				    //seeds.add(new KafkaBrokerInfo("172.*.*.*", 9092));
				    //seeds.add(new KafkaBrokerInfo("172.*.*.*", 9092));
				    try {
				        example.run(maxReads, topicPartitionInfo, seeds, responseHandle);
				    } catch (Exception e) {
				        e.printStackTrace();
				    }
				    // 获取该topic所属的所有分区ID列表
				    //System.out.println(example.fetchTopicPartitionIDs(seeds, topic, 100000, 64 * 1024, "client-id"));
				    
					String resultData = "[top]{\"errorno\":0,\"message\":\"ok\",\"actiontype\":\"heartbeat\",\"data\":\"\"}[end]";
					byte[] datas = resultData.getBytes();
					ByteBuf buf_out = Unpooled.copiedBuffer(datas);
					
					responseHandle.writeAndFlush(buf_out);
				}else{
					String resultData = "[top]{\"errorno\":0,\"message\":\"ok\",\"actiontype\":\"heartbeat\",\"data\":\"\"}[end]";
					byte[] datas = resultData.getBytes();
					ByteBuf buf_out = Unpooled.copiedBuffer(datas);
					
					responseHandle.writeAndFlush(buf_out);
				}
		} else {
			String resultData = "[top]{\"result\":\"json decode error\"}[end]";
			byte[] datas = resultData.getBytes();
			ByteBuf buf_out = Unpooled.copiedBuffer(datas);
			
			responseHandle.writeAndFlush(buf_out);
		}
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
       // super.handlerAdded(ctx);

        System.out.println(
                "KgtvServerHandler.handlerAdded()" + ctx.channel().id());
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        //super.handlerRemoved(ctx);
        //PushPool.remove(ctx.channel().id().asLongText());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Read Complete");
        //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        //ctx.flush();
        //ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
