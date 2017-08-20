package com.pjerebic.remotedownloader.torrent;

public interface DownloadInfo {
    Double getAverageSpeed();
    Double getCurrentSpeed();
    Long getDownloadSize();
    String getDownloadName();
    Long getEstimatedRemainingTime();
    Double getDownloadedPercentage();
}
