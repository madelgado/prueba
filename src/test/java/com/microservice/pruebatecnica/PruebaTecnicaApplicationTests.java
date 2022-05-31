package com.microservice.pruebatecnica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class) public class PruebaTecnicaApplicationTests {

    @Autowired private ApplicationContext applicationContext;

    @Test public void contextLoads () {
        assertNotNull(applicationContext);
    }

}