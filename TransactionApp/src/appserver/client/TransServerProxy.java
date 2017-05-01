package appserver.client;

import appserver.comm.Message;


/**
 * Created by cdubs on 4/27/17.
 */
public class TransServerProxy {

    private String host;
    private int port;

    // interface to the server for the client
    public TransServerProxy(String host, int port){
        this.host = host;
        this.port = port;
    }

    public int openTrans(){
        int id = 0;
        return id;
    }
    public int closeTrans(){
        boolean flag = false;
        return flag;

    }
    public int read(int accountID){
        return 0;
    }
    public int write (int accountID, int amount){
        return 0;
    }

}
