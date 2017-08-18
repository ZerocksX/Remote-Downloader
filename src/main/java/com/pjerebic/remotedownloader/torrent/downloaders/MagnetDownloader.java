package com.pjerebic.remotedownloader.torrent.downloaders;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.metainfo.Torrent;
import bt.runtime.BtClient;
import bt.runtime.BtRuntime;
import com.pjerebic.remotedownloader.torrent.Downloader;
import com.pjerebic.remotedownloader.torrent.TorrentInfoProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MagnetDownloader implements Downloader {

    private BtRuntime runtime;

    public MagnetDownloader(BtRuntime runtime) {
        this.runtime = runtime;
    }

    public BtRuntime getRuntime() {
        return runtime;
    }

    @Override
    public void download(String link, String path) {

        if (link == null) {
            throw new RuntimeException();
        }
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            throw new RuntimeException();
        }

        Storage storage = new FileSystemStorage(filePath);

        BtClient client = Bt
                .client(getRuntime())
                .storage(storage)
                .magnet(link)
                .build();

        client.startAsync(state -> {
            Torrent currentTorrent = client.getSession().getTorrent();
            if(!currentTorrent.getTorrentId().toString().equals("0000000000000000000000000000000000000000")){ //stub torrent
                TorrentInfoProvider.addTorrent(client);
                TorrentInfoProvider.updateTorrentState(currentTorrent, state);
            }
            if (state.getPiecesRemaining() == 0) {
                System.out.println("Finished downloading");
                client.stop();
            }
        }, 1000);
    }
}
