package com.pjerebic.remotedownloader.torrent;

import bt.net.Peer;
import bt.torrent.TorrentSessionState;

public class Utils {

    public static String stateInfo(TorrentSessionState state) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "state: %d/%d%n->downloaded: %d%n->uploaded: %d%n->peers: %d%n",
                state.getPiecesTotal() - state.getPiecesRemaining(), state.getPiecesTotal(),
                state.getDownloaded(),
                state.getUploaded(),
                state.getConnectedPeers().size()
        ));
        state.getConnectedPeers()
                .forEach(
                        peer -> sb.append(
                                String.format("%s%n", peerInfo(peer))
                        )
                );
        return sb.toString();
    }



    private static String peerInfo(Peer peer) {
        return String.format(
                "peer: %s, %s", peer.getInetAddress().toString(), peer.getInetSocketAddress().toString()
        );
    }
}
