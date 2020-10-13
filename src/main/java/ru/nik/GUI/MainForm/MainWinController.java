package ru.nik.GUI.MainForm;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import com.google.gson.Gson;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LongStringConverter;
import ru.nik.GUI.createObjectForm.CreateIfObjectController;
import ru.nik.GUI.createObjectForm.CreateObjectController;
import ru.nik.businessLogic.ticketClasses.Coordinates;
import ru.nik.businessLogic.ticketClasses.Ticket;
import ru.nik.businessLogic.ticketClasses.TicketType;
import ru.nik.clientPackage.GuiFunctionalClient;

import javax.swing.plaf.synth.ColorType;

public class MainWinController {

    private String chat_area_text = "";
    private GuiFunctionalClient client;

    private Ticket currentClickedTicket;

    private TableColumn<Ticket,String> ticketColumn ;
    private TableColumn<Ticket,Long> priceColumn ;
    private TableColumn<Ticket, String> coordinatesColumn ;
    private TableColumn<Ticket,Double> xCoordinateColumn;
    private TableColumn<Ticket,Double> yCoordinateColumn ;
    private TableColumn<Ticket, TicketType> typeColumn ;
    private TableColumn<Ticket, String> creationDateColumn;
    private TableColumn<Ticket, String> ownerColumn;
    private TableColumn<Ticket, Long> idColumn;


    private Map<Pair<Double,Double>, Ticket> vizualTicketMap = new HashMap<>();

    private Map<String, Color> userColorMap = new HashMap<>();

    private List<Color> colorSet= new ArrayList<>();

    private ArrayList<Ticket> currentCollection = new ArrayList<>();


    private Ticket currentTicket;

    private LanguageInt languageInt;




    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane vizual_layout;

    @FXML
    private Canvas vizual_area;

    @FXML
    private TextField ticket_visual_name;

    @FXML
    private TextField ticket_visual_price;

    @FXML
    private TextField ticket_visual_owner;

    @FXML
    private TextField ticket_visual_type;

    @FXML
    private TextField filter_area;

    @FXML
    private Button to_collection_from_button;

    @FXML
    private AnchorPane options_loyaut;

    @FXML
    private Button add_element_button;

    @FXML
    private Button remove_element_button;

    @FXML
    private Button help_button;

    @FXML
    private Button sort_button;

    @FXML
    private Button info_button;

    @FXML
    private Button add_if_button1;

    @FXML
    private Button clear_button;

    @FXML
    private Button history_button;

    @FXML
    private TextField id_value;

    @FXML
    private ScrollPane result_text_area;

    @FXML
    private Button to_collection_button;

    @FXML
    private Button to_chat_button;

    @FXML
    private AnchorPane chat_loyaout;

    @FXML
    private TextField message_area;

    @FXML
    private Button SEND_BUTTON;

    @FXML
    private TextArea chat_area;

    @FXML
    private Button to_profile_button;

    @FXML
    private TextField user_name;

    @FXML
    private AnchorPane profile_loyaut;

    @FXML
    private ImageView avatar;

    @FXML
    private Text monitor_title;

    @FXML
    private TextArea result_area;

    @FXML
    private Text user_login_area;

    @FXML
    private Text number_elemens_area;

    @FXML
    private Text priority_area;

    @FXML
    private ImageView canvas_image;

    @FXML
    private Button translate_en_button;

    @FXML
    private Text price_area;
    @FXML
    private AnchorPane table_loyaout;

    @FXML
    private ScrollPane table_scroll;

    @FXML
    private TableView<Ticket> objectTable;

    @FXML
    private Button to_collection_options;

    @FXML
    private Button to_graphic_collection;

    @FXML
    private Button clearScreenButton;

    @FXML
    private AnchorPane main_frame;

    @FXML
    private Text option_title;

    @FXML
    private Button update_ticket_button;

    @FXML
    void clearScreen(ActionEvent event) {
            result_area.clear();
    }

