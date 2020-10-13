package ru.nik.GUI.infoForm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InfoController {
    @FXML
    private Button close_button;
    @FXML
    void initialize() {
        close_button.setOnAction(event -> {
            Stage stage = (Stage)close_button.getScene().getWindow();
            stage.close();
        });
    }

}
