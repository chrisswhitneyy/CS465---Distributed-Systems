package appserver.data;

/**
 * Created by cdubs on 4/27/17.
 */
public class Account {

    private int id;
    private int amount;

    public Account(){}

    public Account(int id, int amount){
        this.id = id;
        this.amount = amount;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getAmount(){
        return this.amount;
    }
    public void setAmount(int amount){ this.amount = amount;}

}
