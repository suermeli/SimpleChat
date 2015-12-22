package org.suermeli.simplechat.server;

class LongAnswer {

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
