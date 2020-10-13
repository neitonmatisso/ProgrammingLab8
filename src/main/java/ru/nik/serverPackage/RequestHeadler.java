package ru.nik.serverPackage;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.nik.clientPackage.Account;
import ru.nik.clientPackage.Request;
import ru.nik.clientPackage.RequestType;
import ru.nik.connectionPackage.Connection;
import ru.nik.connectionPackage.TransferJsonObject;
import ru.nik.serverPackage.accounts.AccountManager;

import java.io.IOException;
import java.security.AccessControlContext;
import java.util.concurrent.Callable;

public class RequestHeadler implements Runnable {
    private Server server;
    private String query;
    private Connection c;
    private AccountManager accountManager;
    private Logger logger;

    public RequestHeadler(Server server, String s, Connection connection, AccountManager accountManager, Logger logger) {
        this.server = server;
        this.query = s;
        this.accountManager = accountManager;
        this.logger = logger;
        c = connection;
    }

    @Override
    public void run() {
        Request request = new Gson().fromJson(query, Request.class);
        switch (request.getRequestType()) {
            case LOGIN:
                server.sighIn(c, new Gson().fromJson(request.getOptions(), Account.class));
                break;
            case QUERY:
                if (accountManager.checkToken(request.getToken())) {
                    logger.info(request.getOwner() + " имеет валидный токен");
                    server.queryExecutor(c, request.getOwner() + "!" + request.getCommmandName() + "!" + request.getOptions());
                } else {
                    ResultShell resultShell = new ResultShell();
                    resultShell.setResultType(ResultType.INFO);
                    resultShell.checkResult("Авторизуйтесь для исполнения комнад");
                    try {
                        TransferJsonObject transferJsonObject = new TransferJsonObject();
                        transferJsonObject.setTransferJson(new Gson().toJson(resultShell));
                        c.sendTransferObject(transferJsonObject);
//                        c.sendMessage(new Gson().toJson(resultShell));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REGISTER:
                server.registerAcc(c, new Gson().fromJson(request.getOptions(), Account.class));
                break;
            case DISCONNECT:
                accountManager.removeDisconnectToken(request.getToken(), request.getOwner());
                server.isDisconnect(c);
                break;
        }



    }
}
