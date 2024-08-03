package com.example.service;
import com.example.model.Translation;
import com.example.repository.TranslationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class TranslationServiceTest {
    @Autowired
    private TranslationService translationService;

    @Autowired
    private TranslationRepository translationRepository;

    @Test
    public void testTranslate() {
        String ipAddress = "127.0.0.1";
        String inputText = "Hello world";
        String sourceLang = "en";
        String targetLang = "ru";

        String translatedText = translationService.translate(inputText, sourceLang, targetLang, ipAddress);

        assertEquals("Привет мир", translatedText);

        List<Translation> translations = (List<Translation>) translationRepository.findAll();
        assertEquals(1, translations.size());

        Translation translation = translations.get(0);
        assertEquals(ipAddress, translation.getIpAddress());
        assertEquals(inputText, translation.getSourceText());
        assertEquals(translatedText, translation.getTranslatedText());
    }
}
