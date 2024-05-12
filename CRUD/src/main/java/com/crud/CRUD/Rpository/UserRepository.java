package com.crud.CRUD.Rpository;

import com.crud.CRUD.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {

}
