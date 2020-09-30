package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**Product class holds all of the variables for products.
 * Similar to the parts page, getters and setters applied to variables of the products.
 * Troubles linking product to ModifyProductInfo(NulPointerException)
 * Worked on the bottom with associatedPart section*/
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int prodID;
    private String prodName;
    private double prodPrice;
    private int prodStock;
    private int prodMin;
    private int prodMax;
    private ObservableList<Part> allAssociatedParts;

    public Product(int id, String name, double price, int stock, int min, int max) {
        prodID = id;
        prodName = name;
        prodPrice = price;
        prodStock = stock;
        prodMin = min;
        prodMax = max;
    }

    public int getProdID() {
        return prodID;
    }

    public void setProdID(int ID) {
        prodID = ID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String name) {
        prodName = name;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double price) {
        prodPrice = price;
    }

    public int getProdStock() {
        return prodStock;
    }

    public void setProdStock(int stock) {
        prodStock = stock;
    }

    public int getProdMin() {
        return prodMin;
    }

    public void setProdMin(int min) {
        prodMin = min;
    }

    public int getProdMax() {
        return prodMax;
    }

    public void setProdMax(int max) {
        prodMax = max;
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return allAssociatedParts;
    }

    public void setAllAssociatedParts(ObservableList<Part> allAssociatedParts) {
        this.allAssociatedParts = allAssociatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public Part addAssociatedPart(Part tempPart) {
        return tempPart;
    }
}