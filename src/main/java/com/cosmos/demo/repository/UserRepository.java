package com.cosmos.demo.repository;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.cosmos.demo.entity.User;

@Repository
public interface UserRepository extends CosmosRepository<User, String> {
}
