package org.lucjross.uspresidential.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lucas on 12/28/2014.
 */
@RestController
public class ExampleController {

    @RequestMapping("/greeting")
    public String greetingPage() {
        return "greeting";
    }
}
