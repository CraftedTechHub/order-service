package com.KalaroApplication.KALARO_ORDERS;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {TestcontainersConfiguration.class, KalaroOrdersApplication.class})
@SpringBootTest
class KalaroOrdersApplicationTests {

	@Test
	void contextLoads() {
	}

}
