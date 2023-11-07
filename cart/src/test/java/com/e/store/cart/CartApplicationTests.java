package com.e.store.cart;

import com.e.store.cart.check.CheckWorkflow;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CartApplicationTests {

  CheckWorkflow check = new CheckWorkflow();
  CartApplication cartApplication = new CartApplication();

  @Test
  void getMessage_whenHaveParams_shouldReturnRightValue() {
    String msg = "Me";
    String expectResult = "Hello Me";
    String actualResult = check.getMessage(msg);
    Assert.assertEquals(expectResult, actualResult);
  }

  @Test
  void getMessage_whenHaveNullValue_shouldReturnRightValue() {
    String msg = "";
    String expectResult = "Hello ";
    String actualResult = check.getMessage(msg);
    Assert.assertEquals(expectResult, actualResult);
  }

  @Test
  void getSum_whenHaveDiffType_shouldReturnBiggerValue() {
    int num = 1;
    int num2 = 2;
    var actualResult = check.getSum(num, num2);
    Assert.assertEquals(3, actualResult);
  }

  @Test
  void getMsg() {
    Assert.assertEquals("Hello To The World!", cartApplication.hello());
  }
}
