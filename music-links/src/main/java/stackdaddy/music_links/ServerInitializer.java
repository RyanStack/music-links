package stackdaddy.music_links;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final int SIZE = 1048576;
	
	public ServerInitializer() {}
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(SIZE));
		pipeline.addLast(new RequestFilter());
		pipeline.addLast(new RequestParameterDecoder());
		pipeline.addLast(new GsonResponseEncoder());
		pipeline.addLast(new RequestHandler());
	}

}
