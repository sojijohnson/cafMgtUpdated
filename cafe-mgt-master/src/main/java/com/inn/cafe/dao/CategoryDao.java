package com.inn.cafe.dao;

import com.inn.cafe.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryDao extends JpaRepository<Category,Integer> {



             List getAllCategory();

}
