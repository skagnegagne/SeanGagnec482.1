package Model;
/** Model.outsource adds companyName into the part class. outsource extends part to add company name, toggled to and from with radio buttons on add/modify screen. */
public class outsource extends Part {
    private String companyName;
    public outsource(int ID, String name, double price, int stock, int min, int max, String companyName) {
        super(ID, name, price, stock, min, max);
        this.companyName=companyName;
    }
    public String getCompanyName(){
        return companyName; }
    public void setCompanyName(String companyName){
        this.companyName=companyName;
    }
}
