package com.cosmos.demo;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.models.PartitionKeyBuilder;
import com.cosmos.demo.entity.User;
import com.cosmos.demo.entity.UserWithMultiPartitionKey;
import com.cosmos.demo.repository.UserRepository;
import com.cosmos.demo.repository.UserWithMultiPartitionKeyRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CosmosDemoApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserWithMultiPartitionKeyRepository userMultiPartRepository;

    public static void main(String[] args) {
        SpringApplication.run(CosmosDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Cosmos Start:\n\n\n\n\n");

        User user = null;
        UserWithMultiPartitionKey userMultiPart = null;
        UserWithMultiPartitionKey userMultiPartDuplicateItem = null;

        buildObjects(user, userMultiPart, userMultiPartDuplicateItem);

        // Step 1(a): save item
        userRepository.save(user);

        // Step 1(b): fetch using findAll()
        userRepository.findAll().forEach(u -> {
            System.out.println(u.getEmail());
        });

        // Step 1(c): fetch using findById() - using partition key
        Optional<User> optionalUserFindById = userRepository.findById("1", new PartitionKey("New York"));
        if (optionalUserFindById.isPresent()) {
            System.out.println(optionalUserFindById.get().getEmail() + " : " + optionalUserFindById.get().getFirstName());
        }

        // Step 2(a): save item with multiple partition key
        userMultiPartRepository.save(userMultiPart);

        // Step 2(b): fetch using findAll()
        userMultiPartRepository.findAll().forEach(ump -> {
            System.out.println(ump.getEmail());
        });

        // building partition key for UserWithMultiPartition - multiple attributes
        PartitionKey partitionKey = new PartitionKeyBuilder()
                .add("New Delhi")
                .add("India")
                .build();

        // Step 2(c): fetch using findById() - using partition key
        Optional<UserWithMultiPartitionKey> optionalUserWithMultipartKey = userMultiPartRepository.findById("1", partitionKey);

        if (optionalUserWithMultipartKey.isPresent()) {
            System.out.println(optionalUserWithMultipartKey.get().getEmail() + " : " + optionalUserWithMultipartKey.get().getFirstName());
        }


        // Step 3: save duplicate item
        // will get below exception in case entry already exists in container with same unique key combination
        // firstName, lastName and email -- unique key defined at the time of container creation
        // com.azure.spring.data.cosmos.exception.CosmosAccessException: Failed to upsert item
        // compare userMultiPart and userMultiPartDuplicateItem objects
        userMultiPartRepository.save(userMultiPartDuplicateItem); // throws exception

        System.out.println("\n\n\n\n\nCosmos End.");
    }
    
    public void buildObjects(User user, UserWithMultiPartitionKey userMultiPart,
            UserWithMultiPartitionKey userMultiPartDuplicateItem) {

        user = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .age(25)
                .email("john.doe@email.com")
                .city("New York")
                .build();

        userMultiPart = UserWithMultiPartitionKey.builder()
                .id("1")
                .firstName("Jack")
                .lastName("Ryan")
                .email("jack.ryan@email.com")
                .city("New Delhi")
                .country("India")
                .build();

        userMultiPartDuplicateItem = UserWithMultiPartitionKey.builder()
                .id("2")
                .firstName("Jack")
                .lastName("Ryan")
                .email("jack.ryan@email.com")
                .city("New Delhi")
                .country("India")
                .build();
    }
}
