package com.e.store.product;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class ProductApplicationTests {

	@Test
	void text_alert() {
		String msg = "HelloWorld";
		Assertions.assertEquals("HelloWorld", msg);
	}

}
