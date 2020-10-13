package ru.nik.GUI.createObjectForm;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nik.GUI.MainForm.LanguageInt;
import ru.nik.clientPackage.GuiFunctionalClient;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreateObjectController {
    GuiFunctionalClient client;

    LanguageInt languageInt;

    public void setClient(GuiFunctionalClient client) {
        this.client = client;
    }

    public void setLanguageInt(LanguageInt languageInt){
        this.languageInt = languageInt;
    }

    @FXML
    private AnchorPane main_anchor;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text title;

    @FXML
    private Button create_object_button;

    @FXML
    private Text ticket_name;

    @FXML
    private Text x_cor;

    @FXML
    private Text y_cor;

    @FXML
    private Text type;

    @FXML
    private TextField bilet_name;

    @FXML
    private TextField x_coordinate_fiied;

    @FXML
    private TextField y_coordinate_fiied1;

    @FXML
    private RadioButton expensive_choice;

    @FXML
    private RadioButton economical_choise;

    @FXML
    private RadioButton cheap_choise;

    @FXML
    private RadioButton budgetary_choise;

    @FXML
    private Text price;

    @FXML
    private TextField price_field;

    @FXML
    void createObject(ActionEvent event) {
        if(x_coordinate_fiied.getText().isEmpty() || y_coordinate_fiied1.getText().isEmpty() ||
            bilet_name.getText().isEmpty()|| price_field.getText().isEmpty()) {
            create_object_button.setText("введите все значения!");
        }
        try {
            int a = Integer.parseInt(x_coordinate_fiied.getText());
            int b = Integer.parseInt(y_coordinate_fiied1.getText());
            if(a> 400 || a < 0 || b > 400 || b<0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Координаты лежат в диапозоне [0;400]");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
                return;
            }
        } catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("координаты должны иметь числовое значение!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            return;
        }

        ArrayList<String> properties = new ArrayList<>();
        properties.add(x_coordinate_fiied.getText());
        properties.add(y_coordinate_fiied1.getText());
        properties.add(bilet_name.getText());
        properties.add(price_field.getText());
        if(cheap_choise.isSelected()){
            properties.add("CHEAP");
            cheap_choise.fire();
        } else if (budgetary_choise.isSelected()){
            properties.add("BUDGETARY");
            budgetary_choise.fire();
        } else if (expensive_choice.isSelected()){
            properties.add("VIP");
            expensive_choice.fire();
        } else if(economical_choise.isSelected()){
            properties.add("USUAL");
            economical_choise.fire();
        }
        client.createObjectQuery("add", properties);
        Stage stage = (Stage)create_object_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pushBudgetary(ActionEvent event) {
        cheap_choise.setSelected(false);
        economical_choise.setSelected(false);
        expensive_choice.setSelected(false);
    }

    @FXML
    void pushCheap(ActionEvent event) {
        budgetary_choise.setSelected(false);
        economical_choise.setSelected(false);
        expensive_choice.setSelected(false);
    }

    @FXML
    void pushEconomical(ActionEvent event) {
        budgetary_choise.setSelected(false);
        cheap_choise.setSelected(false);
        expensive_choice.setSelected(false);
    }

    @FXML
    void pushExpensive(ActionEvent event) {
        budgetary_choise.setSelected(false);
        cheap_choise.setSelected(false);
        economical_choise.setSelected(false);
    }

    @FXML
    void initialize() {
        System.out.println(languageInt);
        if (languageInt.equals(LanguageInt.EN)) {
            switchLan(ResourceBundle.getBundle("Locale/extraWin_en", Locale.ENGLISH));
        }
        else if (languageInt.equals(LanguageInt.RU)){
            switchLan(ResourceBundle.getBundle("Locale/extra_ru_RU"));
        }
    }

    void switchLan(ResourceBundle resourceBundle){
            for (Node node : main_anchor.getChildren()){
                try {

                if(node instanceof Text){
                    ((Text) node).setText(resourceBundle.getString(node.getId()));
                }
                if (node instanceof Button){
                    ((Button) node).setText(resourceBundle.getString(node.getId()));
                }
                if (node instanceof RadioButton){
                    ((RadioButton) node).setText(resourceBundle.getString(node.getId()));
                }

                } catch (NullPointerException ex){
                    System.out.print(node.getId());
                }
            }


        }

    }

