package ru.nik.connectionPackage;

import ru.nik.clientPackage.Request;

public interface ConnectionListener {
    void connectionReady(Connection c);
    void getString(Connection c, String s);
    void isDisconnect(Connection c);
    void getTransferObject(Connection c , TransferJsonObject transferJsonObject);
}
