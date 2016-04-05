package stackdaddy.music_links;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import stackdaddy.music_links.handlers.GsonResponseEncoder;
import stackdaddy.music_links.handlers.RequestFilter;
import stackdaddy.music_links.handlers.RequestHandler;
import stackdaddy.music_links.handlers.RequestParameterDecoder;

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
