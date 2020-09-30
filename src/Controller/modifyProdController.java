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
/** able to change variables in a selected product. have a prod. selected, then click modify to get here.
 * pops up with product info, where you can change any part of it.*/
public class modifyProdController implements Initializable {
    Stage stage;
    Parent scene;
    ObservableList<Part> modifiedAssociatedParts = FXCollections.observableArrayList();
    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productStockField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;
    @FXML
    private TextField partSearchField;
    @FXML
    private TableView<Part> inventoryPartsTableView;
    @FXML
    private TableColumn<Part, Integer> inventoryPartId;
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
        modifiedAssociatedParts.add(inventoryPartsTableView.getSelectionModel().getSelectedItem());
    }
    @FXML
    void onActionDeletePart(ActionEvent event) {
        if(associatedPartsTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            alert.setTitle("Delete forever???");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                modifiedAssociatedParts.removeAll(associatedPartsTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    void onActionReturnToHomeScreen(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Click 'okay' to go back home");
        alert.setTitle("Homeward bound");
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
        if (Integer.parseInt(productStockField.getText()) < Integer.parseInt(productMaxField.getText()) && Integer.parseInt(productStockField.getText()) > Integer.parseInt(productMinField.getText())) {
            int id = Integer.parseInt(productIdField.getText());
            String name = productNameField.getText();
            int stock = Integer.parseInt(productStockField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());
            for (Product product : inventory.getAllProd()) {
                if (product.getProdID() == id) {
                    int productIndex = inventory.getAllProd().indexOf(product);
                    Product modifiedProduct = new Product(id, name, price, stock, min, max);
                    modifiedProduct.getAllAssociatedParts().setAll(modifiedAssociatedParts);
                    inventory.updateProd(productIndex, modifiedProduct);
                }
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Gui/homeScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory must be less than max and more than min.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionSearchPart(ActionEvent event) {
        String partInput = partSearchField.getText();
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

    public void sendProductInfo(Product product) {
        productIdField.setText(Integer.toString(product.getProdID()));
        productNameField.setText(product.getProdName());
        productStockField.setText(Integer.toString(product.getProdStock()));
        productPriceField.setText(Double.toString(product.getProdPrice()));
        productMaxField.setText(Integer.toString(product.getProdMax()));
        productMinField.setText(Integer.toString(product.getProdMin()));

        modifiedAssociatedParts.setAll(product.getAllAssociatedParts());
        associatedPartsTableView.setItems(modifiedAssociatedParts);

        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedStockLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inventoryPartsTableView.setItems(inventory.getAllParts());
        inventoryPartId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        inventoryPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryStockLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventoryPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}