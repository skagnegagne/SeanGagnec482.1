package Model;
/** inHouse extends the part class to add machine ID. Accessed by radio button in "addPart" and "modifyPart" to toggle between companyName and machineID.*/
public class inHouse extends Part {
    private int machineID;
    public inHouse(int ID, String name, double price, int stock, int min, int max, int machineID) {
        super(ID, name, price, stock, min, max);
        this.machineID =machineID;
    }
    public int getMachineID(){
        return machineID; }
    public void setMachineID(int machineID){
        this.machineID=machineID;
    }
}
