package ru.nik.clientPackage;
import ru.nik.businessLogic.commands.CommandType;
import com.google.gson.Gson;
import ru.nik.connectionPackage.Connection;
import ru.nik.connectionPackage.ConnectionListener;
import ru.nik.connectionPackage.TransferJsonObject;
import ru.nik.serverPackage.ResultShell;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client implements ConnectionListener {
    private Connection connection;
    private Map<CommandType,List<String>> settings;
    private String clientName = "";
    private String clientPass = "";
    private String token;
    private String currConnection;
    private boolean serverCheck;
    private String currentAnswer;
    public Client() throws InterruptedException, IOException {
        try {
            settings = new HashMap<>();
            tryToCommect(enterServerData());
            Thread.sleep(500);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка подключения");
        }

        while (true) {
            String state = "";
            try {
            if (settings.isEmpty()) {
                Thread.sleep(1000);
                if (connection.checkDisconnect()) {
                    throw new IOException();
                }
                continue;
            }
                state = new RequestBuilder(settings, clientName,token).completeRequest();
                serverCheck = connection.checkDisconnect();
                TransferJsonObject transferJsonObject = new TransferJsonObject();
                transferJsonObject.setTransferJson(state);
//                connection.sendMessage(state);
                connection.sendTransferObject(transferJsonObject);

            } catch (IOException ex) {
                System.out.println(connection.checkDisconnect());
                tryToCommect(currConnection.split(" "));
                Thread.sleep(1000);
                if (!connection.checkDisconnect()) {
                    Account ac = new Account(clientName, clientPass);
                    Request loginRequest = new Request(null, new Gson().toJson(ac),null,RequestType.LOGIN,token);
                    connection.sendTransferObject( new TransferJsonObject(new Gson().toJson(loginRequest)));
                    Thread.sleep(1000);
                    Request stateRequest = new Gson().fromJson(state, Request.class);
                    stateRequest.setToken(token);
                    connection.sendTransferObject(new TransferJsonObject(new Gson().toJson(stateRequest)));
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        while (true) {
            new Client();
            System.out.println("Connection with server was interrupt. Try to reconnect");
        }

    }
    public void tryToCommect( String[] ar) throws IOException {
        String [] buff = new String[0];
        try {
            buff = ar;
            connection = new Connection(this,new Socket(buff[0], Integer.parseInt(buff[1])));
            currConnection = buff[0] +" "+ buff[1];
        } catch (IOException ex){
            System.out.println("Сервер недоступен! Введите \'1\' если хотите попробовать еще раз подклбчится к этому серверу. Введите \'2\' чтобы выйти из приложения. 3 чтобы ввести новые данные");
            String buffString = new Scanner(System.in).nextLine();
                switch (buffString) {
                    case "1":
                        System.out.println(buff[0] + " " + buff[1]);
                        tryToCommect(buff);
                        break;
                    case "2":
                        System.exit(0);
                    case "3":
                        tryToCommect(enterServerData());
                        break;
                }
        }
    }
    public String[] enterServerData(){
        System.out.println("Введите IP адрес и порт сервера: ");
        String[] s = new Scanner(System.in).nextLine().split(" ");
        return s;
    }
    public void tryToLogIn(Connection c){
        Account ac = new Account();
        ac.enterData();
        clientName = ac.getLogin();
        clientPass = ac.getPassword();
        Request loginRequest = new Request(null, new Gson().toJson(ac),null,RequestType.LOGIN,token);
        try {
//            c.sendMessage( new Gson().toJson(loginRequest));
            c.sendTransferObject(new TransferJsonObject( new Gson().toJson(loginRequest)));
        } catch (IOException e){
            System.out.println("something wrong with server");
        }

    }
    public void registerAcc(Connection c){
        Account ac = new Account();
        ac.enterData();
        clientName = ac.getLogin();
        clientPass = ac.getPassword();
        Request loginRequest = new Request(null, new Gson().toJson(ac),null,RequestType.REGISTER,token);
        try {
//            c.sendMessage( new Gson().toJson(loginRequest));
            TransferJsonObject transferJsonObject = new TransferJsonObject();
            transferJsonObject.setTransferJson( new Gson().toJson(loginRequest));
            System.out.println(transferJsonObject.getTransferJson());
            c.sendTransferObject(transferJsonObject);
        } catch (IOException e){
            System.out.println("something wrong with server");
        }
    }
    public void enterToAccount(Connection c){
        System.out.println("Добро пожаловать. Нажмите 1 , если вы уже имеете зарегистрированный аккаунт и 2 если хотите зарегистрировать");
        String choise = new Scanner(System.in).nextLine();
        switch (choise){
            case "1": tryToLogIn(c); break;
            case "2": registerAcc(c); break;
        }
    }

    @Override
    public void connectionReady(Connection c) {
        if(clientName.equals("") || clientPass.equals("")) {
            System.out.println("Connection ready! " + clientName + " " + clientPass );
            enterToAccount(c);
        }
    }

    @Override
    public void getString(Connection c, String s) {
        ResultShell resultShell = new Gson().fromJson(s, ResultShell.class);
        switch (resultShell.getResultType()) {
            case SETTINGS:
                settings = new Gson().fromJson(resultShell.getResulst(), HashMap.class);
                System.out.println("Настройки получены!");
                break;
            case RESULT:
            case INFO:
                System.out.println(resultShell.getResulst());
                break;
            case ERROR:
                System.out.println(resultShell.getResulst());
                clientName = "";
                clientPass = "";
                enterToAccount(c);
                break;
            case MESSAGE:
                System.out.println(resultShell.getResulst());
                break;
            case TOKEN:
                token = resultShell.getResulst().split("\n")[0];
                System.out.println("Ваш токен: " + token);
        }
    }

    @Override
    public void isDisconnect(Connection c) {
        System.out.println("Соединение прервано!");
    }

    @Override
    public void getTransferObject(Connection c, TransferJsonObject transferJsonObject) {
        String responseJson = transferJsonObject.getTransferJson();
        getString(c, responseJson);
    }


}
