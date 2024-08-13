package com.cosmos.demo.entity;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;

import lombok.Builder;
import lombok.Data;

@Container(containerName = "user_multi_part_key", autoCreateContainer = false,
                hierarchicalPartitionKeyPaths = { "city", "/country" })
@Data
@Builder
public class UserWithMultiPartitionKey {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String country;
}
