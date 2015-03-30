package org.lucjross.uspresidential.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lross on 3/30/15.
 */
@Controller
@ApiIgnore
public class PageController {

    @RequestMapping("/")
    public String index(
            @RequestParam(value="name", required=false, defaultValue="World") String name,
            Model model) {
        model.addAttribute("name", name);
        return "?";
    }
}
