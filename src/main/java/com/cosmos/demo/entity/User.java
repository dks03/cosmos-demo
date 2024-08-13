package com.cosmos.demo.entity;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import lombok.Builder;
import lombok.Data;

@Container(containerName = "user", autoCreateContainer = false)
@Data
@Builder
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    @PartitionKey
    private String city;
}
