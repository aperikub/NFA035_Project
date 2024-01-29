package fr.cnam.TPSwing;


import javax.swing.*;
import java.time.LocalDate;

public class Client extends JFrame {
    public static LivreDAO library = DAOFactory.getDAO();


    public static void enregistrerLivre(String _isbn, String _titre, Livre.TCategorie _categorie, float _prix, int _quantiteDisponible, LocalDate _dateParution){
        Livre livre = new Livre(_isbn,_titre,_categorie,_prix,_quantiteDisponible,_dateParution);
        if (library.ajouterLivre(livre)){
            Log.getInstance().addInfoLog("livre enregistré : " + livre);
        }
        else{
            Log.getInstance().addErrorLog("erreur lors de l'ajout du livre : "+livre);
        }

    }

    public static void restituerLivre(String isbn){
        try{
            library.getLivre(isbn).restituer();
            Log.getInstance().addInfoLog("livre restitué : " + library.getLivre(isbn));
        }catch (DAOException e){
            String er = "erreur lors de la restitution, l'isbn saisi ne correspond à aucun livre.";
            System.err.println(er);
            Log.getInstance().addErrorLog(er);
        }
    }
    public static void ajouterLivre(String isbn, int nbr){

        if (library.modifierQtLivre(isbn,nbr)){
            try {
                Log.getInstance().addInfoLog("ajouté : " + nbr+" exemplaires de " + library.getLivre(isbn));
            } catch (DAOException e) {
                Log.getInstance().addErrorLog("ajout d'un exemplaire du livre "+ isbn +" :" + e.getMessage());

                e.printStackTrace();
            }
        }
        else{
            String er = "erreur lors de l'ajout";
            System.err.println(er);
            Log.getInstance().addErrorLog(er);
        }


    }

    public static void emprunterLivre(String isbn){

        try {
            if(library.getLivre(isbn).emprunter()){
                Log.getInstance().addInfoLog("livre emprunté : " + library.getLivre(isbn));
                if (library.getLivre(isbn).getQuantiteDisponible() == 0){
                    Log.getInstance().addWarningLog("plus de stock pour " + library.getLivre(isbn));
                }
            } else {
            Log.getInstance().addErrorLog("emprunt impossible "+ library.getLivre(isbn));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    public static void supprimerLivre(Livre livre){
        try {
            library.supprimerLivre(livre);
        } catch (DAOException e) {
            Log.getInstance().addErrorLog("suppression du livre "+ livre +" :" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Livre supprimé : " + livre);
        Log.getInstance().addInfoLog("Livre supprimé : " + livre);
    }


}
