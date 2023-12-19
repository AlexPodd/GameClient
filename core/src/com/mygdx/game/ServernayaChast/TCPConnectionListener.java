package com.mygdx.game.ServernayaChast;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection topConnection);
    void onReceiveString(TCPConnection topConnection,String values);
    void onDisconnect(TCPConnection topConnection);
    void onException(TCPConnection topConnection, Exception e);
}
