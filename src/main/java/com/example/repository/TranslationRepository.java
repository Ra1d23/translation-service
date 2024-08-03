package com.example.repository;

import com.example.model.Translation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TranslationRepository extends CrudRepository<Translation, Long> {
}
