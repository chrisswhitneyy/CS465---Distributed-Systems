package appserver.server;

import javax.sql.rowset.spi.TransactionalWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class TransManager {

    private static int transCounter;
    private static ArrayList<Trans> transactions;

    TransManager(){
        transCounter = 0;
        transactions = new ArrayList();
    }

    public void runTrans(Socket client){

    }

    private class TransManagerThread extends Thread  {

    }


}
