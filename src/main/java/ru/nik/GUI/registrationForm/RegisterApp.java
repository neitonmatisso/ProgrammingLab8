package ru.nik.GUI.registrationForm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.nik.GUI.infoForm.InfoController;

import ru.nik.clientPackage.GuiFunctionalClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GuiFunctionalClient guiFunctionalClient = new GuiFunctionalClient();
        FXMLLoader loaderRegister = new FXMLLoader(RegisterApp.class.getResource("/gui.fxml"));
        Controller controller = new Controller();
        controller.setValues(guiFunctionalClient);
        loaderRegister.setController(controller);
        primaryStage.setScene(new Scene(loaderRegister.load()));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
