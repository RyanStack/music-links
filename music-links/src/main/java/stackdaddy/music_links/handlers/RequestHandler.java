package stackdaddy.music_links.handlers;

import stackdaddy.music_links.APIExecutorService;
import stackdaddy.music_links.models.FormattedResult;
import stackdaddy.music_links.models.SearchDetails;

import java.util.List;
import java.util.concurrent.Future;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RequestHandler extends ChannelInboundHandlerAdapter {

	@Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) {

		SearchDetails searchDetails = (SearchDetails) msg;
		APIExecutorService executor = new APIExecutorService();
		//This is the results of the api calls.  All results are present.  InvokeAll is blocking.
		List<Future<FormattedResult>> results = executor.runQueries(searchDetails);
		ctx.writeAndFlush(results);

	 }
	
	@Override
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	 }
} 



