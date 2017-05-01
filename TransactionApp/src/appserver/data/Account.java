package appserver.data;

/**
 * Class [Account] : Instances of this class are used to create account objects
 *
 * Author: Christopher D. Whitney on April 29th, 2017
 *
 */
public class Account {

    private int id;
    private int amount;

    public Account(){}
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
