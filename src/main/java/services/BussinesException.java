package services;

@SuppressWarnings("serial")
public class BussinesException extends RuntimeException {
    public BussinesException(String msg) { super(msg); }
    public BussinesException(String msg, Throwable cause) { super(msg, cause); }
}
