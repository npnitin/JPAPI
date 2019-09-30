package com.example.repository;

import com.example.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findByEmailAndPassword(String email,String password);
    User findByEmail(String email);
}
