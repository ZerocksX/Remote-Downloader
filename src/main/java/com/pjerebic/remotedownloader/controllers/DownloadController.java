package com.pjerebic.remotedownloader.controllers;

import com.pjerebic.remotedownloader.torrent.DownloadType;
import com.pjerebic.remotedownloader.torrent.Downloader;
import com.pjerebic.remotedownloader.torrent.DownloaderProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Downloader controller
 */
@Controller
public class DownloadController {
    /**
     * Processes post request on '/download'
     *
     * @param link  torrent link
     * @param model model
     * @return template name
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String processDownloadLink(@RequestParam("link") String link, Model model) {
        link = link.trim();
        if (!link.isEmpty()) {
            model.addAttribute("link", link);
        }
        Downloader downloader = DownloaderProvider.getDownloader(DownloadType.Magnet);
        if (downloader != null) {
            downloader.download(link, "C:\\Users\\Pavao\\Documents\\test");
        } else {
            System.out.println("Could not initialize downloader");
        }
        return "home";
    }
}
