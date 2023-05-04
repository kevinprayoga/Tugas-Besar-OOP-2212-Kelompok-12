package exceptions;

public class ExistingOrder extends Exception {
    
    public ExistingOrder(String str) {
        super(str);
    }
}