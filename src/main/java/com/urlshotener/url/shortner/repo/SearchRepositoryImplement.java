package com.urlshotener.url.shortner.repo;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SearchRepositoryImplement implements SearchRepository{
    @Autowired
    MongoClient mongoClient;

    @Override
    public List<String> findByShortUrl(String shortUrl) {
        final List<String> ans = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("newDB");
        MongoCollection<Document> collection = database.getCollection("Urls");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$match", new Document("shortUrl", shortUrl))));

        result.forEach(document -> {
            String shortUrlValue = document.getString("shortUrl");
            ans.add(shortUrlValue);
        });

        return ans;
    }

    @Override
    public List<String> findByLongUrl(String longUrl){
        final List<String> ans = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("newDB");
        MongoCollection<Document> collection = database.getCollection("Urls");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$match", new Document("longUrl", longUrl))));

        result.forEach(document -> {
            String longUrlValue = document.getString("shortUrl");
            ans.add(longUrlValue);
        });

        return ans;
    }

    @Override
    public String findTargetUrl(String key){
        final List<String> ans = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("newDB");
        MongoCollection<Document> collection = database.getCollection("Urls");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$match", new Document("shortUrl", key))));

        result.forEach(document -> {
            String longUrlValue = document.getString("longUrl");
            ans.add(longUrlValue);
        });

        return ans.get(0);
    }
}
