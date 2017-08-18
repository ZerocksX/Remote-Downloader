package com.pjerebic.remotedownloader.torrent;

import bt.metainfo.Torrent;
import bt.metainfo.TorrentId;
import bt.runtime.BtClient;
import bt.torrent.TorrentSessionState;

import java.util.*;
import java.util.stream.Collectors;

public class TorrentInfoProvider {

    private static Map<TorrentId, MyTorrentInfo> torrents = Collections.synchronizedMap(new HashMap<>());

    public static List<MyTorrentInfo> getTorrentList() {
        List<MyTorrentInfo> list = torrents.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        list.sort(Comparator.comparing(MyTorrentInfo::getDateAdded).reversed());
        return list;
    }

    public synchronized static void addTorrent(BtClient client) {
        Torrent currentTorrent = client.getSession().getTorrent();
        torrents.put(
                currentTorrent.getTorrentId(),
                new MyTorrentInfo(client)
        );
    }

    public static void updateTorrentState(Torrent torrent, TorrentSessionState state) {
        MyTorrentInfo info = torrents.get(torrent.getTorrentId());
        info.setState(state);
        if(!torrent.getName().trim().equals("")){
            info.setTorrentName(torrent.getName().trim());
        }
        torrents.put(torrent.getTorrentId(), info); //todo: check if this is optional
    }

}
