package org.lucjross.uspresidential.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lucas on 1/9/16.
 */
@RestController
public class PageController {

    @Autowired
    protected UserDetailsService prezUserDetailsService;

    @RequestMapping("/homePage")
    public Map<String, Object> home() {
        Map<String, Object> model = new HashMap<>();
        
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @RequestMapping("/user")
    public ResponseEntity<String> user(Principal user) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
