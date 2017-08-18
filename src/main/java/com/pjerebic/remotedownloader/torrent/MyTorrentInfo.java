package com.pjerebic.remotedownloader.torrent;

import bt.metainfo.Torrent;
import bt.runtime.BtClient;
import bt.torrent.TorrentSessionState;

import java.util.Date;

public class MyTorrentInfo {
    private String torrentName;
    private TorrentSessionState state;
    private Torrent torrent;
    private BtClient client;
    private Date dateAdded;

    public MyTorrentInfo(BtClient client) {
        this.torrent = client.getSession().getTorrent();
        this.torrentName = torrent.getName();
        this.client = client;
        this.dateAdded = new Date();
    }

    public Date getDateAdded() {
        return dateAdded;
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
        return String.format("%s%n%s", getTorrentName() , getState() == null ? "" : Utils.stateInfo(getState()));
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
