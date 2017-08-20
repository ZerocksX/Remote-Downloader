package com.pjerebic.remotedownloader.torrent;

import bt.metainfo.Torrent;
import bt.runtime.BtClient;
import bt.torrent.TorrentSessionState;

import java.util.Date;

public class MyTorrentInfo implements DownloadInfo {
    private String torrentName;
    private TorrentSessionState state;
    private Torrent torrent;
    private BtClient client;
    private Date dateAdded;

    private Double averageSpeed;
    private Long downloadSize;
    private Long estimatedRemainingTime;
    private Double downloadedPercentage;
    private Double currentSpeed;

    private Date lastStateUpdate;
    private Long lastStateDownloaded;

    public MyTorrentInfo(BtClient client) {
        this.torrent = client.getSession().getTorrent();
        this.torrentName = torrent.getName();
        this.client = client;
        this.dateAdded = new Date();
        this.lastStateUpdate = this.dateAdded;
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
        return String.format(
                "%s %f %d %f %d %f",
                getDownloadName(),
                getDownloadedPercentage(),
                getDownloadSize(),
                getAverageSpeed(),
                getEstimatedRemainingTime(),
                getCurrentSpeed()
        );
    }

    @Override
    public int hashCode() {
        return torrent.getTorrentId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof MyTorrentInfo)) return false;
        MyTorrentInfo other = (MyTorrentInfo) obj;
        return torrent.getTorrentId().equals(other.torrent.getTorrentId());
    }

    public void stop() {
        client.stop();
    }

    @Override
    public Double getAverageSpeed() {
        return averageSpeed;
    }

    @Override
    public Long getDownloadSize() {
        return downloadSize;
    }

    @Override
    public String getDownloadName() {
        return torrentName;
    }

    @Override
    public Long getEstimatedRemainingTime() {
        return estimatedRemainingTime;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setDownloadSize(Long downloadSize) {
        this.downloadSize = downloadSize;
    }

    public void setEstimatedRemainingTime(Long estimatedRemainingTime) {
        this.estimatedRemainingTime = estimatedRemainingTime;
    }

    public void setDownloadedPercentage(Double downloadedPercentage) {
        this.downloadedPercentage = downloadedPercentage;
    }

    @Override
    public Double getDownloadedPercentage() {
        return downloadedPercentage;
    }

    public Date getLastStateUpdate() {
        return lastStateUpdate;
    }

    public Long getLastStateDownloaded() {
        return lastStateDownloaded;
    }

    public void setLastUpdate(Date lastStateUpdate, Long lastStateDownloaded) {
        this.lastStateUpdate = lastStateUpdate;
        this.lastStateDownloaded = lastStateDownloaded;
    }

    public void setCurrentSpeed(Double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    @Override
    public Double getCurrentSpeed() {
        return currentSpeed;
    }
}
