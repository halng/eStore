package com.e.store.email.viewmodel.res;

import java.time.Instant;

import com.e.store.email.exception.ActiveAccountTimeOutException;
import com.google.gson.JsonObject;

public record AuthMessageVm(String email, String username, String activeToken, Long expiryDate) {

    public static AuthMessageVm fromJsonObject(JsonObject json) {
        if (json.has("expiryDate") && json.has("email") && json.has("username") && json.has("activeToken")) {
            Instant maxTime = Instant.now();
            Instant timeExpire = Instant.ofEpochSecond(json.get("expiryDate").getAsLong());
            if (timeExpire.isBefore(maxTime)) {
                throw new ActiveAccountTimeOutException("Send email active account failed. Time out expiryDate");
            }
            return new AuthMessageVm(json.get("email").getAsString(), json.get("username").getAsString(),
                json.get("activeToken").getAsString(), timeExpire.getEpochSecond());
        }
        throw new IllegalArgumentException("Message don't have enough data. Miss expiryDate field.");
    }

    public String getUrlConfirm() {
        return String.format("/active-account?token=%s&email=%s", activeToken, email);
    }
}
