package com.urlshotener.url.shortner.repo;


import java.util.List;

public interface SearchRepository {

    List<String> findByShortUrl(String shortUrl);
    List<String> findByLongUrl(String longUrl);

    String findTargetUrl(String longUrl);
}
