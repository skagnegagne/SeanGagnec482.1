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

public class homeScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField partSearchField;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Gui/addPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Gui/addProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        if(partsTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You ain't getting this part back if you delete it.  You sure?");
            alert.setTitle("CONFIRMATION");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
            }
        } else {
            System.out.println("Select part to delete");
        }
    }

    @FXML
    void onActionDeleteProd(ActionEvent event) {

        if(productsTableView.getSelectionModel().getSelectedItem() != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            alert.setTitle("CONFIRMATION");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                inventory.deleteProd(productsTableView.getSelectionModel().getSelectedItem());
            }
        } else {
            System.out.println("Select product to delete");
        }
    }
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Gui/modifyPart.fxml"));
            loader.load();

            modifyPartController modPartController = loader.getController();
            modPartController.sendPartInfo(partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e);
            System.out.println("No part selected!");
        }
    }
    @FXML
    void onActionModifyProd(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Gui/modifyProduct.fxml"));
            loader.load();

            modifyProdController modProdController = loader.getController();
            modProdController.sendProductInfo(productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e);
            System.out.println("No product selected!");
        }

    }

    @FXML
    void onActionPartsSearch(ActionEvent event) {

        String partInput = partSearchField.getText();

        try {
            int partId = Integer.valueOf(partInput);
            ObservableList<Part> searchResult = FXCollections.observableArrayList();
            searchResult.add(inventory.lookupPart(partId));

            if (searchResult.get(0) == null) {
                partsTableView.setItems(inventory.getAllParts());
            } else {
                partsTableView.setItems(searchResult);
            }
        } catch (NumberFormatException e) {
            partsTableView.setItems(inventory.lookupPart(partInput));
        }
    }

    @FXML
    void onActionProductsSearch(ActionEvent event) {

        String productInput = productSearchField.getText();

        try {
            int prodID = Integer.valueOf(productInput);
            ObservableList<Product> searchResult = FXCollections.observableArrayList();
            searchResult.add(inventory.lookupProduct(prodID));

            if(searchResult.get(0) == null) {
                productsTableView.setItems(inventory.getAllProd());
            } else {
                productsTableView.setItems(searchResult);
            }
        } catch (NumberFormatException e) {
            productsTableView.setItems(inventory.lookupProduct(productInput));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set Parts table view
        partsTableView.setItems(inventory.getAllParts());

        // Fill Parts column with values
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set Products table view
        productsTableView.setItems(inventory.getAllProd());

        // Fill Products column with values

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}