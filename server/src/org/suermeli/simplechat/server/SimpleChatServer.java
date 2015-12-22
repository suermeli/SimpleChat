package org.suermeli.simplechat.server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class SimpleChatServer {

	private class ServerThread implements Runnable {

		private SimpleChatServer context;
		
		public ServerThread(SimpleChatServer context) {
			this.context = context;
		}
		
		@Override
		public void run() {

			context.getServer().start();
			
		}
		
	}
	
	
	private class AnswerHandler implements HttpHandler {

		SimpleChatServer context;
		LongAnswer longAnswer;
		
		private AnswerHandler(SimpleChatServer context, LongAnswer longAnswer) {
			this.context = context;
			this.longAnswer = longAnswer;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {

			int responseCode = 202;
			String response = "Processing...";
			if (longAnswer.isComplete()) {
				responseCode = 200;
				response = longAnswer.getAnswer();
			}
	
			SimpleChatServer.send(exchange,responseCode,response);
			
		}

		
	}
	
	
	private HttpServer server;
	private int currentAnswer;
	private Thread serverThread;
	private String address;
	private QuestionHandler questionHandler;
	
	
	
	
	public static void send(HttpExchange exchange, int responseCode, String response) throws IOException {
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
		exchange.sendResponseHeaders(responseCode, response.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}



	public SimpleChatServer() throws IOException {
		this(2428);
	}




	public void registerQuestionHandler(QuestionHandler questionHandler) {
		this.questionHandler = questionHandler;
		server.createContext("/ask", this.questionHandler);
	}



	public SimpleChatServer(int port) throws IOException {
		server = HttpServer.create(new InetSocketAddress(port),0);
		server.setExecutor(null);
		currentAnswer = 0;
		address = "http://localhost:" + (new Integer(port)).toString();
	}


	public Thread start() {
		
		serverThread = new Thread(new ServerThread(this));
		serverThread.start();
		return serverThread;
	}


	public String registerLongAnswer(LongAnswer longAnswer) {
		++currentAnswer;
		server.createContext("/answers/"+(new Integer(currentAnswer)).toString(), new AnswerHandler(this, longAnswer));
		return address+"/answers/"+(new Integer(currentAnswer)).toString();
	}

	
	public static void main(String[] args) {
		
		try {
			SimpleChatServer scs = new SimpleChatServer();
			QuestionHandler qs = new MyQuestionHandler(scs);
			scs.registerQuestionHandler(qs);
			scs.start();
			System.out.println("Created Server");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	


	public HttpServer getServer() {
		return server;
	}




	public void setServer(HttpServer server) {
		this.server = server;
	}




	public int getCurrentAnswer() {
		return currentAnswer;
	}




	public void setCurrentAnswer(int currentAnswer) {
		this.currentAnswer = currentAnswer;
	}




	public Thread getServerThread() {
		return serverThread;
	}




	public void setServerThread(Thread serverThread) {
		this.serverThread = serverThread;
	}
	
	
}
