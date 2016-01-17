package org.lucjross.uspresidential.controller;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.lucjross.uspresidential.dao.UserDAO;
import org.lucjross.uspresidential.model.PrezUser;
import org.lucjross.uspresidential.model.PrezUserOptionalsLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by lucas on 1/15/16.
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public ResponseEntity<String> user(Principal user) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired private PrezUserValidator prezUserValidator;
    @Autowired private PrezUserOptionalsValidator prezUserOptionalsValidator;

    @InitBinder("userAttr")
    private void initBinderUser(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(prezUserValidator);
    }

    @InitBinder("userOptionalsAttr")
    private void initBinderUserOptionals(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(prezUserOptionalsValidator);
    }

    @RequestMapping(value = "/public-api/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(
            @Valid @ModelAttribute("userAttr") PrezUser.Form userReq,
            BindingResult userBR,
            @Valid @ModelAttribute("userOptionalsAttr") PrezUser.Optionals userOptionalsReq,
            BindingResult userOptionalsBR) {

        if (userOptionalsBR.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userBR.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @Component
    static class PrezUserValidator implements Validator {

        private static final int MINIMUM_PASSWORD_LENGTH = 6;
        private static final EmailValidator emailValidator = new EmailValidator();

        @Autowired
        private UserDAO userDAO;

        @Override
        public boolean supports(Class<?> clazz) {
            return PrezUser.Form.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required");

            PrezUser.Form u = (PrezUser.Form) target;

            if (errors.getFieldError("email") == null && ! emailValidator.isValid(u.getEmail(), null)) {
                errors.rejectValue("email", "field.invalid");
            }

            if (u.getPassword() != null && u.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
                errors.rejectValue("password", "field.min.length",
                        new Object[] { MINIMUM_PASSWORD_LENGTH },
                        "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
            }

            // todo - this needs to be part of the service layer
//            if (errors.getFieldError("username") == null) {
//                try {
//                    userDAO.find(u.getUsername());
//                    // if no exception, then user already exists
//                    errors.rejectValue("username", "field.taken");
//                }
//                catch (IncorrectResultSizeDataAccessException e) {
//                    // ok to add user
//                }
//            }
        }
    }

    /**
     * The form elements for this validator's fields are all "select" fields,
     * so this just makes sure that no one can invoke the API independent
     * of the form with bad selection values.
     */
    @Component
    static class PrezUserOptionalsValidator implements Validator {

        @Autowired
        PrezUserOptionalsLabels labels;

        @Override
        public boolean supports(Class<?> clazz) {
            return PrezUser.Optionals.class.isAssignableFrom(clazz);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void validate(Object target, Errors errors) {
            PrezUser.Optionals optionals = (PrezUser.Optionals) target;
            Method[] methods = PrezUser.Optionals.class.getDeclaredMethods();
            for (Method m : methods) {
                String methodName = m.getName();
                if (methodName.startsWith("get")) {
                    String labelsKey;
                    try {
                        labelsKey = (String) m.invoke(optionals);
                    }
                    catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }

                    if (labelsKey != null && ! labelsKey.isEmpty()) {
                        // then a value was supplied and must be validated

                        // "getBirthDate" --> "birthDate"
                        String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

                        List<Object> pairList = (List<Object>) labels.labels().get(fieldName);
                        boolean keyFound = pairList.stream().anyMatch(o ->
                                ((Map<Object, Object>) o).containsKey(labelsKey));
                        if (! keyFound) {
                            errors.rejectValue("fieldName", "field.invalid");
                        }
                    }
                }
            }
        }
    }
}
