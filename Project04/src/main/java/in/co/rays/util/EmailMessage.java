package in.co.rays.util;

public class EmailMessage {

	private String to;
	private String subject;
	private String message;
	private int mesgType = TEXT_MESG;

	public static final int TEXT_MESG = 2;
	public static final int HTML_MESG = 2;

	public EmailMessage() {

	}

	public EmailMessage(String to, String subject, String message) {
		this.to = to;
		this.subject = subject;
		this.message = message;	
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMesgType() {
		return mesgType;
	}

	public void setMesgType(int mesgType) {
		this.mesgType = mesgType;
	}
}
