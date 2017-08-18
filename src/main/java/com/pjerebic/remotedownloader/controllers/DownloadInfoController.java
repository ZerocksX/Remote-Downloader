package com.pjerebic.remotedownloader.controllers;

import com.pjerebic.remotedownloader.torrent.TorrentInfoProvider;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadInfoController {
//    @RequestMapping(value = "/info", params = "id")
//    public String showInfo(@RequestParam(value = "id") int id, Model model){
//
//        return "info";
//    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String showAllInfo(Model model){
        model.addAttribute("torrentList", TorrentInfoProvider.getTorrentList());
        return "info";
    }

}
