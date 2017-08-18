package com.pjerebic.remotedownloader.torrent.downloaders;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.magnet.MagnetUri;
import bt.magnet.MagnetUriParser;
import bt.runtime.BtClient;
import bt.runtime.BtRuntime;
import bt.runtime.Config;
import com.google.inject.Module;
import com.pjerebic.remotedownloader.torrent.Downloader;
import com.pjerebic.remotedownloader.torrent.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

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
//        magnet:?xt=urn:btih:2A6787A1E748267EF1CD72EED820B88970D170F4&dn=how%20to%20become%20rich%20investing%20in%20the%20stock%20market.pdf&tr=udp%3a%2f%2ftracker.leechers-paradise.org%3a6969&tr=udp%3a%2f%2fzer0day.ch%3a1337&tr=udp%3a%2f%2fopen.demonii.com%3a1337&tr=udp%3a%2f%2ftracker.coppersurfer.tk%3a6969&tr=udp%3a%2f%2fexodus.desync.com%3a6969

        MagnetUri magnetUri = MagnetUriParser.parser().parse(link);

        String torrentName = magnetUri.getDisplayName().orElse("Unknown name");
        System.out.println(torrentName);

        BtClient client = Bt
                .client(getRuntime())
                .storage(storage)
                .magnet(link)
                .build();


        client.startAsync(state -> {
            System.out.println(Utils.stateInfo(state));
            if (state.getPiecesRemaining() == 0) {
                System.out.println("Finished downloading");
                client.stop();
            }
        }, 1000);
    }
}
