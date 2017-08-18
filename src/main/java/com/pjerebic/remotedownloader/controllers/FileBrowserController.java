package com.pjerebic.remotedownloader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileBrowserController {
    @RequestMapping(value = "/browse", method = RequestMethod.GET, params = "path")
    public String showFolder(@RequestParam("path") String path, Model model ){
        Path currentPath = Paths.get(path);
        if(!(Files.exists(currentPath) && Files.isDirectory(currentPath))){
            throw new RuntimeException("Not a valid path");
        }
        List<File> files = Arrays.stream(currentPath.toFile().listFiles()).collect(Collectors.toList());
        files.sort((b, a) -> {
            if (a.isDirectory()) {
                if (b.isDirectory()) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (b.isDirectory()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        if(currentPath.getParent()!=null){
            model.addAttribute("back", currentPath.getParent().toAbsolutePath().toString());
        }
        model.addAttribute("files", files);
        model.addAttribute("currentPath", path);
        return "fileBrowser";

    }
}
