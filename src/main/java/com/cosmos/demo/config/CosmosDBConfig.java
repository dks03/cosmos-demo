package com.cosmos.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;

@Configuration
@EnableCosmosRepositories(basePackages = "com.cosmos.demo.repository")
public class CosmosDBConfig extends AbstractCosmosConfiguration {

    @Value("${azure.cosmos.database}")
    private String database;

    @Value("${azure.cosmos.endpoint}")
    private String endpoint;

    @Value("${azure.cosmos.key}")
    private String key;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    public CosmosClientBuilder getCosmosClientBuilder() {
        DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
        GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
        return new CosmosClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(key))
                .directMode(directConnectionConfig, gatewayConnectionConfig);
    }

}
