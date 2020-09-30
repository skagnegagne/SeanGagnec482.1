package Controller;

import Model.inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Functioning app screen with controller. Adds text fields to receive user input for product information.
 * Part ID error fixed by fixing cellValue name.  I had errors in both partID tables before.*/
public class addProdController implements Initializable {
    Stage stage;
    Parent scene;
    ObservableList<Part> tempAssociatedPartsList = FXCollections.observableArrayList();

    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInventory;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField addProductPartSearchField;
    @FXML
    private TableView<Part> inventoryPartsTableView;
    @FXML
    private TableColumn<Part, Integer> inventoryPartID;
    @FXML
    private TableColumn<Part, String> inventoryPartName;
    @FXML
    private TableColumn<Part, Integer> inventoryStockLevel;
    @FXML
    private TableColumn<Part, Double> inventoryPrice;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartId;
    @FXML
    private TableColumn<Part, String> associatedPartName;
    @FXML
    private TableColumn<Part, Integer> associatedStockLevel;
    @FXML
    private TableColumn<Part, Double> associatedPrice;

    @FXML
    void onActionAddPart(ActionEvent event) {
        tempAssociatedPartsList.add(inventoryPartsTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        if(associatedPartsTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You'll lose this part forever if you delete it.");
            alert.setTitle("If you're sure...");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                tempAssociatedPartsList.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    void onActionReturnToHomeScreen(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Back to the home screen?");
        alert.setTitle("Are you sure?");
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
        int id = inventory.getAllProd().size() + 1;
        String name = addProductName.getText();
        double price = Double.parseDouble(addProductPrice.getText());
        int stock = Integer.parseInt(addProductInventory.getText());
        int min = Integer.parseInt(addProductMin.getText());
        int max = Integer.parseInt(addProductMax.getText());
        if (stock < max && stock > min) {
            inventory.addProd(new Product(id, name, price, stock, min, max));
            for (Part tempPart : tempAssociatedPartsList) {
                inventory.getAllProd().get(inventory.getAllProd().size() - 1).addAssociatedPart(tempPart);
            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Gui/homeScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory must be more than min and less than max.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionSearchProductPart(ActionEvent event) {
        String partInput = addProductPartSearchField.getText();
        try {
            int partId = Integer.valueOf(partInput);
            ObservableList<Part> searchResult = FXCollections.observableArrayList();
            searchResult.add(inventory.lookupPart(partId));

            if (searchResult.get(0) == null) {
                inventoryPartsTableView.setItems(inventory.getAllParts());
            } else {
                inventoryPartsTableView.setItems(searchResult);
            }
        } catch (NumberFormatException e) {
            inventoryPartsTableView.setItems(inventory.lookupPart(partInput));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inventoryPartsTableView.setItems(inventory.getAllParts());
        inventoryPartID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        inventoryPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryStockLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventoryPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(tempAssociatedPartsList);

        addProductID.setText(String.valueOf(inventory.getAllProd().size()+1));
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedStockLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}