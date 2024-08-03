package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.model.Translation;
import com.example.repository.TranslationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
@SpringBootTest
@SpringJUnitConfig
@AutoConfigureMockMvc
public class TranslationControllerTest {
    @Autowired
    private TranslationRepository translationRepository;



    @Test
    public void testSaveAndRetrieveTranslation() {
        // Сначала сохраняем запись
        Translation translation = new Translation();
        translation.setIpAddress("127.0.0.1");
        translation.setSourceText("Hello world");
        translation.setTranslatedText("Привет мир");
        translationRepository.save(translation);
        Long id = translation.getId(); // Получаем ID только что сохраненной записи
        assertNotNull(id); // Убедитесь, что ID не равен null

        // Извлекаем запись по ID
        Translation retrievedTranslation = translationRepository.findById(id).orElse(null);
        assertNotNull(retrievedTranslation); // Убедитесь, что запись найдена

        assertEquals("127.0.0.1", retrievedTranslation.getIpAddress());
        assertEquals("Hello world", retrievedTranslation.getSourceText());
        assertEquals("Привет мир", retrievedTranslation.getTranslatedText());
    }

    }

