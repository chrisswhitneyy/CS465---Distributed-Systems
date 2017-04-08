package appserver.job.impl;

/**
 * Class [FibAux] Helper class for Fib to demonstrate that dependent classes are loaded automatically
 * when a class is loaded (in this case Fib). Though this class could be called solo, it's still cool 
 * seeing the dependent classes load automatically. 
 * 
 * @author Christopher D. Whitney 
 */
public class FibAux {
    
    Integer number = null;
    
    public FibAux(Integer number) {
        this.number = number;
    }
    
    public Integer getResult() {
        return fib(number);
    }
    
    /** Fib: Recursively calculates the fibonacci number of a passed integer */
    public Integer fib(Integer number){
        if(number == 0){
            return 0;
        }
        else if(number == 1){
            return 1;
        }
        return fib(number - 1) + fib(number - 2);
    }
}
