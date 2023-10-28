package com.e.store.api.viewmodel;

import java.util.Date;
import org.springframework.http.HttpStatusCode;

public record ResVm(HttpStatusCode statusCode, String err, String errDetail, Throwable cause, Date date) {

}
