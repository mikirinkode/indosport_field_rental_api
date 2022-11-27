package com.example.indosport.repository;

import com.example.indosport.model.SportField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportFieldRepository extends JpaRepository<SportField, Long> {

    @Query(value = "select * from field_sport where field_sport.name like %:keyword%", nativeQuery = true)
    List<SportField> findByKeyword(@Param("keyword") String keyword);
}
