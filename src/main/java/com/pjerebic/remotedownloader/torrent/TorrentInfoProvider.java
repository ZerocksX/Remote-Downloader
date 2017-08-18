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
        return torrents.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
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

    public static class MyTorrentInfo {
        private String torrentName;
        private TorrentSessionState state;
        private Torrent torrent;
        private BtClient client;

        public MyTorrentInfo(BtClient client) {
            this.torrent = client.getSession().getTorrent();
            this.torrentName = torrent.getName();
            this.client = client;
        }

        public String getTorrentName() {
            return torrentName;
        }

        public void setTorrentName(String torrentName) {
            this.torrentName = torrentName;
        }

        public TorrentSessionState getState() {
            return state;
        }

        public void setState(TorrentSessionState state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return String.format("%s%n%s", getTorrentName(), getState() == null ? "" : Utils.stateInfo(getState()));
        }

        @Override
        public int hashCode() {
            return torrent.getTorrentId().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if ( obj == null ) return false;
            if (!(obj instanceof MyTorrentInfo)) return false;
            MyTorrentInfo other = (MyTorrentInfo) obj;
            return torrent.getTorrentId().equals(other.torrent.getTorrentId());
        }

        public void stop(){
            client.stop();
        }
    }

}
