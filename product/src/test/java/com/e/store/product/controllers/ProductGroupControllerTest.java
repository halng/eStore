package com.e.store.product.controllers;

import com.e.store.product.viewmodel.req.ProductGroupCreateReqVm;
import com.e.store.product.viewmodel.req.ProductGroupUpdateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ProductGroupControllerTest extends AbstractControllerTest {

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
  }

  @Test
  void createNewGroup_whenDataInvalid() throws Exception {
    ProductGroupCreateReqVm reqVm = new ProductGroupCreateReqVm("Group 1", "new description");

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/product/group")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(this.mapToJson(reqVm)))
            .andReturn();

    Assertions.assertEquals(400, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"message\":\"Product Group Name: Group 1 already"
            + " exists\",\"statusCode\":400,\"reason\":\"\",\"logMessage\":\"Error Message: Product"
            + " Group Name: Group 1 already exists\\n"
            + "Status Code: 400\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void createNewGroup_whenDataValid() throws Exception {
    ProductGroupCreateReqVm reqVm = new ProductGroupCreateReqVm("new name", "new description");

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/product/group")
                    .content(this.mapToJson(reqVm))
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals(201, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"status\":\"CREATED\",\"message\":\"Create new group with name: new name"
            + " successfully\",\"logMessage\":\"201 CREATED - Create new group with name: new name"
            + " successfully\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void updateGroupTest() throws Exception {
    ProductGroupUpdateReqVm reqVm =
        new ProductGroupUpdateReqVm("g1", "ENABLED", "new name", "new description");
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/v1/product/group")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(this.mapToJson(reqVm)))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"status\":\"OK\",\"message\":\"Update product group name: new name"
            + " successfully!\",\"logMessage\":\"200 OK - Update product group name: new name"
            + " successfully!\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void deleteGroupTest() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/api/v1/product/group/g3")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"status\":\"OK\",\"message\":\"Delete group successfully\",\"logMessage\":\"200 OK -"
            + " Delete group successfully\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void deleteGroup_whenGroupContainProduct() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/api/v1/product/group/g2")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals(400, result.getResponse().getStatus());
    Assertions.assertEquals(
        "{\"message\":\"Group Group 2 contain product. Please make sure this group empty before"
            + " delete\",\"statusCode\":400,\"reason\":\"\",\"logMessage\":\"Error Message: Group"
            + " Group 2 contain product. Please make sure this group empty before delete\\n"
            + "Status Code: 400\"}",
        result.getResponse().getContentAsString());
  }

  @Test
  void getProductGroupTest() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/product/group")
                    .param("page", "1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    PagingResVm listProductGroupResVm =
        this.mapFromJson(result.getResponse().getContentAsString(), PagingResVm.class);
    Assertions.assertEquals(3, listProductGroupResVm.totalItems());
    Assertions.assertEquals(1, listProductGroupResVm.totalPages());
  }

  @Test
  void getAllProductGroupTest() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/product/group/all")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals(200, result.getResponse().getStatus());
    CommonProductResVm[] commonProductResVms =
        this.mapFromJson(result.getResponse().getContentAsString(), CommonProductResVm[].class);
    Assertions.assertEquals(2, commonProductResVms.length);
  }
}
