package app.model;

public class JwtToken {
	private String algorithm;
	private String type;
	private String contentType;
	private String keyId;
	private String subject;
	
	public JwtToken withAlgorithm(String algorithm) {
		this.algorithm = algorithm;
		return this;
	}
	
	public JwtToken withType(String type) {
		this.type = type;
		return this;
	}
	
	public JwtToken withContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	
	public JwtToken withKeyId(String keyId) {
		this.keyId = keyId;
		return this;
	}
	
	public JwtToken withSubject(String subject) {
		this.subject = subject;
		return this;
	}
	
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
}
