package stackdaddy.music_links;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
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
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.handler.codec.DecoderResult;

public class RequestHandler extends ChannelInboundHandlerAdapter {

	@Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) {


		//Handle request
		HttpRequest request = (HttpRequest) msg;
		System.out.println(msg);


		QueryStringDecoder queryDecoder = new QueryStringDecoder(request.getUri());

		Map<String, List<String>> params = queryDecoder.parameters();
		String track  = StringUtils.join(params.get("track"), " ");
		String artist = StringUtils.join(params.get("artist"), " ");
		String album  = StringUtils.join(params.get("album"), " ");
		System.out.println(track);
		System.out.println(artist);
		System.out.println(album);


		//Is there a better way so I don't have to remember order of input
		SearchDetails searchDetails = new SearchDetails(artist, album, track);

		APIExecutorService executor = new APIExecutorService();

		//This is the results of the api calls.  All results are present.  InVoke all is blocking.
		List<Future<String>> results = executor.runQueries(searchDetails);

		//Need to properly organize data and better error catching
		StringBuilder sb = new StringBuilder();
		for (Future<String> future : results) {
			try {
				System.out.println(future.get());
				sb.append(future.get());
				sb.append("\n");
   			} catch (ExecutionException e) {
   				ctx.writeAndFlush("temp error");
   				e.printStackTrace();
   			} catch (InterruptedException e) {
   				ctx.writeAndFlush("temp error");
				e.printStackTrace();
   			}
		}

		System.out.println("write before writing a flushing");
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(sb.toString().getBytes()));
		ctx.write(response);
			

	 }

	//channelReadComplete is called when no RequestFilter but not with it
	
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



