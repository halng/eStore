package com.e.store.auth.viewmodel.res;

public record ErrorVm(String message, String cause, String code) {

	@Override
	public String toString() {
		return "ErrorVm{" +
			"message='" + message + '\'' +
			", cause='" + cause + '\'' +
			", code='" + code + '\'' +
			'}';
	}
}
