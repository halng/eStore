package com.e.store.product.controllers;

import com.e.store.product.ProductApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractControllerTest {
  protected MockMvc mockMvc;
  @Autowired WebApplicationContext webApplicationContext;
  private ObjectMapper mapper;

  protected void setUp() {

    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    this.mapper = new ObjectMapper();
    SecurityContextHolder.getContext()
        .setAuthentication(
            new Authentication() {
              @Override
              public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
              }

              @Override
              public Object getCredentials() {
                return null;
              }

              @Override
              public Object getDetails() {
                return null;
              }

              @Override
              public Object getPrincipal() {
                return null;
              }

              @Override
              public boolean isAuthenticated() {
                return false;
              }

              @Override
              public void setAuthenticated(boolean isAuthenticated)
                  throws IllegalArgumentException {}

              @Override
              public String getName() {
                return "SYSTEM";
              }
            });
  }

  protected String mapToJson(Object obj) throws JsonProcessingException {
    return this.mapper.writeValueAsString(obj);
  }

  protected <T> T mapFromJson(String json, Class<T> entity) throws IOException {
    return this.mapper.readValue(json, entity);
  }
}
