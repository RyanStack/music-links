package stackdaddy.music_links;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestHandlerTest {
	
//	@Test
//	public void testRequestHandler() {
//		EmbeddedChannel embedder = new EmbeddedChannel(new RequestHandler());
//		ByteBuf bytebuf = Unpooled.copiedBuffer("stackDaddyTest", CharsetUtil.US_ASCII);
//		FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, 
//                HttpMethod.GET, "http://localhost:9000", bytebuf); 
//		assertFalse(embedder.writeInbound(request));
//		assertTrue(embedder.finish());
//		assertNull(embedder.readInbound()); 
//		assertTrue(embedder.readOutbound().equals("stackDaddyTest"));
//		
//	}

}
