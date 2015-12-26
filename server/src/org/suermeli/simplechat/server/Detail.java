package org.suermeli.simplechat.server;

import java.util.Vector;

public class Detail {
	
	private static volatile int currentDetailID = 0;
	
	public static Detail create() {
		
		return create("");
	}

	public static Detail create(String text) {
		return new Detail((new Integer(currentDetailID++)).toString(), text);
	}

	String id;
	String text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Detail(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}


	public static String encodeDetails(Vector<Detail> detailVector) {
		String result = "";
		if (detailVector != null) {
			
			String delim = "";
			for (int i = 0; i < detailVector.size(); ++i) {
				Detail d = detailVector.elementAt(i);
				result += delim + "{\"id\" : " + (new Integer(d.getId())).toString() + ", \"text\" : \"" + d.getText() + "\"}";
				delim = ",";
			}
			
		}
		
		
		return result;
	}

	public static String link(Detail d, String msg, String title) {
		return "[" + msg + "](#" + d.getId() + " \"" + title + "\")";
	}

	public static String link(Detail d, String msg) {
		return link(d,msg,"Click for Details");
	}

}
