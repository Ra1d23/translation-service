package com.example.controller;

import com.example.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/translate")
public class TranslationController {

    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public String translate(@RequestParam String text,
                            @RequestParam String sourceLang,
                            @RequestParam String targetLang,
                            @RequestHeader("X-FORWARDED-FOR") String ipAddress) {
        return translationService.translate(text, sourceLang, targetLang, ipAddress);
    }
}
