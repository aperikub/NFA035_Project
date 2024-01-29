package fr.cnam.TPSwing;

import java.util.List;

public interface LivreDAO {

    public String getLivres();
    public Livre getLivre(String isbn) throws DAOException;
    public List<Livre> getLivreParCategorie(Livre.TCategorie categorie) throws DAOException;
    public List<Livre> getLivreParTitre(String titre) throws DAOException;
    public boolean ajouterLivre(Livre l);
    public boolean modifierLivre(Livre livre);
    public boolean modifierQtLivre(String isbn, int quantite);
    public void supprimerLivre(Livre livre) throws DAOException;

}
