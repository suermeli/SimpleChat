package org.suermeli.simplechat.server;

import java.util.Date;
import java.util.Vector;

import org.suermeli.simplechat.server.LongAnswer;

class MyQuestionHandler extends QuestionHandler {

	String msg = null;
	
	public MyQuestionHandler(SimpleChatServer context) {
		super(context);
	}
	


	@Override
	protected LongAnswer determineLongAnswer() {
		
		return longAnswer;

	}

	
	@Override
	protected String determineShortAnswer() {
		return shortAnswer;
	}

	@Override
	protected Vector<Detail> determineDetails() {
		return details;
	}


	String shortAnswer = null;
	Vector<Detail> details = null;
	volatile LongAnswer longAnswer = null;

	volatile boolean busy = false;
	
	@Override
	protected void process(String msg) {

		for (;busy;);
		busy = true;
		this.msg = msg;
		shortAnswer = null;
		longAnswer = null;
		details = null;
		
		if (msg.equals("Ups")) {
			longAnswer = new LongAnswer("Okay, ich überlege mal...");
			
			new Thread() {
				
				public void run() {
					Detail d = Detail.create("Unglaublich!");
					longAnswer.addDetail(d);
					longAnswer.setAnswer(Detail.link(d, "Crazy") + " shit!");
					try {
						this.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					longAnswer.setComplete(true);
					busy = false;
					
				}
				
			}.start();

			
			return; };	
		if (msg.equals("ping")) shortAnswer = "pong";

		details = new Vector<Detail>();

		Detail d1 = Detail.create("Die Nachricht war: " + msg);
	    Detail d2 = Detail.create("Heute: " + new Date().toString());
		details.add(d1);
		details.add(d2);
		shortAnswer = ">>" + Detail.link(d1,msg) + "<< (" + Detail.link(d2, "Uhrzeit") + ")";
		busy = false;
	}
}
