package com.pjerebic.remotedownloader.torrent;

public interface Downloader {

    void download(String link, String path);
    
}
