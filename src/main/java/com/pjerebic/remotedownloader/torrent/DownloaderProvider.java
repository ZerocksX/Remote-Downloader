package com.pjerebic.remotedownloader.torrent;

import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.runtime.BtRuntime;
import bt.runtime.Config;
import com.pjerebic.remotedownloader.torrent.downloaders.MagnetDownloader;

public class DownloaderProvider {


    private static BtRuntime magnetRuntime = BtRuntime
            .builder(new Config() {
                @Override
                public int getNumOfHashingThreads() {
                    return Runtime.getRuntime().availableProcessors() * 2;
                }
            })
            .module(new DHTModule(new DHTConfig() {
                @Override
                public boolean shouldUseRouterBootstrap() {
                    return true;
                }
            }))
            .autoLoadModules()
            .disableAutomaticShutdown()
            .build();

    public static Downloader getDownloader(DownloadType type) {
        switch (type) {
            case Link:
                return null;
            case Magnet:
                return new MagnetDownloader(magnetRuntime);
            case Torrent:
                return null;
        }
        throw new RuntimeException("Unknown download type");
    }
}
