package com.example.service;
import com.example.model.Translation;
import com.example.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class TranslationService {
    private static final int MAX_THREADS = 10;
    private final TranslationRepository translationRepository;
    private final RestTemplate restTemplate;
    private final ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

    @Autowired
    public TranslationService(TranslationRepository translationRepository, RestTemplate restTemplate) {
        this.translationRepository = translationRepository;
        this.restTemplate = restTemplate;
    }

    public String translate(String text, String sourceLang, String targetLang, String ipAddress) {
        List<String> words = List.of(text.split("\\s+"));
        List<Future<String>> futures = new ArrayList<>();

        for (String word : words) {
            Future<String> future = executorService.submit(() -> callTranslationApi(word, sourceLang, targetLang));
            futures.add(future);
        }

        String translatedText = futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "";
                    }
                })
                .collect(Collectors.joining(" "));

        translationRepository.save(new Translation(null, ipAddress, text, translatedText));
        return translatedText;
    }

    private String callTranslationApi(String word, String sourceLang, String targetLang) {
        String url = String.format("https://api.translation.service/translate?text=%s&source=%s&target=%s", word, sourceLang, targetLang);
        return restTemplate.getForObject(url, String.class);
    }
}
