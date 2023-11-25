package com.urlshotener.url.shortner.controller;

import com.urlshotener.url.shortner.hashing.UniqueHasing;
import com.urlshotener.url.shortner.models.Url;
import com.urlshotener.url.shortner.repo.SearchRepository;
import com.urlshotener.url.shortner.repo.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173","http://localhost:4173"})
@RestController
@AllArgsConstructor
@RequestMapping("/generate")
public class BusinessController {


    @Autowired
    private final UrlRepository urlRepository;
    @Autowired
    SearchRepository searchRepository;

    @Autowired
    UniqueHasing obj = new UniqueHasing();


    @PostMapping("/addUrl")
    public ResponseEntity addUrl(@RequestBody Url url){
        String longUrl = url.getLongUrl();
        String hashString = obj.generateUniqueHash(longUrl);
        Url newUrl = new Url(hashString,longUrl);
        System.out.println(newUrl);
        return ResponseEntity.ok(newUrl);
    }

}
