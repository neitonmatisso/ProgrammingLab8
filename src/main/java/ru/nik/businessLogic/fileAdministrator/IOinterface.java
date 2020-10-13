package ru.nik.businessLogic.fileAdministrator;

import ru.nik.serverPackage.ResultShell;

public interface IOinterface {
    void write(ResultShell resultShell);
    void read(ResultShell resultShell);
}
