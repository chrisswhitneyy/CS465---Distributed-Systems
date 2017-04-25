/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.data;

import java.util.ArrayList;

/** Class [DataManager] : Class that is incharge of handling the accounts.
 *
 * @author Christopher D. Whitney
 */
public class DataManager {

    static ArrayList accounts = null;

    public DataManager() {
        accounts = new ArrayList<Integer>();
    }

    public Integer read(int id){

        return (Integer) accounts.get(id-1);
    }

    public boolean write(int id, Integer amount){

    }



}
