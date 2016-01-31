package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.RestApiConfig;
import org.lucjross.uspresidential.dao.VoteDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(RestApiConfig.BASE_URI + "/vote")
public class VoteController {

    @Autowired private VoteDAO voteDAO;
    @Autowired private VoteValidator voteValidator;

    @RequestMapping(value = "/by-event", method = RequestMethod.GET)
    public Collection<Vote> getVotesByEvent(
            @RequestParam("event-id") Integer eventID) {
        Event event = new Event();
        event.setId(eventID);
        Collection<Vote> votes = voteDAO.getVotes(event);
        return votes;
    }

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(voteValidator);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<String> submit(
            @RequestBody Vote vote,
            BindingResult voteBR,
            Principal principal) {

        if (voteBR.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String username = principal.getName();
        vote.setUser_username(username);

        try {
            voteDAO.create(vote);
        }
        catch (DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
        // todo - return new Vote (to provide `created`)
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> update(
            @RequestBody Vote vote,
            BindingResult voteBR,
            Principal principal) {

        if (voteBR.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String username = principal.getName();
        vote.setUser_username(username);

        try {
            voteDAO.update(vote);
        }
        catch (Exception e) { // todo - catch specific exceptions
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Component
    static class VoteValidator implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return Vote.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "event_id", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "response", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "voteWeight", "field.required");
        }
    }
}