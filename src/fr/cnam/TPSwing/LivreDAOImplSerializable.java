package fr.cnam.TPSwing;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class LivreDAOImplSerializable implements LivreDAO, Serializable {


    public static final String DATA_SEPARATOR = "||";
    private static final String FILE_PATH_PROPERTY_NAME = "File_Path";
    @Serial
    private static final long serialVersionUID = 8983124610364431455L;

    private String serialFilePath;
    private Properties props;

    public LivreDAOImplSerializable(){
        props = new Properties();
        InputStream is = LivreDAOImplSerializable.class.getResourceAsStream("props.properties");
        try {
            props.load(is);
            is.close();
            serialFilePath = props.getProperty(FILE_PATH_PROPERTY_NAME);
            System.out.println("LivreDAoImplFile : poperties value : " + serialFilePath);
            DataService.getLivres().putAll(deserialize());
            System.out.println("deserialized books :");
            for (Livre l : DataService.getLivres().values()){
                System.out.println(l.getTitre());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public HashMap<String, Livre> deserialize() throws IOException {
        File libraryFile = new File(serialFilePath);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(libraryFile));
        HashMap<String,Livre> livres = null;
        if (libraryFile.exists()){
            try {
                livres = (HashMap<String, Livre>) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return livres;

    }
    @Override
    public String getLivres() {
        return null;
    }

    @Override
    public Livre getLivre(String isbn) throws DAOException {
        if (!DataService.getLivres().containsKey(isbn)) {
            throw new DAOException("No such Book in memory");
        }

        return DataService.getLivres().get(isbn);
    }

    @Override
    public List<Livre> getLivreParCategorie(Livre.TCategorie categorie) throws DAOException {
        List<Livre> livres = DataService.getLivres().values().stream().filter(livre -> livre.getCategorie().equals(categorie)).toList();

        if ( livres.size() == 0 ){
            throw new DAOException("no books in this category");
        }
        return livres;
    }

    @Override
    public List<Livre> getLivreParTitre(String titre) throws DAOException {
        List<Livre> livres = DataService.getLivres().values().stream().filter(l -> l.getTitre().contains(titre)).toList();

        if ( livres.size() == 0 ){
            throw new DAOException("no such book");
        }
        return livres;

    }

    @Override
    public boolean ajouterLivre(Livre l) {
        boolean b = DataService.getLivres().putIfAbsent(l.getIsbn(),l) == null;
        if (b){
            serialize();
        }
        return b;


    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize called");
        serialize();

    }
    public void serialize(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serialFilePath));
            oos.writeObject(DataService.getLivres());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean modifierLivre(Livre livre) {
        if (DataService.getLivres().containsKey(livre.getIsbn())){
            DataService.getLivres().replace(livre.getIsbn(),livre);
            serialize();
            return true;
        }
        else{ return false;}
    }


    @Override
    public boolean modifierQtLivre(String isbn, int quantite) {
        if (quantite >= 0 && DataService.getLivres().containsKey(isbn)){
            DataService.getLivres().get(isbn).setQuantiteDisponible(quantite);
            serialize();
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public void supprimerLivre(Livre livre) throws DAOException {
        if(DataService.getLivres().containsValue(livre) ){
            DataService.getLivres().remove(livre.getIsbn());
            serialize();
        }
        else{
            throw new DAOException("no such book in system");
        }


    }
}
