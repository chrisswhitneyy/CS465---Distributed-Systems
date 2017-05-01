/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.data;

import java.util.ArrayList;

/** Class [DataManager] : Class that is in charge of handling the accounts.
 *
 * @author Christopher D. Whitney
 */
public class DataManager {

    static ArrayList accounts = null;


    public DataManager() {
        accounts = new ArrayList<Integer>();

        // initialize 10 accounts to $10;
        for (int i = 0; i<=10; i++){
            accounts.set(i, 10);
        }

    }

    public Integer read(int id){
        return (Integer) accounts.get(id-1);
    }

    public void write(int id, Integer amount){

    }



}
