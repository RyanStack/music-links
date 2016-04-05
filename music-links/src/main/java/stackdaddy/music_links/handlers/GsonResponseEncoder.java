package stackdaddy.music_links.handlers;

import stackdaddy.music_links.models.FormattedResult;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ryanstack on 3/28/16.
 */
public class GsonResponseEncoder extends ChannelOutboundHandlerAdapter {

	static Logger LOGGER = LoggerFactory.getLogger(GsonResponseEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {

        List<Future<FormattedResult>> results = (List<Future<FormattedResult>>) msg;

        //TODO: Need to properly organize data and better error catching
		StringBuilder sb = new StringBuilder();

		for (Future<FormattedResult> future : results) {
			try {
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		        String json = gson.toJson(future.get());
                sb.append(json);
   			} catch (ExecutionException e) {
   				ctx.writeAndFlush("temp error");
   				e.printStackTrace();
   			} catch (InterruptedException e) {
   				ctx.writeAndFlush("temp error");
				e.printStackTrace();
   			}
		}

		LOGGER.info("printing results");
		LOGGER.info(sb.toString());

		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(sb.toString().getBytes()));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);;
    }
}
