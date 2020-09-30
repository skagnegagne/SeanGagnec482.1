package Model;

import javafx.collections.ObservableList;

/** part class holds all of the part information.
 * Getters and setters applied to all the variables of an individual part, ready to be assigned.
 * */
public class Part {
    private int partID;
    private String partName;
    private double partPrice;
    private int partStock;
    private int partMin;
    private int partMax;

    public Part(int ID, String name, double price, int stock, int min, int max){
        partID=ID;
        partName=name;
        partPrice=price;
        partStock=stock;
        partMin=min;
        partMax=max;
    }
    public int getID(){
        return partID; }
    public void setID(int ID){
        partID=ID; }

    public String getName(){
        return partName; }
    public void setName(String name){
        partName=name; }

    public double getPrice(){
        return partPrice; }
    public void setPrice(double price){
        partPrice=price; }

    public int getStock(){
        return partStock; }
    public void setStock(int stock){
        partStock=stock; }

    public int getMin(){
        return partMin; }
    public void setMin(int min){
        partMin=min; }

    public int getMax(){
        return partMax; }
    public void setMax(int max){
        partMax=max; }

}
