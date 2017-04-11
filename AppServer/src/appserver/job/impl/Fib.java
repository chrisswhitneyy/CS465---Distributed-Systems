package appserver.job.impl;

import appserver.job.Tool;

/**
 * Class [Fib] calculates the fibonacci number that implements the Tool interface
 * 
 * @author Christopher D. Whitney 
 */
public class Fib implements Tool{


    /** Fib: Recursively calculates the fibonacci number of a passed integer. 
     *  This is implemented as a class method so that memory isn't wasted by 
     *  repeatedly creating new instance of objects. 
     */
    static public Integer fib(Integer number){
        if(number == 0){
            return 0;
        }
        else if(number == 1){
            return 1;
        }
        return fib(number - 1) + fib(number - 2);
    }
    
    
    @Override
    public Object go(Object parameters) {
        return Fib.fib((Integer) parameters);
    }
}
