package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.model.PrezUser;
import org.lucjross.uspresidential.model.PrezUserDetailLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lucas on 1/9/16.
 */
@RestController
public class PageController {

    @Autowired
    private PrezUserDetailLabels prezUserDetailLabels;

    @RequestMapping("/homePage")
    public Map<String, ?> home(Authentication auth) {

        PrezUser user = (PrezUser) auth.getPrincipal();

        Assert.isNull(user.getPassword());

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);

        return map;
    }

    @RequestMapping("/public-api/userDetailLabels")
    public Map<String, ?> userDetailLabels() {
        return prezUserDetailLabels.labels();
    }
}
