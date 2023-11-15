package com.urlshotener.url.shortner.controller;


import com.urlshotener.url.shortner.repo.SearchRepository;
import com.urlshotener.url.shortner.repo.UrlRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/redirect")
@CrossOrigin(origins = "http://localhost:5173")
public class RedirectingController {

    @Autowired
    UrlRepository urlRepository;
    @Autowired
    SearchRepository searchRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/{target}", method = RequestMethod.GET)
    public void redirectToTwitter(HttpServletResponse response , @PathVariable String target) throws IOException {
        response.setStatus(HttpStatus.FOUND.value());

        String redirectTo = searchRepository.findTargetUrl(target);

        response.setHeader("Location", redirectTo);

        response.getWriter().println("Redirecting to " + redirectTo);

        System.out.println("Redirected to : " + redirectTo);
    }

}
