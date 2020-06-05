package in.lendenindia.backend.code.exception;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = 3096070253911533621L;
	
	public UserServiceException(String message) {
		super(message);
	}

}
