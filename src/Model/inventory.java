package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProd = FXCollections.observableArrayList();
    public static void addPart(Part part){
        allParts.add(part);
    }
    //Parts First

    public static Part lookupPart(int partID) {
        for(Part part:allParts){
            if(part.getID()==partID){
                return part;
            }
        }
        return null;
    }
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> filterPartResult = FXCollections.observableArrayList();
        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                filterPartResult.add(part);
            }
        }
        if(filterPartResult.isEmpty()){
            return allParts;
        }
        return filterPartResult;
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }
    public static void deletePart(Part part){
        allParts.remove(part);
    }

    //Then Products

    public static void addProd(Product product){
        allProd.add(product);
    }
    public static Product lookupProd(int prodID){
        for(Product product:allProd){
            if(product.getProdID()==prodID){
                return product;
            }
        }
        return null;
    }
    public static ObservableList<Product> lookupProduct(String prodName){
        ObservableList<Product> filterProdResult = FXCollections.observableArrayList();
        for(Product product: allProd){
            if(product.getProdName().toLowerCase().contains(prodName.toLowerCase())){
                filterProdResult.add(product);
            }
        }
        if(filterProdResult.isEmpty()){
            return allProd;
        }
        return filterProdResult;
    }
    public static ObservableList<Product> getAllProd(){
        return allProd;
    }
    public static void updateProd(int index, Product product){
        allProd.set(index, product);
    }
    public static void deleteProd(Product product){
        allProd.remove(product);
    }

    public static Product lookupProduct(int prodID) {
        return null;
    }
}
