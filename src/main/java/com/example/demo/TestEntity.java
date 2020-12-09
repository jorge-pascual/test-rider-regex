package com.example.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@Table(schema = "test", name = "test_entity")
public class TestEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;

    public TestEntity(String name) {
        this.name = name;
    }

    public TestEntity() {
        this.name = "";
    }
}
