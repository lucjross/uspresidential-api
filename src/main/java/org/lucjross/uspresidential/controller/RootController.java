package org.lucjross.uspresidential.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lucas on 2/16/2015.
 */
@Controller
@RequestMapping("/")
public class RootController {

    @RequestMapping
    @ApiIgnore
    public String index() {
        return "sdoc.jsp";
    }
}
