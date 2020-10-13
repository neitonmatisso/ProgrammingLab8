package ru.nik.serverPackage;

import com.google.gson.Gson;
import ru.nik.connectionPackage.Connection;

import java.io.IOException;

public class ResponseDispatcher implements Runnable {
    private Connection c;
    private ResultShell resultShell;
    public ResponseDispatcher(Connection c , ResultShell resultShell, ResultType type){
        this.c = c;
        this.resultShell = resultShell;
        resultShell.setResultType(type);
    }
    @Override
    public void run() {
        try {
            c.sendMessage(new Gson().toJson(resultShell));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
