package exeptions;

public class InsufficentFundException extends RuntimeException{
    public InsufficentFundException(String message) {
        super(message);
    }

}
