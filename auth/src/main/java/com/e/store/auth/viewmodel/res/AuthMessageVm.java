package com.e.store.auth.viewmodel.res;

import com.google.gson.Gson;

public record AuthMessageVm(String email, String username, String activeToken, Long expiryDate) {
    public String toStringWithJsonFormat() {
        return new Gson().toJson(this);
    }
}
