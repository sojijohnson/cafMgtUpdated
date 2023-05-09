package com.inn.cafe.dao;

import com.inn.cafe.Model.User;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository

public interface UserDao extends JpaRepository<User,Integer> {



   User findByEmailId(@Param("email")String email);

    User findByName(@Param("name") String userName);
         List<UserWrapper> getAllUser();

         @Transactional
         @Modifying
    Integer update( @Param("status") String status, @Param("id") Integer id);

    List<String> getAllAdmin();

    User findByEmail(String email);
    //User findByEmail(String email);
}
