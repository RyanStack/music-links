package stackdaddy.music_links;

import stackdaddy.music_links.utils.Config;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//  Example requests below
//  http://localhost:9090?track=Money+On+My+Mind&artist=Sam+Smith&album=In+The+Lonely+Hour
//  http://localhost:9090?track=Money%20On%20My%20Mind&artist=Sam%20Smith&album=In%20The%20Lonely%20Hour

public class Server 
{
	private final int port;
	static Logger LOGGER = LoggerFactory.getLogger(Server.class);
	
	public Server(Config conf) {
		this.port = conf.getPort();
	}
	
	public void start() throws Exception {
		 EventLoopGroup bossGroup = new NioEventLoopGroup();
		 EventLoopGroup workerGroup = new NioEventLoopGroup();
		 try {
			 ServerBootstrap b = new ServerBootstrap(); 
			 b.group(bossGroup, workerGroup)
			 	.channel(NioServerSocketChannel.class) 
			 	.localAddress(new InetSocketAddress(port))
			 	.childHandler(new ServerInitializer());
			 
			 ChannelFuture f = b.bind().sync();
			 LOGGER.info(RequestHandler.class.getName() + "started and listen on" + f.channel().localAddress());
			 f.channel().closeFuture().sync(); 
		 } finally { 
			 bossGroup.shutdownGracefully().sync(); 
		 }
	}

//	TODO: create a bash script to start project
//	java -jar music-links-0.0.1-SNAPSHOT-jar-with-dependencies.jar <port>
    public static void main( String[] args ) throws Exception
    {
    	if (args.length !=1) {
			LOGGER.error("Please adjust parameters");
			System.exit(1);
    	}
    	
    	Config conf = new Config();
    	conf.setPort(args[0]);
    		 
    	new Server(conf).start(); 
    }
}




	