package com.crud.CRUD.Repository;

import com.crud.CRUD.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Users findByEmail(String email);

}
