package com.e.store.auth.viewmodel.res;

import java.time.Instant;

public record AuthMessageVm(String email, String username, String activeToken, Instant expiryDate) {

}
