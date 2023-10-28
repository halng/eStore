package com.e.store.media.viewmodel.res;

public record ErrorResVm(String msg, int statusCode, String reason) {

	public String getLogMessage() {
		return "Error Message: " + this.msg + "\nStatus Code: " + this.statusCode;
	}
}
