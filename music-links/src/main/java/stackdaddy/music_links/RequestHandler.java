package stackdaddy.music_links;

import stackdaddy.music_links.models.SearchDetails;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;

import stackdaddy.music_links.APIExecutorService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

public class RequestHandler extends ChannelInboundHandlerAdapter {

	@Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("received a request..............");
		//Handle request 
		HttpRequest request = (HttpRequest) msg;
		QueryStringDecoder queryDecoder = new QueryStringDecoder(request.getUri());
		Map<String, List<String>> params = queryDecoder.parameters();
		String track  = StringUtils.join(params.get("track"), " ");
		String artist = StringUtils.join(params.get("artist"), " ");
		String album  = StringUtils.join(params.get("album"), " ");

		//Is there a better way so I don't have to remember order of input
		SearchDetails searchDetails = new SearchDetails(artist, album, track);
		
		APIExecutorService executor = new APIExecutorService();
		//This is the results of the api calls
		List<Future<String>> results = executor.runQueries(searchDetails);
		
//		
		for (Future<String> future : results) {
			try {
				System.out.println(future.get());
				ctx.write(future.get());
   			} catch (ExecutionException e) {
   				ctx.write("Fuck bitches, get money");
   				e.printStackTrace();
   			} catch (InterruptedException e) {
   				ctx.write("Fuck bitches, get money");
   				e.printStackTrace();
   			}
		}
			
		//This is where we write the results
//		ctx.write("stackDaddyTest"); 
	 }
	
	@Override
	 public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
		.addListener(ChannelFutureListener.CLOSE); 
	 }
	
	@Override
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close(); 
	 }
} 



