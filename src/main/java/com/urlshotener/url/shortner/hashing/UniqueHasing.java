package com.urlshotener.url.shortner.hashing;

import com.urlshotener.url.shortner.models.Url;
import com.urlshotener.url.shortner.repo.SearchRepository;
import com.urlshotener.url.shortner.repo.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UniqueHasing {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private SearchRepository searchRepository;

    private static ArrayList<Character> hashMap = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

    private static  String hashShortner(String hash){
        String ans = "";
        short val;
        for(int i = 0 ; i < hash.length() ; i += 2){
            val = (short) ((hashMap.indexOf(hash.charAt(i)) + hashMap.indexOf(hash.charAt(i+1))));
            val = (short) (val%62);
            ans += hashMap.get(val);
        }
        return ans;
    }

    private static String generateBase62Hash(String message) throws Exception{
        MessageDigest digester = MessageDigest.getInstance("MD5");
        byte[] md5Result = digester.digest(message.getBytes());


        BigInteger no = new BigInteger(1,md5Result);

        String hashtext = no.toString(16);
        while (hashtext.length() < 32){
            hashtext = "0" + hashtext;
        }

        return hashShortner(hashtext);

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
