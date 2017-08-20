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
        if (!torrents.containsKey(currentTorrent.getTorrentId())) {
            torrents.put(
                    currentTorrent.getTorrentId(),
                    new MyTorrentInfo(client)
            );
        }
    }

    public static void updateTorrentState(Torrent torrent, TorrentSessionState state) {
        MyTorrentInfo info = torrents.get(torrent.getTorrentId());
        info.setState(state);
        if (!torrent.getName().trim().equals("")) {
            info.setTorrentName(torrent.getName().trim());
            Date currentTime = new Date();

//            System.out.println(String.format("%s %s %f %d %f",currentTime.toString(),info.getDateAdded().toString(),(1.0*state.getDownloaded()), (currentTime.getTime() - info.getDateAdded().getTime()) ,(1.0*state.getDownloaded())/(currentTime.getTime() - info.getDateAdded().getTime()))  );
            info.setAverageSpeed((1.0 * state.getDownloaded()) / (currentTime.getTime() - info.getDateAdded().getTime()));
            info.setDownloadSize(torrent.getSize() / 1024);
//            info.setDownloadedPercentage((1.0 * (state.getPiecesTotal() - state.getPiecesRemaining())) / state.getPiecesTotal());

            Date lastUpdate = info.getLastStateUpdate();

            if(lastUpdate.getTime() + 5000 < currentTime.getTime() ){
                long previousStateDownloaded = info.getLastStateDownloaded();
                info.setCurrentSpeed((state.getDownloaded() - previousStateDownloaded) / 1.0 / (currentTime.getTime() - lastUpdate.getTime()));
                System.out.println(
                        String.format(
                                "(%d-%d)/(%d-%d)=%f",
                                state.getDownloaded(),
                                previousStateDownloaded,
                                currentTime.getTime(),
                                lastUpdate.getTime(),
                                info.getCurrentSpeed()
                        )
                );
                info.setLastUpdate(currentTime, state.getDownloaded());
            }

            info.setDownloadedPercentage(state.getDownloaded() / 1.0 / torrent.getSize() * 100);
            info.setEstimatedRemainingTime((long) ((100.0 - info.getDownloadedPercentage()) * info.getDownloadSize() / info.getAverageSpeed()));

        }
        torrents.put(torrent.getTorrentId(), info); //todo: check if this is optional
    }

}
