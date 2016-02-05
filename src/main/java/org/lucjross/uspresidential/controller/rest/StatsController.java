package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.RestApiConfig;
import org.lucjross.uspresidential.dao.StatsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(RestApiConfig.BASE_URI + "/stats")
public class StatsController {

    @Autowired private StatsDAO statsDAO;

    @RequestMapping(value = "/by-events", method = RequestMethod.GET)
    public List<Map<String, Object>> getStatsByEvents(
            @RequestParam("eventIds") List<Integer> eventIds) {

        return statsDAO.getStatsByEvents(eventIds);
    }
}
