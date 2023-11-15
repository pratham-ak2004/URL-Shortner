package com.urlshotener.url.shortner.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Urls")
@Data
public class Url {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("shortUrl")
    private String shortUrl;

    @JsonProperty("longUrl")
    private String longUrl;

    public Url(String shortUrl, String longUrl) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }


}
