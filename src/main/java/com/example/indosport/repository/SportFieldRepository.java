package com.example.indosport.repository;

import com.example.indosport.model.SportField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportFieldRepository extends JpaRepository<SportField, Long> {

    @Query(value = "select * from sport_field where sport_field.name like %:keyword% AND sport_field.user_id = :userId", nativeQuery = true)
    List<SportField> findByKeyword(@Param("keyword") String keyword, @Param("userId") Long userId);

    @Query(value = "select * from sport_field where sport_field.user_id = :userId", nativeQuery = true)
    List<SportField> getUserSportField(@Param("userId") Long userId);
}
