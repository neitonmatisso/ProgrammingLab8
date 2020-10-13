package ru.nik.GUI.createObjectForm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nik.clientPackage.GuiFunctionalClient;

public class CreateIfObjectController {
    GuiFunctionalClient client;

    String arguments = "";

    public void setClient(GuiFunctionalClient client) {
        this.client = client;
    }

    @FXML
    private TextField path_field;

    @FXML
    private Button script_button;

    @FXML
    void loadScript(ActionEvent event) {
        try {
            scriptLoader(path_field.getText());
        } catch (Exception exception){
            path_field.setText("File not found");
            return;
        }
        client.createQuery("execute",arguments);

    }

    @FXML
    void initialize() {

    }

    public void scriptLoader(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(filePath));
        while (sc.hasNext()) {
            String buffe2 = sc.nextLine();
            List<String> inCommand = Arrays.asList(buffe2.split(" "));
            if (inCommand.get(0).equals("execute")) {
                scriptLoader(inCommand.get(1));
                continue;
            }
            if (inCommand.size() == 1) {
                arguments += inCommand.get(0) + "%";
                continue;
            }
            if (inCommand.size() == 2) {
                arguments += inCommand.get(0) + " " + inCommand.get(1) + "%";
                continue;
            }
        }

    }
}
