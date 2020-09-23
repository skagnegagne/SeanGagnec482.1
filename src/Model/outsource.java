package Model;

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
