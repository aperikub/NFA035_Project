package fr.cnam.TPSwing;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Livre implements Serializable {


    @Serial
    private static final long serialVersionUID = -7263536820157625142L;
    private final String isbn;
    private final String titre;
    public enum TCategorie {
        fiction,
        documentaire,
        bande_dessinee,
        technique,
        biographique,
        jeunesse;

    }
    private final TCategorie categorie;
    private float prix;
    private int quantiteDisponible;
    private final LocalDate dateParution;

    public Livre(String _isbn, String _titre, TCategorie _categorie, float _prix, int _quantiteDisponible, LocalDate _dateParution){
        isbn = _isbn;
        titre = _titre;
        categorie = _categorie;
        prix = _prix;
        quantiteDisponible = _quantiteDisponible;
        dateParution = _dateParution;
    }


    public void ajouter(int nbr){
        if (nbr < 0){
            throw  new IllegalArgumentException("La quantité disponible ne peut être inférieure à 0");
        }
        quantiteDisponible = nbr;
    }
    public boolean emprunter(){
        if (quantiteDisponible == 0){
            return false;
        }
        else{
            quantiteDisponible--;
            return true;
        }
    }
    public long duree(){
        return ChronoUnit.DAYS.between(dateParution,LocalDate.now());
    }

    public void restituer(){
        quantiteDisponible++;
    }

    public String getIsbn(){
        return isbn;
    }

    public String getTitre(){
        return titre;
    }

    public TCategorie getCategorie(){
        return categorie;
    }

    public float getPrix(){
        return prix;
    }

    public void setPrix(float _prix){
        prix = _prix;
    }

    public int getQuantiteDisponible(){
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    public LocalDate getDateParution(){
        return dateParution;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Livre)){
            return false;
        }
        else{
            return isbn.equals(((Livre) obj).getIsbn());
        }
    }

    @Override
    public String toString() {
        return String.format("%s, isbn : %s, %s, %s",titre,isbn,categorie,dateParution.toString());
    }
}
