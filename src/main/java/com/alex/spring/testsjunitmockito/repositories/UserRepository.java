package com.alex.spring.testsjunitmockito.repositories;

import com.alex.spring.testsjunitmockito.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
