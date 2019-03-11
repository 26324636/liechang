package model;
/*
 * 异常处理
 * */
public class LieChangException extends RuntimeException {

	private static final long serialVersionUID = 4813071046628929423L;

	public LieChangException() {
		super();

	}

	public LieChangException(String message, Throwable cause) {
		super(message, cause);
	}

	public LieChangException(String message) {
		super(message);
	}

	public LieChangException(Throwable cause) {
		super(cause);
	}

}
