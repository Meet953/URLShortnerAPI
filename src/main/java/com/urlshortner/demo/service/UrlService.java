package com.urlshortner.demo.service;

import com.urlshortner.demo.Exception.NotFoundException;
import com.urlshortner.demo.entity.Url;
import com.urlshortner.demo.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlConversion conversion;

    public UrlService(UrlRepository urlRepository, UrlConversion conversion) {
        this.urlRepository = urlRepository;
        this.conversion = conversion;
    }

    public String getShortUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setLastUpdated(new Date());
        Url entity = urlRepository.save(url);
        return conversion.encode(entity.getId());
    }

    public String getOriginalUrl(String shortUrl) {
        long id = conversion.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no entity with " + shortUrl));
        entity.setLastUpdated(new Date());
        urlRepository.save(entity);
        return entity.getOriginalUrl();
    }

}
