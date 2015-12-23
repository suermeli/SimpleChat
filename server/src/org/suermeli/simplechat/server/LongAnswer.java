package org.suermeli.simplechat.server;

import java.util.Vector;

class LongAnswer {

	private Vector<Detail> details;
	
	
	public Vector<Detail> getDetails() {
		return details;
	}

	public void setDetails(Vector<Detail> details) {
		this.details = details;
	}
	
	public void addDetail(Detail detail) {
		details.addElement(detail);
	}
	
	private String intro;
	private boolean complete;
	
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public LongAnswer(String intro) {
		this.intro = intro;
		this.complete = false;
		this.details = new Vector<Detail>();
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	private String answer;
	
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public boolean isComplete() {
		return complete;
	}
	
	
}
