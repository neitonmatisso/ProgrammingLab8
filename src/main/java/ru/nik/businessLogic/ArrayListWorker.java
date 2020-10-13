package ru.nik.businessLogic;

import com.google.gson.Gson;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.dbpackage.ticketDAO.TicketDAO;
import ru.nik.serverPackage.ResultShell;
import ru.nik.serverPackage.accounts.User;
import ru.nik.serverPackage.accounts.UserPriority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class-shell for the ArrayList collection. This class implements interface CollectionWorker (general interface for all wrapper class
 */
public class ArrayListWorker implements ColletionWorker {
    //private List<Ticket> ticketList;
    private List<Ticket > ticketList = Collections.synchronizedList(new ArrayList<>());
    private Date createDate;
    public ArrayListWorker() {
       // ticketList = new ArrayList<>();
        createDate = new Date();

    }

    @Override
    public void addToCollection(Ticket t, User user) {
        ticketList.add(t);
    }

    @Override
    public void setCollection(List<Ticket> l) {
        ticketList = l;
    }

    @Override
    public void addToCollection(Ticket t) {
        ticketList.add(t);
    }

    /**
     * @param id - individual non-repeating number
     * @return returns the object found by the specified parameter in the collection
     */
    @Override
    public Ticket getById(long id) {
        Ticket bufferTick;
        try {
            bufferTick = ticketList.stream()
                    .filter(x -> x.getId() == id)
                    .findAny()
                    .get();
        } catch (NoSuchElementException ex){
            bufferTick = null;
        }
        return bufferTick;
    }

    /**
     * Удаляет из коллекции билет по заданному айди
     * @param id - индвидуальный номер каждого билета
     */
    @Override
    public void removeById(long id, User user, ResultShell resultShell) {
        if(!getById(id).getOwner().equals(user.getLogin())){
            resultShell.checkResult("Вы не являетесь владельцем этого объекта и не можете его удалить!");
            return;
        }
        if(user.getPriority().equals(UserPriority.ROOT)){
            ticketList.removeIf(x -> (x.getId() == id ));
            resultShell.checkResult("Удаление прошло успешно");
            return;
        }

        ticketList.removeIf(x -> (x.getId() == id ));
        resultShell.checkResult("Удаление прошло успешно");
    }

    /**
     * ПОлучение всей коллекции ( в скором времени изменю , ибо это костыль )
     * @return рабочую коллекцию
     */
    @Override
    public ArrayList<Ticket> getCollection() {
        return new ArrayList<>(ticketList);
    }

    @Override
    public List<Ticket> getByUserLogin(String s) {
        List <Ticket> list = new ArrayList<>();
        for(Ticket t : ticketList){
            if(t.getOwner().equals(s)){
                list.add(t);
            }
        }
        return list;
    }

    @Override
    public List<Ticket> getGreater(Ticket t, String login) {
        List<Ticket> bufferList = new ArrayList<>();
        bufferList = ticketList.stream()
                    .filter(x -> x.getOwner().equals(login))
                    .filter(x -> x.getPrice() > t.getPrice())
                    .collect(Collectors.toList());
        return bufferList;
    }

    @Override
    public void removeByUserLogin(String s) {
        ticketList.removeIf(x -> x.getOwner().equals(s));
    }

    @Override
    public void removeTicket(Ticket ticket) {
        ticketList.remove(ticket);
    }

    /**
     * очищает коллекцию
     */
    @Override
    public void clear() {
        ticketList.clear();
    }

    /**
     *
     * @return билет с максимальной ценой
     */
    @Override
    public long getMaxPrice() {
        Long l = 0l;
        try {
            l = ticketList.stream()
                    .max((x, y) -> (int) (x.getPrice() - y.getPrice()))
                    .get()
                    .getPrice();
        } catch (NoSuchElementException ex){
            l = 0l;
        }
        return l;
    }

    /**
     * @return билет с минимальной ценой
     */

    @Override
    public long getMinPrice() {
        Long l = 0L;
        try {
            ticketList.stream()
                    .min((x,y) -> (int) (x.getPrice() - y.getPrice()))
                    .get()
                    .getPrice();
        } catch (NoSuchElementException ex){
            l = 9999999L;
        }
        return l;
    }

    /**
     * Сортировака коллекции
     */
    @Override
    public void sort() {
        ticketList.sort(Ticket::compareTo);
    }

    @Override
    public Boolean isEmpty() {
        return ticketList.isEmpty();
    }

    @Override
    public String getInfo() {
        return "Дата создания: " + createDate +"\n"
                + "количество элементов: " + ticketList.size();

    }

    @Override
    public String priceFilter(long pr) {
        List<Ticket> tr = ticketList.stream()
                .filter(t -> t.getPrice() > pr)
                .collect(Collectors.toList());
        if (tr.size() == 0 ){
            return "Элеметонв со значением цены больше чем "+ pr+ " не найдено";
        }
        return tr.toString();

    }

    @Override
    public String recerve() {
        return ticketList.stream()
                .sorted((x,y) -> (int) (x.getPrice()-y.getPrice()))
                .collect(Collectors.toList())
                .toString();
    }

    /**
     * вывод в консоль содержимого коллекции
     */
    @Override
    public String Show(){
        return ticketList.toString();
    }
    @Override
    public String getAllJsonTickets(){
        String s = "";
        for (Ticket t : ticketList){
            s += new Gson().toJson(t) + "\n";
        }
        return s;
    }

}
