package com.e.store.product.controllers;

import com.e.store.product.constant.Action;
import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.ListProductAttributeResVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ProductAttributeControllerTest extends AbstractControllerTest {
    ProductAttributeCreateReqVm updateModel;
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        updateModel = new ProductAttributeCreateReqVm("hehe", "test update attribute");

    }

    @Test
    void getAttributeTest() throws Exception {
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/product/attribute").accept(MediaType.APPLICATION_JSON_VALUE)
                .param("page", "1")).andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);

        String responseContent = result.getResponse().getContentAsString();
        ListProductAttributeResVm listProductAttributeResVm = this.mapFromJson(responseContent, ListProductAttributeResVm.class);
        Assertions.assertEquals(4, listProductAttributeResVm.totalAttributes());
        Assertions.assertEquals(1, listProductAttributeResVm.totalPages());
    }
    @Test
    void getAllAttributeTest() throws Exception {
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/product/attribute/all")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        CommonProductResVm[] commonProductResVmList = this.mapFromJson(result.getResponse().getContentAsString(), CommonProductResVm[].class);

        Assertions.assertEquals(2, commonProductResVmList.length); // ENABLE only
    }
    @Test
    void updateAttributeTest_whenAttributeIsRemoved() throws Exception {
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/product/attribute/att4").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapToJson(updateModel))).andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(400, statusCode);
        Assertions.assertEquals("{\"msg\":\"Attribute removed. Please try again!\",\"statusCode\":400,\"reason\":\"\",\"logMessage\":\"Error Message: Attribute removed. Please try again!\\nStatus Code: 400\"}", result.getResponse().getContentAsString());
    }
    @Test
    void updateAttributeTest() throws Exception {
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/product/attribute/att2").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapToJson(updateModel))).andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("{\"status\":\"OK\",\"message\":\"Update attribute successfully\",\"logMessage\":\"200 OK - Update attribute successfully\"}", result.getResponse().getContentAsString());
    }
    @Test
    void updateStatusAttribute() throws Exception{
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders
                .patch("/api/v1/product/attribute")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("action", Action.ENABLE.toString().toLowerCase())
                .param("attId", "att1"))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("{\"status\":\"OK\",\"message\":\"Update status successfully\",\"logMessage\":\"200 OK - Update status successfully\"}", result.getResponse().getContentAsString());
    }
    @Test
    void deleteAttributeTest() throws Exception{
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                    .delete("/api/v1/product/attribute/att1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("{\"status\":\"OK\",\"message\":\"Delete attribute successfully\",\"logMessage\":\"200 OK - Delete attribute successfully\"}", result.getResponse().getContentAsString());
    }
    @Test
    void createAttributeTest_whenNameExist() throws Exception{
        ProductAttributeCreateReqVm createModel = new ProductAttributeCreateReqVm("Att - 1", "");
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/api/v1/product/attribute")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(this.mapToJson(createModel)))
            .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(400, statusCode);
        Assertions.assertEquals("{\"msg\":\"Product Attribute with name Att - 1 already exists!\",\"statusCode\":400,\"reason\":\"\",\"logMessage\":\"Error Message: Product Attribute with name Att - 1 already exists!\\nStatus Code: 400\"}", result.getResponse().getContentAsString());
    }
    @Test
    void createAttributeTest() throws  Exception {
        ProductAttributeCreateReqVm createModel = new ProductAttributeCreateReqVm("Att - 5", "");
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/v1/product/attribute")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapToJson(createModel)))
        .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(201, statusCode);
        Assertions.assertTrue(result.getResponse().getContentAsString().startsWith("{\"status\":\"CREATED\",\"message\":\"Create new attribute successfully - id:"));
    }
}
