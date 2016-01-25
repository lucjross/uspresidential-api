package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.model.PrezUser;
import org.lucjross.uspresidential.model.PrezUserOptionalsLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 1/9/16.
 */
@RestController
public class PageController {

    @Autowired
    private PrezUserOptionalsLabels prezUserOptionalsLabels;

    @RequestMapping("/home-auth")
    public Map<String, ?> home(Authentication auth) {

        PrezUser user = (PrezUser) auth.getPrincipal();

        Assert.isNull(user.getPassword());

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);

        return map;
    }

    @RequestMapping("/public-api/userDetailLabels")
    public Map<String, ?> userDetailLabels() {
        return prezUserOptionalsLabels.labels();
    }
}
