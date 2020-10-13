package ru.nik.clientPackage;

import ru.nik.businessLogic.commands.CommandType;
import ru.nik.businessLogic.factories.TicketFactory;
import com.google.gson.Gson;
import ru.nik.businessLogic.rootCommands.Message;
import ru.nik.connectionPackage.Connection;

import java.io.*;
import java.util.*;

public class RequestBuilder implements Serializable{
    private String commandName;
    private String arguments;
    private Scanner scanner;
    private Map<CommandType,List<String>> validCommands;
    private String owner;
    private String token;
    public RequestBuilder(Map s, String owner, String token){
        commandName = "";
        arguments = "";
        validCommands = s;
        this.owner = owner;
        this.token = token;
        scanner = new Scanner(System.in);
    }
    public void argumenstCreater(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("> ");
        String buff = scanner.nextLine();
        if(buff.equals("")){
            argumenstCreater();
            return;
        }
        if(buff.equals("exit")){
            commandName = "exit";
            return;
        }
        List<String> splitQuery = Arrays.asList(buff.split(" "));
        Set<CommandType> keySet = validCommands.keySet();
        for(Object t : keySet){
            if(validCommands.get(t).contains(splitQuery.get(0))){
                if(t.equals("CLEAR_COMMAND")) {
                    if (splitQuery.size() > 1) {
                        System.out.println("Данная команда не содержит аргументов, запрос не будет сформирован");
                        argumenstCreater();
                        return;
                    } else {
                        commandName = splitQuery.get(0);
                        return;
                    }
                } else if (t.equals("ARGS_COMMAND")){
                    if(splitQuery.size() != 2){
                        System.out.println("Данная команда обязательно должна содержать аргумент! Повторите ввод");
                        argumenstCreater();
                        return;
                    } else{
                        commandName = splitQuery.get(0);
                        arguments = splitQuery.get(1);
                        return;
                    }
                } else if (t.equals("OBJECT_COMMAND")){
                    commandName = splitQuery.get(0);
                    arguments = new Gson().toJson(new TicketFactory().createTicket());
                    return;
                } else if (t.equals("SCRIPT_COMMAND")){
                    commandName = splitQuery.get(0);
                        try {
                            Scanner s = new Scanner(new File(splitQuery.get(1)));
                            while (s.hasNextLine()){
                                String buffer = s.nextLine();
                                List<String> currCommand= Arrays.asList(buffer.split(" "));

                                if(currCommand.get(0).equals("execute")){
                                   scriptLoader(currCommand.get(1));
                                    continue;
                                }


                                if(currCommand.size() == 1) {
                                    arguments += currCommand.get(0) + "%";
                                    continue;
                                }
                                if(currCommand.size() == 2 ){
                                    arguments+= currCommand.get(0)+" "+currCommand.get(1) + "%";
                                    continue;
                                }

                            }
                            return;
                    } catch (FileNotFoundException e) {
                        System.out.println("Такого файла не сущесвтует!");
                        return;
                    }
                } else if (t.equals("MESSAGE")){
                    commandName = splitQuery.get(0);
                    Message message = new Message();
                    System.out.print("Выберите получателя: ");
                    message.setReceiver(new Scanner(System.in).nextLine());
                    System.out.println("Введите текст вашего сообщения: ");
                    message.setText(new Scanner(System.in).nextLine());
                    message.setAuthor(owner);
                    arguments = new Gson().toJson(message);
                    return;

                }
           }

        }
        System.out.println("Данной команды не сущесвтует! Повторите попытку");
        argumenstCreater();
    }
    public String completeRequest(){
        argumenstCreater();
        System.out.println("Запрос сформирован: " + commandName + " " + arguments);
        if(commandName.equals("exit")){
            return new Gson().toJson(new Request(commandName,arguments,owner, RequestType.DISCONNECT,token));
        }
        return new Gson().toJson(new Request(commandName,arguments,owner, RequestType.QUERY,token));
    }
    public void scriptLoader(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(filePath));
        while (sc.hasNext()){
            String buffe2 = sc.nextLine();
            List<String> inCommand = Arrays.asList(buffe2.split(" "));
            if(inCommand.get(0).equals("execute")){
                scriptLoader(inCommand.get(1));
                continue;
            }
            if(inCommand.size() == 1) {
                arguments += inCommand.get(0) + "%";
                continue;
            }
            if(inCommand.size() == 2 ){
                arguments+= inCommand.get(0)+" "+inCommand.get(1) + "%";
                continue;
            }
        }

    }
}
