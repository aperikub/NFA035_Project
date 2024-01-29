/*
 * Nom de classe : ResultsTableModel
 *
 * Description   : contient le modèle à appliquer à la JTable permettant d'afficher les utilisateurs
 *                 (inspirée du code permettant d'afficher des résultats de requêtes MySQL proposée par Dominique Liard sur koor.fr)
 *
 * Auteurs       : Steven Besnard, Agnes Laurencon, Olivier Baylac, Benjamin Launay
 *
 * Version       : 1.0
 *
 * Date          : 09/01/2022
 *
 * Copyright     : CC-BY-SA
 */

package fr.cnam.TPSwing.graphics;




import fr.cnam.TPSwing.Livre;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;


public class ResultsTableModel extends AbstractTableModel {
    private final ArrayList<String> columnsNames = new ArrayList<>();
    private final ArrayList<String> columnsTypes = new ArrayList<>();
    private final ArrayList< ArrayList<String> > values = new ArrayList<>();

    public <T> ResultsTableModel(T[] resultArray ) throws IllegalArgumentException {

        if (resultArray == null){ // vide le JTable

        }
        else {
            if (resultArray instanceof Livre[]) {
                columnsNames.add("Isbn");
                columnsNames.add("Titre");
                columnsNames.add("Catégorie");
                columnsNames.add("prix");
                columnsNames.add("Quantité");
                columnsNames.add("date de parution");
                columnsTypes.add(String.class.getName());
                columnsTypes.add(String.class.getName());
                columnsTypes.add(String.class.getName());
                columnsTypes.add(String.class.getName());
                columnsTypes.add(String.class.getName());
                columnsTypes.add(String.class.getName());

                System.out.println("lesngth of resultArray = " + resultArray.length);
                Livre[] partArray = (Livre[]) Arrays.copyOf(resultArray,resultArray.length);

                for (Livre p : partArray) {
                    if (p != null) {
                        ArrayList<String> line = new ArrayList<>();
                        line.add(p.getIsbn());
                        line.add(p.getTitre());
                        line.add(p.getCategorie().name());
                        line.add(String.valueOf(p.getPrix()));
                        line.add(String.valueOf(p.getQuantiteDisponible()));
                        line.add(p.getDateParution().toString());

                        values.add(line);
                    }
                }
            }
            else{
                throw new IllegalArgumentException("ResultsTableModel only takes  Livres Classes");
            }
        }

    }

    @Override public Class<?> getColumnClass( int column ) {
        String type = this.columnsTypes.get( column );
        try {
            return Class.forName( type );
        } catch( Exception e ) {
            return String.class;
        }
    }


    @Override public String getColumnName(int i) {
        return columnsNames.get( i );
    }

    @Override public int getColumnCount() {
        return columnsNames.size();
    }

    @Override public int getRowCount() {
        return values.size();
    }

    @Override public Object getValueAt( int line, int column ) {
        return values.get( line ).get( column );
    }

}
