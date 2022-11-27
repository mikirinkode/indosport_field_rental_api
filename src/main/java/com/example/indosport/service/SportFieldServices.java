package com.example.indosport.service;

import com.example.indosport.model.SportField;

import java.util.List;

public interface SportFieldServices {
    List<SportField> getAll();

    List<SportField> getUserSportField(Long id);

    void save(SportField sportField);

    SportField getById(Long id);

    void deleteViaId(long id);

    List<SportField> search(String keyword, Long userId);
}
