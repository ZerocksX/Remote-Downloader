package com.pjerebic.remotedownloader.torrent;

import bt.metainfo.Torrent;
import bt.metainfo.TorrentId;
import bt.torrent.TorrentSessionState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TorrentList {

    private static List<MyTorrentInfo> torrents = Collections.synchronizedList(new ArrayList<>());

    public static MyTorrentInfo getTorrent(int index) {
        return torrents.get(index);
    }

    public static void addTorrent(Torrent torrent) {
        torrents.add(
                new MyTorrentInfo()
        );
    }

    public static class MyTorrentInfo {
        private String torrentName;
        private TorrentSessionState state;

    }

}
