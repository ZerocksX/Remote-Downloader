package com.pjerebic.remotedownloader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping({"/", "/home"})
    public String home(Model model){
        return "home";
    }
}
