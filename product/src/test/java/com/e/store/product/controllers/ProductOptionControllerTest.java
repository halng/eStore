package com.e.store.product.controllers;

import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductOptionResVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ProductOptionControllerTest extends AbstractControllerTest {

  ProductOptionCreateReqVm productOptionCreateReqVm;

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
  }

  @Test
  void createNewOption_whenDataInvalid() throws Exception {
    productOptionCreateReqVm = new ProductOptionCreateReqVm("option 1", "", "");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/product/option")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(this.mapToJson(productOptionCreateReqVm)))
            .andReturn();

    Assertions.assertEquals(400, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"msg\":\"Product Option with name option 1 already exists!\",\"statusCode\":400,\"reason\":\"\",\"logMessage\":\"Error Message: Product Option with name option 1 already exists!\\nStatus Code: 400\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void createNewOption_whenDataValid() throws Exception {
    productOptionCreateReqVm = new ProductOptionCreateReqVm("option 4", "", "");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/product/option")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(this.mapToJson(productOptionCreateReqVm)))
            .andReturn();

    Assertions.assertEquals(201, result.getResponse().getStatus());
    Assertions.assertTrue(
        result
            .getResponse()
            .getContentAsString()
            .startsWith(
                "{\"status\":\"CREATED\",\"message\":\"Create new option successfully - id: "));
  }

  @Test
  void getOptionTest() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/product/option")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .param("page", "1"))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    PagingResVm<ProductOptionResVm> listProductOptionResVm =
        this.mapFromJson(result.getResponse().getContentAsString(), PagingResVm.class);
    Assertions.assertEquals(3L, listProductOptionResVm.totalItems());
    Assertions.assertEquals(1, listProductOptionResVm.totalPages());
  }

  @Test
  void getAllOptionTest() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/product/option/all")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    CommonProductResVm[] listCommon =
        this.mapFromJson(result.getResponse().getContentAsString(), CommonProductResVm[].class);
    Assertions.assertEquals(3, listCommon.length);
  }

  @Test
  void updateOption_whenOptionIdInvalid() throws Exception {
    productOptionCreateReqVm = new ProductOptionCreateReqVm("option 4", "", "");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/v1/product/option/ooop1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.mapToJson(productOptionCreateReqVm)))
            .andReturn();

    Assertions.assertEquals(404, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"msg\":\"Option with id ooop1 not found\",\"statusCode\":404,\"reason\":\"\",\"logMessage\":\"Error Message: Option with id ooop1 not found\\nStatus Code: 404\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void updateOption_whenDataValid() throws Exception {
    productOptionCreateReqVm = new ProductOptionCreateReqVm("option 4", "", "");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/v1/product/option/o1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.mapToJson(productOptionCreateReqVm)))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"status\":\"OK\",\"message\":\"Update option option 4 successfully\",\"logMessage\":\"200 OK - Update option option 4 successfully\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void deleteOption_whenOptionInUse() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/api/v1/product/option/o1")
                    .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    Assertions.assertEquals(400, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"msg\":\"This option still in use. Please try again\",\"statusCode\":400,\"reason\":\"\",\"logMessage\":\"Error Message: This option still in use. Please try again\\nStatus Code: 400\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void deleteOption_whenOptionNotUse() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/api/v1/product/option/o2")
                    .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"status\":\"OK\",\"message\":\"Delete option option 2 successfully\",\"logMessage\":\"200 OK - Delete option option 2 successfully\"}",
        result.getResponse().getContentAsString());
  }
}
