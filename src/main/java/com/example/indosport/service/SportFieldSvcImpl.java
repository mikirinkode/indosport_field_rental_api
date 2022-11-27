package com.example.indosport.service;

import com.example.indosport.model.SportField;
import com.example.indosport.repository.SportFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SportFieldSvcImpl implements SportFieldServices {

    @Autowired private SportFieldRepository repository;

    @Override public List<SportField> getAll() {
        return repository.findAll();
    }
    @Override
    public List<SportField> getUserSportField(Long id) {
        return repository.getUserSportField(id);
    }
    @Override public void save(SportField sportField)
    {
        repository.save(sportField);
    }

    @Override public SportField getById(Long id)
    {
        Optional<SportField> optional = repository.findById(id);
        SportField sportField = null;
        if (optional.isPresent())
            sportField = optional.get();
        else
            throw new RuntimeException(
                    "Field Sport not found for id : " + id);
        return sportField;
    }

    @Override public void deleteViaId(long id)
    {
        repository.deleteById(id);
    }

    @Override
    public List<SportField> search(String keyword, Long userId) {
        return repository.findByKeyword(keyword, userId);
    }
}
