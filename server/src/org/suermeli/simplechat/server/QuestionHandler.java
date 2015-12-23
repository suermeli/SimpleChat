package org.suermeli.simplechat.server;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.suermeli.simplechat.server.LongAnswer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class QuestionHandler implements HttpHandler {
	
	private SimpleChatServer context;
	
	public QuestionHandler(SimpleChatServer context) {this.context = context;}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String query = URLDecoder.decode(exchange.getRequestURI().getRawQuery(),"UTF-8");
		System.out.println(query);
		//System.out.println(URLDecoder.decode(exchange.getRequestURI().getQuery(),"UTF-8"));
		
		//System.out.println(exchange.getRequestURI().getQuery());
		//System.out.println(exchange.getRequestURI().getRawQuery());
		
	    Map<String, String> result = new HashMap<String, String>();
	    for (String param : query.split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
		process(result.get("msg"));
		String response = determineShortAnswer();
		int responseCode = 200;
		
		
		if (response != null) {
			response = response.replace("\\","\\\\").replace("\"","\\\"");
			response = "{\"answer\" : \"" + response + "\", \"details\" : [" + Detail.encodeDetails(determineDetails()) + "]}";
		}
		
		if (response == null) {
			LongAnswer longAnswer = determineLongAnswer();
			responseCode = 201;
			response = "{\"intro\" : \"" + longAnswer.getIntro().replace("\\","\\\\").replace("\"","\\\"") + "\", \"address\" : \"" + context.registerLongAnswer(longAnswer) + "\"}";
		} 

		SimpleChatServer.send(exchange,responseCode,response);
		
	}
	
	protected abstract void process(String msg);
	protected abstract String determineShortAnswer();
	protected abstract Vector<Detail> determineDetails();
	protected abstract LongAnswer determineLongAnswer();

	
	
}
