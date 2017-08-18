package com.pjerebic.remotedownloader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Home controller
 */
@Controller
public class HomeController {
    /**
     * Processes get request on '/' and '/home'
     * @param model model to display
     * @return template name
     */
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(@RequestParam(value = "storage", required = false, defaultValue = "") String storage, Model model){
        model.addAttribute("storage", storage);
        return "home";
    }
}
