package ru.nik.businessLogic.factories;

import ru.nik.businessLogic.ticketClasses.Coordinates;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.businessLogic.ticketClasses.TicketType;
import com.google.gson.Gson;

import java.util.*;

/**
 * Фабрика билетов. Сделана криво. Рассмотерны случаи
 * Создание нового объекта с новым id
 * Создание нового объекта по заданым строчкам
 * Создание объекта с передаваемым id ( задание по ТЗ)
 */
public class TicketFactory implements Factory {
    private Scanner scanner;
    public TicketFactory(){
        scanner = new Scanner(System.in);
    }
    public void updateScanner(){
        scanner = new Scanner(System.in);
    }
    @Override
    public Ticket createTicket() {
        System.out.println("Введите данные о билете , который вы хотите поместить в коллекцию");
        return new Ticket(createName(),createCooordinates(),createPrice(),createTicketType());
    }
    @Override
    public Ticket createTicketParams(String options){
        List<String> params = Arrays.asList(options.split(" "));
        String TicketName = params.get(0);
        Double x = Double.parseDouble(params.get(1));
        Double y = Double.parseDouble(params.get(2));
        Coordinates cor = new Coordinates(x,y);
        Long price = Long.parseLong(params.get(3));
        TicketType t = TicketType.valueOf(params.get(4));
        return new Ticket(TicketName,cor,price,t);
    }


    @Override
    public Ticket createTicketWithId(Long Id) { //TODO аццкий говнокод. Пофиксить при первой же возможности
        System.out.println("Введите данные о билете , который вы хотите поместить в коллекцию");
        return new Ticket(createName(),createCooordinates(),createPrice(),createTicketType(),Id);
    }
    @Override
    public Ticket createTicketFromJson(String json){
        Gson g = new Gson();
        Ticket t = g.fromJson(json, Ticket.class);
        return t;
    }
    public String createName(){
        System.out.println("Введите имя билета");
        return  scanner.next(); // 1 поле для ввода в конструктор класса Ticket
    }
    public Coordinates createCooordinates(){
        System.out.println("Введите координаты перелета");
        double x = 0;
        double y = 0;
        while (true) {
            try {
                 x = scanner.nextDouble();
                 y = scanner.nextDouble();
                break;
            } catch (Exception c) {
                System.out.println("Введите правильный параметр");
                updateScanner();
            }
        }
        return new Coordinates(x, y); // второй параметр для конструктора
    }
    public long createPrice(){
        System.out.println("Введите цену билета");
        long price = 0;
        while (true) {
            try {
                price = scanner.nextLong();
                break;
            } catch (Exception c) {
                System.out.println("Введите правильный параметр");
                System.out.println(price);
                updateScanner();
            }

        }
        return price;
    }
    public TicketType createTicketType(){
        System.out.println("Выберите тип билетв. Возможные варианты:\n 1) VIP \n 2) USUAL \n 3) BUDGETARY \n 4) CHEAP");
        TicketType t;  // 3 параметр конструктора
        String type = scanner.next();
        switch (type) {
            case "VIP": t = TicketType.VIP; break;
            case "USUAL": t = TicketType.USUAL; break;
            case "BUDGETARY": t = TicketType.BUDGETARY; break;
            case "CHEAP": t=TicketType.CHEAP; break;
            default: t = null;
        }
        return t;
    }
    public Ticket createTicketGui(String owner, ArrayList<String> params){
        Ticket ticket = new Ticket();
        Coordinates coordinates = new Coordinates(Double.parseDouble(params.get(0)), Double.parseDouble(params.get(1)));
        ticket.setCoordinates(coordinates);
        ticket.setOwner(owner);
        ticket.setName(params.get(2));
        ticket.setPrice(Integer.parseInt(params.get(3)));
        ticket.setCreationDate(new Date());
        ticket.setType(TicketType.valueOf(params.get(4)));
        return ticket;
    }
}
