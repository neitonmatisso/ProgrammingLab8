package ru.nik.clientPackage;

import com.google.gson.Gson;
import ru.nik.businessLogic.commands.CommandType;
import ru.nik.businessLogic.factories.TicketFactory;
import ru.nik.businessLogic.rootCommands.Message;
import ru.nik.businessLogic.ticketClasses.Coordinates;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.connectionPackage.Connection;
import ru.nik.connectionPackage.ConnectionListener;
import ru.nik.connectionPackage.TransferJsonObject;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.SendUser;
import ru.nik.serverPackage.accounts.User;

import java.io.IOException;
import java.util.*;

public class GuiFunctionalClient implements ConnectionListener{
    private  Connection connection;
    private Map<CommandType, List<String>> settings;
    private String clientName = "";
    private String clientPass = "";
    private  String token;
    private  String currConnection;
    private  ServerStatus serverStatus;
    private  String currentResponse;
    private Queue<String> infoQueue = new PriorityQueue<>();
    private Queue<String> messQueue = new PriorityQueue<>();
    private Queue<String> errQueue = new PriorityQueue<>();
    private SendUser currentUserInfo;
    private Queue<ArrayList<Ticket>> collectionUpdates = new PriorityQueue<>();

    public String connectToServer(){
        try {
            connection = new Connection(this, "localhost",7878);
            serverStatus = ServerStatus.READY;
            return "сервер доступен";

        } catch (IOException exception) {
            serverStatus = ServerStatus.CLOSE;
            return "Сервер временно недоступен";
        }

    }
    public String getNextError(){
        return errQueue.poll();
    }
    public String getNextInfo(){
        return infoQueue.poll();
    }
    public String getNextMessage(){
        return messQueue.poll();
    }
    public SendUser getCurrentUserInfo(){
        return currentUserInfo;
    }
    public ArrayList<Ticket> getNextCollection(){
        return collectionUpdates.poll();
    }

    public ServerStatus register(RequestType requestType, String login, String pass){
        Account account = new Account(login,pass);
        Request registrationRequest = new Request(null, new Gson().toJson(account),null,requestType,token);
        try {
            connection.sendTransferObject(new TransferJsonObject(new Gson().toJson(registrationRequest)));
            clientName = login;
            clientPass = pass;
            return ServerStatus.TRUE;
        } catch (IOException ex){
            return ServerStatus.CLOSE;

        }
    }
    public void sendMessage(String name, String text){
        Request request = new Request("send_message",new Gson().toJson(new Message(name,text)),name,RequestType.QUERY,token);
        try {
            connection.sendTransferObject(new TransferJsonObject(new Gson().toJson(request)));
        } catch (IOException ex){
            System.out.println("false ( message )");
        }
    }
    public void createObjectQuery(String commandName, ArrayList<String> properties) {
        Ticket sendTicket = new TicketFactory().createTicketGui(clientName,properties);
        Request request = new Request(commandName, new Gson().toJson(sendTicket),clientName,RequestType.QUERY, token);
        try {
            connection.sendTransferObject(new TransferJsonObject(new Gson().toJson(request)));
        } catch (IOException ex){
            System.out.println("false (send object) " );
            ex.printStackTrace();
        }
    }
    public void createQuery(String commandName, String args){
        Request request = new Request(commandName,args,clientName, RequestType.QUERY,token);
        try {
            connection.sendTransferObject(new TransferJsonObject(new Gson().toJson(request)));
        } catch (IOException ex){
            System.out.println("false ( message )");
        }
    }
    public ServerStatus getServerStatus(){
        return serverStatus;
    }

    public String getClientName(){
        return clientName;
    }



    @Override
    public void connectionReady(Connection c) {

    }

    @Override
    public void getString(Connection c, String s) {
        ResultShell resultShell = new Gson().fromJson(s, ResultShell.class);
        System.out.println(resultShell.getResulst());
        switch (resultShell.getResultType()) {
            case SETTINGS:
                settings = new Gson().fromJson(resultShell.getResulst(), HashMap.class);
                break;
            case RESULT:
                infoQueue.add(resultShell.getResulst());
                break;
            case INFO:
                break;
            case ERROR:
                errQueue.add(resultShell.getResulst());
                System.out.println("Ошибка: " + errQueue.peek());
                clientName = "";
                clientPass = "";
                break;
            case MESSAGE:
                messQueue.add(resultShell.getResulst());
                break;
            case TOKEN:
                token = resultShell.getResulst().split("\n")[0];
                break;
            case USER:
                currentUserInfo = new Gson().fromJson(resultShell.getResulst(), SendUser.class);
                clientName = currentUserInfo.getLogin();
                break;
            case COLLECTION_UPDATE:
                String jsonTickets = resultShell.getResulst();
                Scanner scanner = new Scanner(jsonTickets);
                ArrayList<Ticket> ticketArrayList = new ArrayList<>();
                while (scanner.hasNextLine()){
                    ticketArrayList.add(new Gson().fromJson(scanner.nextLine(), Ticket.class));
                }
                collectionUpdates.add(ticketArrayList);
                break;
        }

    }

    @Override
    public void isDisconnect(Connection c) {

    }

    @Override
    public void getTransferObject(Connection c, TransferJsonObject transferJsonObject) {
        String responseJson = transferJsonObject.getTransferJson();
        getString(c, responseJson);
    }
}
