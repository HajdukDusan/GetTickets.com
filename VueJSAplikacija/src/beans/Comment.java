package beans;

public class Comment {
	
	public enum CommentStatus {
		APPROVED,DENIED,PENDING
	};
	private CommentStatus status;
	private String user;
	private String manifestation;
	private String text;
	private String grade;
	public Comment() {
		// TODO Auto-generated constructor stub
	}
	
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus status) {
		this.status = status;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getManifestation() {
		return manifestation;
	}
	public void setManifestation(String manifestation) {
		this.manifestation = manifestation;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Comment(String user, String manifestation, String text, String grade) {
		super();
		this.user = user;
		this.manifestation = manifestation;
		this.text = text;
		this.grade = grade;
		this.status = CommentStatus.PENDING;
	}
	public Comment(String user, String manifestation, String text, String grade,CommentStatus cs) {
		super();
		this.user = user;
		this.manifestation = manifestation;
		this.text = text;
		this.grade = grade;
		this.status = cs;
	}

}
