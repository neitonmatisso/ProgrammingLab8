package ru.nik.GUI.registrationForm;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.nik.GUI.MainForm.MainWinController;
import ru.nik.clientPackage.GuiFunctionalClient;
import ru.nik.clientPackage.RequestType;
import ru.nik.clientPackage.ServerStatus;

public class Controller  {
    private GuiFunctionalClient client;
    public FXMLLoader winLoader;
    public FXMLLoader infoLoader;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button info_button;

    @FXML
    private TextField login_area;

    @FXML
    private TextField password_area;

    @FXML
    private Button sighin_button;

    @FXML
    private Button register_button;

    @FXML
    private Text info_message;

    public void setValues (GuiFunctionalClient client){
        this.client = client;
    }

    @FXML
    public void initialize() {
        MainWinController mainWinController = new MainWinController();
        mainWinController.setClient(client);
        info_button.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/info.fxml"));
                Parent root = (Parent)loader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e){
                info_message.setText("Временная ошибка");
                e.printStackTrace();
            }
        });
        register_button.setOnAction(event -> {
            client.connectToServer();
            if(client.getServerStatus().equals(ServerStatus.CLOSE)){
                info_message.setText("Сервер недоступен");
                return;
            }
            String login = login_area.getText();
            String pass = password_area.getText();
            if (login.isEmpty() || pass.isEmpty()) {
                info_message.setText("Вы ввели пустые строки, так нельзя");
                return;
            }
            client.register(RequestType.REGISTER, login,pass);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String error = client.getNextError();
            if( !(error == null)){
                info_message.setText(error);
                login_area.setText("");
                password_area.setText("");
                return;
            }
            try {
                client.getNextMessage();
            } catch (NullPointerException e){
                info_message.setText("повторите попытку");
                return;
            }

            loadMainWin(mainWinController);

        });
        sighin_button.setOnAction(event -> {
            client.connectToServer();
            if(client.getServerStatus().equals(ServerStatus.CLOSE)){
                info_message.setText("Сервер недоступен");
                return;
            }

            String login = login_area.getText();
            String pass = password_area.getText();
            if (login.isEmpty() || pass.isEmpty()) {
               info_message.setText("Вы ввели пустые строки, так нельзя");
               return;
            }
            client. register(RequestType.LOGIN, login,pass);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String error = client.getNextError();
            System.out.println(error);
            if( !(error == null)){
                info_message.setText(error);
                login_area.setText("");
                password_area.setText("");
                return;
            }
            try {
                client.getNextMessage();
            } catch (NullPointerException e){
                info_message.setText("повторите попытку");
                return;
            }
            loadMainWin(mainWinController);

        });
    }
    public void loadMainWin (MainWinController mainWinController){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setController(mainWinController);
            loader.setLocation(getClass().getResource("/MainWin.fxml"));
            Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
            register_button.getScene().getWindow().hide();
        } catch (IOException e){
            info_message.setText("Временная ошибка");
            e.printStackTrace();
        }
    }
}

