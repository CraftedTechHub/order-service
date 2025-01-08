package com.KalaroApplication.KALARO_ORDERS;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class KalaroCenterApplicationTests {
    @Test
    public void contextLoads() {
    }
}
