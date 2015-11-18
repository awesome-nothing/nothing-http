package io.nothing;

public class NothingException extends Exception{
	private static final long serialVersionUID = 1L;
	private String mExtra;

	public NothingException(String message) {
		super(message);
	}

	public NothingException(String message, String extra) {
		super(message);
		mExtra = extra;
	}

	public String getExtra() {
		return mExtra;
	}
}
