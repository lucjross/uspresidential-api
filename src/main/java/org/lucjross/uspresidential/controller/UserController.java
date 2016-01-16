package org.lucjross.uspresidential.controller;

import org.lucjross.uspresidential.model.PrezUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by lucas on 1/15/16.
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public ResponseEntity<String> user(Principal user) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/public-api/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(PrezUser newUser) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
