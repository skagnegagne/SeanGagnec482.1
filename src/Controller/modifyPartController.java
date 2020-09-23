package Controller;

import Model.inHouse;
import Model.inventory;
import Model.outsource;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyPartController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private RadioButton modPartInHouse;
    @FXML
    private ToggleGroup partSource;
    @FXML
    private RadioButton modPartOutsourced;
    @FXML
    private Label modPartVariableName;
    @FXML
    private TextField partIdField;
    @FXML
    private TextField modPartVariableField;
    @FXML
    private TextField partPriceField;
    @FXML
    private TextField partStockField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField partMaxField;
    @FXML
    private TextField partMinField;
    @FXML
    void onActionModPartInHouse(ActionEvent event) {
        modPartVariableName.setText("Machine ID:");
    }

    @FXML
    void onActionModPartOutsource(ActionEvent event) {
        modPartVariableName.setText("Company Name:");
    }

    @FXML
    void onActionReturnToHomeScreen(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes won't be saved.  Do you want to go back?");
        alert.setTitle("CONFIRMATION");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Gui/homeScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        if(Integer.parseInt(partStockField.getText()) < Integer.parseInt(partMaxField.getText()) && Integer.parseInt(partStockField.getText()) > Integer.parseInt(partMinField.getText())) {
            int id = Integer.parseInt(partIdField.getText());
            String name = partNameField.getText();
            int stock = Integer.parseInt(partStockField.getText());
            double price = Double.parseDouble(partPriceField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            for(Part part: inventory.getAllParts()) {
                if (part.getID() == id) {
                    int partIndex = inventory.getAllParts().indexOf(part);
                    if (modPartInHouse.isSelected()) {
                        if (part instanceof inHouse) {
                            part.setName(name);
                            part.setStock(stock);
                            part.setPrice(price);
                            part.setMax(max);
                            part.setMin(min);
                            ((inHouse) part).setMachineID(Integer.parseInt(modPartVariableField.getText()));
                            break;
                        } else {
                            Part inHousePart = new inHouse(id, name, price,stock,min,max, Integer.parseInt(modPartVariableField.getText()));
                            inventory.updatePart(partIndex, inHousePart);
                            break;
                        }
                    } else {
                        if(part instanceof outsource) {
                            part.setName(name);
                            part.setStock(stock);
                            part.setPrice(price);
                            part.setMax(max);
                            part.setMin(min);
                            ((outsource) part).setCompanyName(modPartVariableField.getText());
                            break;
                        } else {
                            Part outSrcPart = new outsource(id, name, price, stock, min, max, modPartVariableField.getText());
                            inventory.updatePart(partIndex, outSrcPart);
                            break;
                        }
                    }
                }
            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Gui/homeScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Minimum must be greater than the Maximum value.");
            alert.showAndWait();
        }
    }

    public void sendPartInfo(Part part) {
        if(part instanceof inHouse) {
            modPartInHouse.setSelected(true);
            modPartVariableName.setText("Machine ID:");
            modPartVariableField.setText(String.valueOf(((inHouse) part).getMachineID()));
        } else {
            modPartOutsourced.setSelected(true);
            modPartVariableName.setText("Company Name:");
            modPartVariableField.setText(((outsource) part).getCompanyName());
        }
        partIdField.setText(String.valueOf(part.getID()));
        partNameField.setText(part.getName());
        partStockField.setText(String.valueOf(part.getStock()));
        partPriceField.setText(String.valueOf(part.getPrice()));
        partMaxField.setText(String.valueOf(part.getMax()));
        partMinField.setText(String.valueOf(part.getMin()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}