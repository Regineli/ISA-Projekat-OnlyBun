package rs.ac.uns.ftn.informatika.jpa.exception;


//Custom izuzetak
public class ResourceConflictException extends RuntimeException {
	private static final long serialVersionUID = 1791564636123821405L;

	private Integer resourceId;

	public ResourceConflictException(Integer integer, String message) {
		super(message);
		this.setResourceId(integer);
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer integer) {
		this.resourceId = integer;
	}

}