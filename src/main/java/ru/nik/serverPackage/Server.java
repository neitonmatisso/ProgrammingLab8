package ru.nik.serverPackage;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.FileLockInterruptionException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.nik.businessLogic.ArrayListWorker;
import ru.nik.businessLogic.ColletionWorker;
import ru.nik.businessLogic.Exceptions.AccountRegisterException;
import ru.nik.businessLogic.Exceptions.ExitException;
import ru.nik.businessLogic.Exceptions.LogInException;
import ru.nik.businessLogic.commands.*;
import ru.nik.businessLogic.factories.IdController;
import ru.nik.businessLogic.fileAdministrator.FileManager;
import ru.nik.clientPackage.Account;
import ru.nik.clientPackage.Request;
import ru.nik.clientPackage.RequestType;
import ru.nik.connectionPackage.*;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.AccountManager;
import ru.nik.serverPackage.accounts.SendUser;
import ru.nik.serverPackage.accounts.TokenManager;
import ru.nik.serverPackage.accounts.User;

public class Server implements ConnectionListener {
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Server.class);
    private static List<Connection> connectionList = new ArrayList<>();
    private ColletionWorker coll = new ArrayListWorker();
    private static ControlUnit cunit = new ControlUnit();
    private IdController idController = new IdController("idFile");
    private FileManager fi = new FileManager(coll,"SaveFile");
    private TokenManager tokenManager = new TokenManager();
    private AccountManager accountManager = new AccountManager(tokenManager);
    private CommandLoader cl =  new CommandLoader(coll,cunit,fi,accountManager);
    private  ExecutorService ex = Executors.newCachedThreadPool();
    private ExecutorService queryHadl = Executors.newFixedThreadPool(10);
    private Server() {
        log.info("Сервер был запущен!");
        System.out.println("Server start...");
        coll.setCollection(new TicketDAO().loadTickets());
        log.info("Данные из БД загружены на сервер");
        try (ServerSocket socket = new ServerSocket(7878);) {
            System.out.println("Server open on port 7878");
            while (true) {
                new Connection(socket.accept(), this);
                System.out.println("Залетело новое соединение");
            }
        } catch (IOException e) {
            System.out.println("Shit with server");
        }
    }

    public static void main(String[] args) {
            new Server();
    }

    @Override
    public void connectionReady(Connection c) {
        System.out.println(tokenManager.showTokens());
        log.info("Новое подключение: " + c.toString());
    }

    @Override
    public void getString(Connection c, String s)  {
        log.info("Получено сообщение от: " +c.toString() + " сообщение:" +s);
        System.out.println("Получено сообщение от: " +c.toString() + " сообщение:" +s);
        queryHadl.submit(new RequestHeadler(this, s, c,accountManager,log));


    }

    @Override
    public void isDisconnect(Connection c) {
        System.out.println(tokenManager.showTokens());
        connectionList.remove(c);
        System.out.println("Клиент " + c.toString() +" отключился, осталось подключений: "+ connectionList.size() );
        log.info("Клиент "+ c.toString() + " отключился");
        c.disconnect();

    }

    @Override
    public void getTransferObject(Connection c, TransferJsonObject transferJsonObject) {
        String requestJson = transferJsonObject.getTransferJson();
        System.out.println(requestJson);
        getString(c, requestJson);
    }


    public void queryExecutor(Connection c,String buffer){
        try {
            ResultShell res = new ResultShell();
            Future<ResultShell> resul = ex.submit(new CommandExecutor(buffer,res,cunit,accountManager));
            res.setResultType(ResultType.RESULT);
            res = resul.get();
//            c.sendMessage(new Gson().toJson(res));
            c.sendTransferObject(new TransferJsonObject(new Gson().toJson(res)));
            ResultShell updatesResult = new ResultShell();
            updatesResult.setResultType(ResultType.COLLECTION_UPDATE);
            updatesResult.checkResult(coll.getAllJsonTickets());
            connectionList.forEach(x -> {
                try {
                    x.sendTransferObject(new TransferJsonObject(new Gson().toJson(updatesResult)));
                } catch (IOException e) {
                    System.out.println("Ошибка отправки обновленной коллекции");
                }
            });
            System.out.println("Сообщение отправлено клиенту " + c + " текст: "+ "\n" + resul.get().getResulst());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException ex){
            System.out.println("some error on query executor");
            log.info("some error on query executor");
        }

    }
    public void sighIn(Connection c, Account account){
        ResultShell resultShell = new ResultShell();
        try {
            System.out.println("начало авторизации");
            accountManager.checkAcc(resultShell,account,c);
            resultShell.setResultType(ResultType.TOKEN);
            c.sendTransferObject(new TransferJsonObject(new Gson().toJson(resultShell)));
            ResultShell settingsToClient = new ResultShell();
            settingsToClient.checkResult(new Gson().toJson(cunit.getSettings()));
            settingsToClient.setResultType(ResultType.SETTINGS);
//            c.sendMessage(new Gson().toJson(settingsToClient));
            c.sendTransferObject(new TransferJsonObject(new Gson().toJson(settingsToClient)));
            User currentUser = accountManager.getUser(account.getLogin());
            System.out.println(currentUser);
            ResultShell userInfo = new ResultShell();
            userInfo.setResultType(ResultType.USER);
            userInfo.checkResult(new Gson().toJson(new SendUser(currentUser)));
            c.sendTransferObject(new TransferJsonObject(new Gson().toJson(userInfo)));
            ResultShell updatesResult = new ResultShell();
            updatesResult.setResultType(ResultType.COLLECTION_UPDATE);
            updatesResult.checkResult(coll.getAllJsonTickets());
            connectionList.add(c);
            connectionList.forEach(x -> {
                try {
                    x.sendTransferObject(new TransferJsonObject(new Gson().toJson(updatesResult)));
                } catch (IOException e) {
                    System.out.println("Ошибка отправки обновленной коллекции");
                }
            });
            System.out.println(userInfo);
            log.info("Пак настроек отправлен: " + c.toString());
            System.out.println("Новый клиент авторизировался!");
        } catch (IOException e) {
            System.out.println("Oops");
        } catch (LogInException ex){
            try {
                resultShell.setResultType(ResultType.ERROR);
                TransferJsonObject transferJsonObject = new TransferJsonObject(new Gson().toJson(resultShell));
                c.sendTransferObject(transferJsonObject) ;
                System.out.println("Отказ в авторизации отправлен "+ c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void registerAcc(Connection c , Account account){
        try {
            ResultShell resultShell = new ResultShell();
            accountManager.registerAcc(resultShell, account,c);
            resultShell.setResultType(ResultType.TOKEN);
//            c.sendMessage(new Gson().toJson(resultShell));
            c.sendTransferObject(new TransferJsonObject(new Gson().toJson(resultShell)));
            ResultShell settingsToClient = new ResultShell();
            settingsToClient.checkResult(new Gson().toJson(cunit.getSettings()));
            settingsToClient.setResultType(ResultType.SETTINGS);
//            c.sendMessage(new Gson().toJson(settingsToClient));
            c.sendTransferObject(new TransferJsonObject(new Gson().toJson(settingsToClient)));
            User currentUser = accountManager.getUser(account.getLogin());
            System.out.println(currentUser);
            ResultShell userInfo = new ResultShell();
            userInfo.setResultType(ResultType.USER);
            userInfo.checkResult(new Gson().toJson(new SendUser(currentUser)));
            c.sendTransferObject(new TransferJsonObject(new Gson().toJson(userInfo)));
            ResultShell updatesResult = new ResultShell();
            updatesResult.setResultType(ResultType.COLLECTION_UPDATE);
            updatesResult.checkResult(coll.getAllJsonTickets());
            connectionList.add(c);
            connectionList.forEach(x -> {
                try {
                    x.sendTransferObject(new TransferJsonObject(new Gson().toJson(updatesResult)));
                } catch (IOException e) {
                    System.out.println("Ошибка отправки обновленной коллекции");
                }
            });
            System.out.println(userInfo);
        } catch (IOException ex){
            System.out.println("Oops...");
        } catch(AccountRegisterException ac){
            ResultShell resultShell = new ResultShell();
            resultShell.checkResult("Аккаунт с таким логином уже существует ");
            resultShell.setResultType(ResultType.ERROR);
            try {
//                c.sendMessage(new Gson().toJson(resultShell));
                c.sendTransferObject(new TransferJsonObject(new Gson().toJson(resultShell)));
                System.out.println("Клиент "+ c + "не смог зарегестрироваться");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
