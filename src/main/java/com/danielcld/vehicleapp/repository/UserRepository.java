package com.danielcld.vehicleapp.repository;

import com.danielcld.vehicleapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByUserName(String userName);
    User findById(int id);
}