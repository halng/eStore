package com.e.store.email.viewmodel.method;

import static junit.framework.TestCase.assertEquals;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

import com.e.store.email.exception.ActiveAccountTimeOutException;
import com.e.store.email.viewmodel.res.AuthMessageVm;
import com.google.gson.JsonObject;

public class AuthMessageMethodTest {

    @Test
    public void fromJsonObject_shouldThrowException_WhenMissingField () {
        JsonObject object = new JsonObject();
        object.addProperty("username", "name");
        object.addProperty("activeToken", "1hsd-nbvejqa");

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
            AuthMessageVm.fromJsonObject(object);
        });

        assertEquals(exception.getMessage(), "Message don't have enough data. Miss expiryDate field.");
    }

    @Test
    public void fromJsonObject_shouldThrowException_WhenTimeOut () {
        JsonObject object = new JsonObject();
        object.addProperty("username", "name");
        object.addProperty("activeToken", "1hsd-nbvejqa");
        object.addProperty("expiryDate", Instant.now().getEpochSecond() - 100);
        object.addProperty("email", "1hsd-nbvejqa");

        ActiveAccountTimeOutException exception = Assert.assertThrows(ActiveAccountTimeOutException.class, () -> {
            AuthMessageVm.fromJsonObject(object);
        });

        assertEquals(exception.getMessage(), "Send email active account failed. Time out expiryDate");
    }

    @Test
    public void fromJsonObject_shouldReturnViewModel_WhenValidData () {
        JsonObject object = new JsonObject();
        object.addProperty("username", "name");
        object.addProperty("activeToken", "1hsd-nbvejqa");
        object.addProperty("expiryDate", Instant.now().getEpochSecond() + 86400L);
        object.addProperty("email", "test@gmail.com");

        AuthMessageVm expected = AuthMessageVm.fromJsonObject(object);

        assertEquals(expected.email(), "test@gmail.com");
        assertEquals(expected.activeToken(), "1hsd-nbvejqa");
        assertEquals(expected.username(), "name");
    }

    @Test
    public void getUrlActiveAccountTest(){
        AuthMessageVm authMessageVm = new AuthMessageVm("email", "username", "123-344", 10000L);
        assertEquals(authMessageVm.getUrlConfirm(), String.format("/active-account?token=%s&email=%s", "123-344", "email"));
    }


}
