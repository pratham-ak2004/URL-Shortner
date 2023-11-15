package com.urlshotener.url.shortner.repo;

import com.urlshotener.url.shortner.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UrlRepository extends MongoRepository<Url, String> {
}
