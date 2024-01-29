package fr.cnam.TPSwing;

public class DAOException extends Exception{
    public DAOException(String message){
        super(message);
        Log.getInstance().addErrorLog(message);


    }
}
