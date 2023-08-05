package com.e.store.product.viewmodel.res;

public record ErrorResVm(String msg, int statusCode, String reason) {

    public String getLogMessage() {
        return "Error Message: " + this.msg + "\nStatus Code: " + this.statusCode
            + "\nReason: " + reason;
    }
}
