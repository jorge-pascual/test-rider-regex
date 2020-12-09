package com.example.demo.glue;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Config {
    @Before
    public void setUp(){
        System.out.println("Spring Context Initialized For Executing Cucumber Tests ---------------------");
    }
}
