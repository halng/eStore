package com.e.store.product.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ProductAttributeControllerTest extends AbstractControllerTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getAllAttributeTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/attribute").accept(
            MediaType.APPLICATION_JSON_VALUE).param("page", "1")).andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);
    }


}
