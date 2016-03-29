package stackdaddy.music_links;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.lang.StringUtils;
import stackdaddy.music_links.models.SearchDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by ryanstack on 3/28/16.
 */
public class RequestParameterDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        HttpRequest request = (HttpRequest) msg;
        QueryStringDecoder queryDecoder = new QueryStringDecoder(request.getUri());
        Map<String, List<String>> params = queryDecoder.parameters();
        String track  = StringUtils.join(params.get("track"), " ");
        String artist = StringUtils.join(params.get("artist"), " ");
        String album  = StringUtils.join(params.get("album"), " ");

        System.out.println("printing out request parameters");
        System.out.println(track);
        System.out.println(artist);
        System.out.println(album);

        //Is there a better way so I don't have to remember order of input
        SearchDetails searchDetails = new SearchDetails(artist, album, track);
        ctx.fireChannelRead(searchDetails);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
