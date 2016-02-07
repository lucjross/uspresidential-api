package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.RestApiConfig;
import org.lucjross.uspresidential.dao.PresidentDAO;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lucas on 1/10/2015.
 */
@RestController
@RequestMapping(RestApiConfig.BASE_URI + "/president")
public class PresidentController {

    @Autowired
    private PresidentDAO presidentDAO;

    @RequestMapping(method=RequestMethod.GET)
    public President getPresident(
            @RequestParam("id") Integer id) {
        President key = new President();
        key.setId(id);
        President president = presidentDAO.find(key);
        return president;
    }

    @RequestMapping(value = "/all-by-id", method = RequestMethod.GET)
    @Cacheable
    public Map<Integer, President> getPresidents() {
        List<President> presidents = presidentDAO.getPresidents();
        return presidents.stream()
                .collect(Collectors.toMap(President::getId, Function.identity()));
    }
}
