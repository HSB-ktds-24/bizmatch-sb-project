package com.ktdsuniversity.edu.bizmatch.common.exceptions.member;


public class SignUpFailException extends RuntimeException {

	private static final long serialVersionUID = 3596880578291996715L;
	
	public SignUpFailException(String message) {
		super(message);
	}
}
