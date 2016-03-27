package stackdaddy.music_links;

import io.netty.handler.codec.http.DefaultFullHttpResponse;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;


import io.netty.handler.codec.http.FullHttpResponse;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class RequestFilter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("received a request..............");

//      Handle request
        HttpRequest request = (HttpRequest) msg;

        boolean isCorrectRequest = isCorrectBrowserRequest(request);
        if (!isCorrectRequest) {
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("".getBytes()));
            ctx.write(response);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private static boolean isCorrectBrowserRequest(HttpRequest request) {
        String uri = request.getUri();
        System.out.println(uri);
        if (uri.startsWith("/?track=") || uri.startsWith("/?artist=") || uri.startsWith("/?album=")) {
            return true;
        }
        return false;
    }
} 