    @FXML
    void updateTicketAction (ActionEvent event ){
        client.createQuery("remove_by_id", String.valueOf(currentTicket.getId()));
        currentClickedTicket.setType(TicketType.valueOf(ticket_visual_type.getText()));
        currentClickedTicket.setPrice(Long.parseLong(ticket_visual_price.getText()));
        currentClickedTicket.setName(ticket_visual_name.getText());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client.createQuery("add", new Gson().toJson(new Ticket(currentTicket)));

    }

    public void setClient(GuiFunctionalClient client){
        this.client=client;
    }
    @FXML
    void addElement(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            CreateObjectController createObjectController = new CreateObjectController();
            createObjectController.setLanguageInt(languageInt);
            createObjectController.setClient(client);
            loader.setController(createObjectController);
            loader.setLocation(getClass().getResource("/createObj.fxml"));
            Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void addIf(ActionEvent event) {
        try {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        CreateIfObjectController createObjectController = new CreateIfObjectController();
        createObjectController.setClient(client);
        loader.setController(createObjectController);
        loader.setLocation(getClass().getResource("/createIfObj.fxml"));
        Parent root = (Parent)loader.load();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
     } catch (IOException e){
        e.printStackTrace();
     }
    }

    @FXML
    void translateToEn(ActionEvent event) {
        languageInt = LanguageInt.EN;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Locale/source_en",Locale.ENGLISH);
        updateLan(resourceBundle);

    }

    @FXML
    void translateToRU(ActionEvent event){
        languageInt = LanguageInt.RU;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Locale/ru_RU");
        updateLan(resourceBundle);
    }

    void updateLan(ResourceBundle resourceBundle){
        for(Node Outnode : main_frame.getChildren()) {
            if (Outnode instanceof AnchorPane) {
                for (Node node : ((AnchorPane) Outnode).getChildren()) {
                    try {
                        if (node instanceof Button) {
                            ((Button) node).setText(resourceBundle.getString(node.getId()));
                        }
                        if (node instanceof Text) {
                            if (node.getId().equals("user_login_area") || node.getId().equals("number_elemens_area")
                                    || node.getId().equals("priority_area") || node.getId().equals("price_area")) {
                                continue;
                            }
                            ((Text) node).setText(resourceBundle.getString(node.getId()));
                        }
                        if (node instanceof TableView) {
                            for (Object columns : ((TableView) node).getColumns()) {
                                TableColumn column = (TableColumn) columns;
                                System.out.println(column.getId());
                                column.setText(resourceBundle.getString(column.getId()));
                            }
                        }
                    } catch (NullPointerException | MissingResourceException ex) {
                        System.out.print("");
                    }


                }
            }
        }

        for (Object columns : objectTable.getColumns()) {
            try {
                TableColumn column = (TableColumn) columns;
                column.setText(resourceBundle.getString(column.getId()));
            } catch (NullPointerException | MissingResourceException ex) {
                System.out.print("");
            }
        }

    }

    @FXML
    void clear(ActionEvent event) {
        client.createQuery("clear", null);
    }

    @FXML
    void goToProfile(ActionEvent event) {
        profile_loyaut.setVisible(true);
        options_loyaut.setVisible(false);
        chat_loyaout.setVisible(false);
        table_loyaout.setVisible(false);
        vizual_layout.setVisible(false);
    }

    @FXML
    void help(ActionEvent event) {
        client.createQuery("help", null);
    }


    @FXML
    void history(ActionEvent event) {
        result_area.appendText("Oops, coming soon");
    }

    @FXML
    void info(ActionEvent event) {
        client.createQuery("info", null);
    }

    @FXML
    void removeElement(ActionEvent event) {
        try{
            int id = Integer.parseInt(id_value.getText());
            client.createQuery("remove_by_id",String.valueOf(id));
        } catch (Exception e){
            id_value.setText("");
        }

    }

    @FXML
    void sendMessage(ActionEvent event) {
        client.sendMessage(user_login_area.getText(), message_area.getText());
    }

    @FXML
    void sort(ActionEvent event) {
        client.createQuery("sort", null);
    }
    @FXML
    void toProfileFromOptions(ActionEvent event){
        options_loyaut.setVisible(false);
        table_loyaout.setVisible(false);
        vizual_layout.setVisible(false);
    }



    @FXML
    void toOptions(ActionEvent event) {
        options_loyaut.setVisible(true);
        table_loyaout.setVisible(false);
        vizual_layout.setVisible(false);
    }
    @FXML
    void toCollection(ActionEvent event) {
        options_loyaut.setVisible(false);
        table_loyaout.setVisible(true);
        vizual_layout.setVisible(false);
    }
    @FXML
    void toGraphic(ActionEvent event){
        options_loyaut.setVisible(false);
        table_loyaout.setVisible(false);
        vizual_layout.setVisible(true);
    }
    @FXML
    void toCollectionOptions(ActionEvent event){
        options_loyaut.setVisible(true);
        table_loyaout.setVisible(false);
        vizual_layout.setVisible(false);
    }
    @FXML
    void toCollectionFromVizual(ActionEvent event){
        options_loyaut.setVisible(false);
        table_loyaout.setVisible(true);
        vizual_layout.setVisible(false);

    }
    @FXML
    void sortTable(ActionEvent event){
        System.out.println("sort");
    }

    void filterTable(){

    }

    @FXML
    void initialize() {
            languageInt = LanguageInt.RU;
            updateLan(ResourceBundle.getBundle("Locale/ru_RU"));
            setVizualFieldStatus(true);
            ticket_visual_owner.setDisable(true);
            colorInitialisation();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkMouseCoordinate();
            char firstLetter = client.getCurrentUserInfo().getLogin().toLowerCase().charAt(0);
            tableCreator();
            result_text_area.setContent(result_area);
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String message = client.getNextMessage();
                    if (message != null) {
                        chat_area.appendText(message);
                    }
                }
            }).start();
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String result = client.getNextInfo();
                    if (result != null) {
                        result_area.appendText(result + "\n");
                    }
                }
            }).start();
            new Thread(new Runnable() {
                ArrayList<Ticket> updateCollection = new ArrayList<>();

                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ArrayList<Ticket> buffer = client.getNextCollection();
                        if (!(buffer == null)) {
                            updateCollection.addAll(buffer);
                            currentCollection = buffer;
                            drawCircles(updateCollection);
                            updateTable(updateCollection);
                            updateCollection.clear();
                        }
                    }
                }
            }).start();

            filter_area.textProperty().addListener((observable, oldValue, newValue) -> {
                //objectTable.getItems().clear();
                ObservableList<Ticket> tableData = FXCollections.observableArrayList();
                tableData.addAll(currentCollection);
                FilteredList<Ticket> tickets = new FilteredList<>(tableData, p -> true);
                tickets.setPredicate(ticket -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    try {
                        if (ticket.getName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;

                        }
                        if(ticket.getType().toString().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                        }
                    } catch (NullPointerException ex) {
                        return false;
                    }

                    return false;
                });
                SortedList<Ticket> sortedData = new SortedList<>(tickets);
                sortedData.comparatorProperty().bind(objectTable.comparatorProperty());
                objectTable.setItems(sortedData);
            });


    }
    void tableCreator(){
        ticketColumn = new TableColumn<>("Имя билета");
         priceColumn = new TableColumn<>("Цена билета");
         coordinatesColumn = new TableColumn<>("Координаты");
        xCoordinateColumn = new TableColumn<>("x");
        yCoordinateColumn = new TableColumn<>("y");
        coordinatesColumn.getColumns().addAll(xCoordinateColumn,yCoordinateColumn);
        typeColumn = new TableColumn<>("Тип билета");
        creationDateColumn = new TableColumn<>("Дата создания");
        ownerColumn = new TableColumn<>("Создатель");
        idColumn = new TableColumn<>("Id");

        ticketColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        xCoordinateColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yCoordinateColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("bruhDate"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        idColumn.setCellValueFactory((new PropertyValueFactory<>("id")));


        ticketColumn.setSortable(true);
        priceColumn.setSortable(true);
        xCoordinateColumn.setSortable(true);
        yCoordinateColumn.setSortable(true);
        typeColumn.setSortable(true);
        creationDateColumn.setSortable(true);
        ownerColumn.setSortable(true);

        ticketColumn.setId("name_column");
        priceColumn.setId("price_column");
        xCoordinateColumn.setId("x_column");
        yCoordinateColumn.setId("y_column");
        typeColumn.setId("type_column");
        creationDateColumn.setId("date_column");
        ownerColumn.setId("owner_column");
        coordinatesColumn.setId("coordinates_column");



        objectTable.setEditable(true);
        ticketColumn.setEditable(true);
        priceColumn.setEditable(true);
        xCoordinateColumn.setEditable(true);
        yCoordinateColumn.setEditable(true);
        typeColumn.setEditable(true);

        ticketColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        xCoordinateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        yCoordinateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        ticketColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Ticket, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ticket, String> t) {
                Ticket sendTicket =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                if(t.getNewValue().equals("")){
                    client.createQuery("remove_by_id", String.valueOf(sendTicket.getId()));
                    return;
                }
                if(!sendTicket.getOwner().equals(client.getClientName())){
                    return;
                }

                client.createQuery("remove_by_id", String.valueOf(sendTicket.getId()));
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setName(t.getNewValue());
                Ticket send2 = t.getTableView().getItems().get(t.getTablePosition().getRow());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.createQuery("add", new Gson().toJson(new Ticket(send2)));
            }
        });

        priceColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Ticket, Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ticket, Long> t) {
                Ticket sendTicket =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                if(!sendTicket.getOwner().equals(client.getClientName())){
                    return;
                }
                client.createQuery("remove_by_id", String.valueOf(sendTicket.getId()));
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setPrice(t.getNewValue());
                Ticket send2 = t.getTableView().getItems().get(t.getTablePosition().getRow());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.createQuery("add", new Gson().toJson(new Ticket(send2)));
            }
        });

        xCoordinateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Ticket, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ticket, Double> t) {

                if ( t.getNewValue() > 400 || t.getNewValue() <0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Координаты лежат в диапазоне 0;400");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                    return;
                }
                Ticket sendTicket =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                if(!sendTicket.getOwner().equals(client.getClientName())){
                    return;
                }
                client.createQuery("remove_by_id", String.valueOf(sendTicket.getId()));
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).getCoordinates().setX(t.getNewValue());
                Ticket send2 = t.getTableView().getItems().get(t.getTablePosition().getRow());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.createQuery("add", new Gson().toJson(new Ticket(send2)));
            }
        });

        yCoordinateColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Ticket, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ticket, Double> t) {
                if ( t.getNewValue() > 400 || t.getNewValue() <0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Координаты лежат в диапазоне 0;400");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            System.out.println("Pressed OK.");
                        }
                    });
                    return;
                }
                Ticket sendTicket =  t.getTableView().getItems().get(t.getTablePosition().getRow());
                if(!sendTicket.getOwner().equals(client.getClientName())){
                    return;
                }
                client.createQuery("remove_by_id", String.valueOf(sendTicket.getId()));
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).getCoordinates().setY(t.getNewValue());
                Ticket send2 = t.getTableView().getItems().get(t.getTablePosition().getRow());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.createQuery("add", new Gson().toJson(new Ticket(send2)));
            }
        });

                objectTable.getColumns().addAll(idColumn, ticketColumn, priceColumn, coordinatesColumn, typeColumn, creationDateColumn, ownerColumn);
    }
    void updateTable(ArrayList<Ticket> list){
        ObservableList<Ticket> tableData = FXCollections.observableArrayList();
        try {
            for(int i = 0; i < list.size(); i++){
                list.removeIf(Objects::isNull);
                Ticket ticket = list.get(i);
                list.get(i).setX(ticket.getCoordinates().getX());
                list.get(i).setY(ticket.getCoordinates().getY());
                list.get(i).setBruhDate(new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm", Locale.ROOT).format(ticket.getCreationDate()));

            }
        } catch (NullPointerException | IndexOutOfBoundsException ex){
            System.out.print("");
        }
        if(languageInt.equals(LanguageInt.EN)) {
            for (Ticket ticket : list) {
                String date = new SimpleDateFormat("yyyy.MM.dd K:mm a, z").format(ticket.getCreationDate());
                ticket.setBruhDate(date);
            }
        }

        tableData.clear();
        tableData.addAll(list);
        FilteredList<Ticket> filteredData = new FilteredList<>(tableData, p->true);
        SortedList<Ticket> sortedData = new SortedList<>(tableData);
        sortedData.comparatorProperty().bind(objectTable.comparatorProperty());
        objectTable.setItems(sortedData);

    }
    void drawCircles(ArrayList<Ticket> list){
        GraphicsContext graphics = vizual_area.getGraphicsContext2D();
        graphics.clearRect(0,0,vizual_area.getWidth(),vizual_area.getHeight());
        try {
            for(Ticket t : list){
                graphics.setFill(setPersonColor(t.getOwner()));
                graphics.fillOval(t.getCoordinates().getX(),t.getCoordinates().getY(),40,40);
                vizualTicketMap.put(new Pair<Double,Double>(t.getCoordinates().getX()+10,t.getCoordinates().getY()+10),t);
            }
        } catch (NullPointerException ex) {
            System.out.print("");
        }

    }

    public void checkMouseCoordinate(){
        vizual_area.setOnMouseClicked(event -> {
                for (Pair<Double, Double> pair : vizualTicketMap.keySet()) {
                    if ( Math.sqrt(Math.pow(event.getX()-pair.getKey(),2)+ Math.pow(event.getY()-pair.getValue(),2)) < 20 ) {
                       Ticket buff = vizualTicketMap.get(pair);
                       currentClickedTicket = buff;
                       ticket_visual_name.setText(buff.getName());
                       ticket_visual_owner.setText(buff.getOwner());
                       ticket_visual_price.setText(String.valueOf((new Long(buff.getPrice()))));
                       ticket_visual_type.setText(String.valueOf(buff.getType()));
                       currentTicket = buff;

                       if(ticket_visual_owner.getText().equals(client.getClientName())){
                            setVizualFieldStatus(false);
                       } else {
                           setVizualFieldStatus(true);
                       }
                    }

            }
        });
    }

    void setVizualFieldStatus(boolean status){
        ticket_visual_type.setDisable(status);
        ticket_visual_price.setDisable(status);
        ticket_visual_name.setDisable(status);
        update_ticket_button.setDisable(status);
    }

    Color setPersonColor(String username){
        if(userColorMap.containsKey(username)){
            return userColorMap.get(username);
        }
        int random = new Random().nextInt(colorSet.size());
        Color color = colorSet.get(random);
        userColorMap.put(username, color);
        colorSet.remove(random);
        return color;

    }

    void colorInitialisation(){
        colorSet.add(Color.BLUE);
        colorSet.add(Color.ALICEBLUE);
        colorSet.add(Color.AQUA);
        colorSet.add(Color.AZURE);
        colorSet.add(Color.CADETBLUE);
        colorSet.add(Color.CHOCOLATE);
        colorSet.add(Color.DARKBLUE);
        colorSet.add(Color.DARKSLATEBLUE);
        colorSet.add(Color.DEEPSKYBLUE);
        colorSet.add(Color.GHOSTWHITE);
        colorSet.add(Color.YELLOWGREEN);
        colorSet.add(Color.TAN);
        colorSet.add(Color.SILVER);
        colorSet.add(Color.TOMATO);
        colorSet.add(Color.LIME);
        colorSet.add(Color.PEACHPUFF);

    }
}
