package com.pjerebic.remotedownloader.controllers;

import bt.metainfo.Torrent;
import com.pjerebic.remotedownloader.torrent.TorrentList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadInfoController {
    @RequestMapping(value = "/info", params = "id")
    public String showInfo(@RequestParam(value = "id") int id, Model model){
        TorrentList.MyTorrentInfo torrent = TorrentList.getTorrent(id);
        return "info";
    }

}
