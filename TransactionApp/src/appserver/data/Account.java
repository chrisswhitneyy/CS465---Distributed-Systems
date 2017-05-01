package appserver.data;

/**
 * Created by cdubs on 4/27/17.
 */
public class Account {

    private int id;
    private int amount;

    public Account(){
        self.id = null;
        self.amount = null;
    }
    public Account(int id, int amount){
        self.id = id;
        self.amount = amount;
    }
    public int getId(){
        return self.id;
    }
    public void setId(int id){
        self.id = id;
    }
    public int getAmount(){
        return self.amount;
    }
    public void setAmount(int amount){
        self.amount = amount;
    }

}
