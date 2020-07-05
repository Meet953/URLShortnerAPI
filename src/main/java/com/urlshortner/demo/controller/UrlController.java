package com.urlshortner.demo.controller;

import com.urlshortner.demo.Exception.ServerException;
import com.urlshortner.demo.Exception.UrlException;
import com.urlshortner.demo.entity.Url;
import com.urlshortner.demo.service.UrlService;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Cacheable;
import java.net.URI;

@RestController
@RequestMapping("/url")
public class UrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> convertToShortUrl(@RequestParam(value = "v") String originalUrl) {
        if (!new UrlValidator(new String[] { "http", "https" }).isValid(originalUrl)) {
            throw new UrlException("Invalid Url");
        }
        String shortUrl = urlService.getShortUrl(originalUrl);
        if (shortUrl == null) {
            LOGGER.error("Failed to create short url: {}.", originalUrl);
            throw new ServerException();
        }
        return new ResponseEntity<>(shortUrl, HttpStatus.CREATED);
    }

    @GetMapping(value = "{shortUrl}")
    public ResponseEntity<Void> getOriginalUrl(@PathVariable String shortUrl) {
        String url = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

}
