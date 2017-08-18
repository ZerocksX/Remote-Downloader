package com.pjerebic.remotedownloader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DrivePickerController {

    @RequestMapping(value = "/drives", method = RequestMethod.GET)
    public String showDrives(Model model){
        List<String> drives = Arrays.stream(File.listRoots()).map(file -> file.toPath().toAbsolutePath().toString()).collect(Collectors.toList());
        model.addAttribute("drives",drives);
        return "drives";
    }
}
