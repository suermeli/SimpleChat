package org.suermeli.simplechat.server;

import org.suermeli.simplechat.server.LongAnswer;

class MyQuestionHandler extends QuestionHandler {

	public MyQuestionHandler(SimpleChatServer context) {
		super(context);
	}

	@Override
	protected LongAnswer determineLongAnswer(String msg) {
		LongAnswer longAnswer = new LongAnswer("Okay, ich überlege mal...");
		
		new Thread() {
			
			public void run() {
				
				longAnswer.setAnswer("Crazy shit!");
				try {
					this.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				longAnswer.setComplete(true);
				
			}
			
		}.start();
		
		return longAnswer;

	}

	@Override
	protected String determineShortAnswer(String msg) {
		if (msg.equals("Ups")) return null;	
		if (msg.equals("ping")) return "pong";	
		return "You said >>" + msg + "<<";
	}
}
