package com.urlshotener.url.shortner.hashing;

import com.urlshotener.url.shortner.models.Url;
import com.urlshotener.url.shortner.repo.SearchRepository;
import com.urlshotener.url.shortner.repo.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import static com.urlshotener.url.shortner.hashing.CustomBase62.encodeBase62;

@Component
public class UniqueHasing {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private SearchRepository searchRepository;


    // Crypto hashing MD5
    private static String generateBase62Hash(String message) throws Exception{
        MessageDigest digester = MessageDigest.getInstance("MD5");
        byte[] md5Result = digester.digest(message.getBytes());


        BigInteger no = new BigInteger(1,md5Result);

        String hashtext = no.toString(16);
        while (hashtext.length() < 32){
            hashtext = "0" + hashtext;
        }

        // Hash shortner base62
        return encodeBase62(hashtext.getBytes());

    }

    public String generateUniqueHash(String message){

        List<String> shortUrls = searchRepository.findByLongUrl(message);
        if(!shortUrls.isEmpty()){
            System.out.println("URL already present in Database");
            return shortUrls.get(0);
        }
        String newHashString = "" , temp = message;
        List<String> longUrls;
        long num = 0;

        try {
            do {
                newHashString = generateBase62Hash(temp);
                longUrls = searchRepository.findByShortUrl(newHashString);

                temp = message + num++;
            }while (!longUrls.isEmpty() || longUrls.contains(newHashString));
        }catch (Exception e){
            System.out.println("Exception encountered at unique hashing : " + e);
        }
        Url url = new Url(newHashString,message);
        this.urlRepository.save(url);
        System.out.println("Saved url : " + url);
        return newHashString;
    }
}
