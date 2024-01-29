package fr.cnam.TPSwing;

import java.util.HashMap;

public class DataService {

    private DataService(){


    }
    private static class Holder{
        private static HashMap<String,Livre> livres = new HashMap<>();
    }

    public static HashMap<String,Livre> getLivres(){
        return Holder.livres;
    }
}
