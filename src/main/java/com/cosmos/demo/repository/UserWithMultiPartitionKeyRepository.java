package com.cosmos.demo.repository;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.cosmos.demo.entity.UserWithMultiPartitionKey;

@Repository
public interface UserWithMultiPartitionKeyRepository extends CosmosRepository<UserWithMultiPartitionKey, String> {
}
