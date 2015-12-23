package org.suermeli.simplechat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.suermeli.simplechat.server.LongAnswer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class QuestionHandler implements HttpHandler {
	
	private SimpleChatServer context;
	
	public QuestionHandler(SimpleChatServer context) {this.context = context;}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String query = java.net.URLDecoder.decode(exchange.getRequestURI().getQuery(), "UTF-8");
		
		System.out.println(query);
		
	    Map<String, String> result = new HashMap<String, String>();
	    for (String param : query.split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
		
		String response = determineShortAnswer(result.get("msg"));
		int responseCode = 200;
		
		if (response == null) {
			LongAnswer longAnswer = determineLongAnswer(result.get("msg"));
			responseCode = 201;
			response = context.registerLongAnswer(longAnswer) + " " + longAnswer.getIntro();
		}

		SimpleChatServer.send(exchange,responseCode,response);
		
	}

	protected abstract LongAnswer determineLongAnswer(String msg);

	protected abstract String determineShortAnswer(String msg);
	
	
}
