package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public void saveItems() {
        testRepository.save(new TestEntity("Test 1"));
        testRepository.save(new TestEntity("Test 2"));
        testRepository.save(new TestEntity("Test 3"));
    }

}
