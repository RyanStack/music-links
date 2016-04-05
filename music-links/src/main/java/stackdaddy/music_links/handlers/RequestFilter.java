package stackdaddy.music_links.handlers;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestFilter extends ChannelInboundHandlerAdapter {

    static Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        LOGGER.info("Received a request..............");

        HttpRequest request = (HttpRequest) msg;

        boolean isCorrectRequest = isCorrectBrowserRequest(request);
        if (!isCorrectRequest) {
            //Providing a response for secondary requests in HTTP pipeline
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("".getBytes()));
            ctx.write(response);
        } else {
            ctx.fireChannelRead(request);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private static boolean isCorrectBrowserRequest(HttpRequest request) {
        String uri = request.getUri();
        LOGGER.info(uri);
        if (uri.startsWith("/?track=") || uri.startsWith("/?artist=") || uri.startsWith("/?album=")) {
            return true;
        }
        return false;
    }
} 



