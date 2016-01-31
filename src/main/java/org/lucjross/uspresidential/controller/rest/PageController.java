package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.RestApiConfig;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.PrezUser;
import org.lucjross.uspresidential.model.PrezUserOptionalsLabels;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    @RequestMapping(RestApiConfig.PUBLIC_API + "/userDetailLabels")
    @Cacheable
    public Map<String, ?> userDetailLabels() {
        return prezUserOptionalsLabels.labels();
    }


    // todo - the below can be generalized into one

    @RequestMapping(RestApiConfig.PUBLIC_API + "/voteResponseLabels")
    @Cacheable
    public Map<String, String> voteOptionLabels() {
        return Arrays.asList(Vote.Response.values()).stream()
                .collect(Collectors.toMap(Enum::name, Vote.Response::getText));
    }

    @RequestMapping(RestApiConfig.PUBLIC_API + "/eventImportanceLabels")
    @Cacheable
    public Map<String, String> eventImportanceEnum() {
        return Arrays.asList(Event.Importance.values()).stream()
                .collect(Collectors.toMap(Enum::name, Event.Importance::getText));
    }

    @RequestMapping(RestApiConfig.PUBLIC_API + "/eventCategoryLabels")
    @Cacheable
    public Map<String, String> eventCategoryEnum() {
        return Arrays.asList(Event.Category.values()).stream()
                .collect(Collectors.toMap(Enum::name, Event.Category::getText));
    }
}
