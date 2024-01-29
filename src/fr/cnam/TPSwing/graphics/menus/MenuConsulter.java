/*
 * Nom de classe : MenuConsulter
 *
 * Description   : pilote le menu permettant de consulter l'annuaire
 *
 * Auteurs       : Steven Besnard, Agnes Laurencon, Olivier Baylac, Benjamin Launay
 *
 * Version       : 1.0
 *
 * Date          : 09/01/2022
 *
 * Copyright     : CC-BY-SA
 */

package fr.cnam.TPSwing.graphics.menus;


import fr.cnam.TPSwing.Client;
import fr.cnam.TPSwing.DataService;
import fr.cnam.TPSwing.Livre;
import fr.cnam.TPSwing.graphics.ResultsTableModel;
import fr.cnam.TPSwing.graphics.SearchDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuConsulter  {

    private JPanel consultPane;
    private JPanel userSearchPanel;
    private JTextField TitreField;
    private JTextField ISBNField;

    private JTable resultsTable;
    private JButton validerButton;
    private JCheckBox selectAllBox;
    private JLabel TitreLabel;
    private JLabel ISBNLabel;
    private JLabel categorieLabel;
    private JComboBox categorieBox;
    private JButton searchByButton;

    private Livre[] livreSearchResult;

    private enum SearchType{isbn, titre, categorie, noSearch}
    private SearchType searchType;
    private MenuPrincipal menuPrincipal;

    public MenuConsulter() {
        /*
         * vérifie le statut de l'utilisateur (administrateur ou particulier)
         */
        searchType = SearchType.titre;
        manageSearchPanel();

        searchByButton.addActionListener(listener ->{
            modifySearchType();
        });

        /*
         * listener sur la checkbox permettant d'afficher l'intégralité des utilisateurs
         */
        selectAllBox.addActionListener((listener) ->{
            if (selectAllBox.isSelected()){
                searchType = SearchType.noSearch;

                validerButton.setVisible(false);
                validerButton.doClick();
            }
            else{
                searchType = SearchType.titre;


                try {
                    resultsTable.setModel(new ResultsTableModel(null));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                manageSearchPanel();
            }
        });




        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selectAllBox.isSelected()){
                        livreSearchResult = new Livre[DataService.getLivres().size()];
                        livreSearchResult = DataService.getLivres().values().toArray(livreSearchResult);
                            ResultsTableModel resultsTableModel = new ResultsTableModel(livreSearchResult);
                            resultsTable.setModel(resultsTableModel);

                        resultsTable.setVisible(true);
                        consultPane.updateUI();
                    }
                    else {
                        boolean isbn = false;
                        boolean titre = false;
                        boolean categorie= false;
                        switch (searchType){
                            case isbn -> livreSearchResult = new Livre[]{Client.library.getLivre(ISBNField.getText())};
                            case titre -> livreSearchResult = Client.library.getLivreParTitre(TitreField.getText()).toArray(new Livre[DataService.getLivres().size()]);
                            case categorie -> livreSearchResult = Client.library.getLivreParCategorie(Livre.TCategorie.valueOf(categorieBox.getSelectedItem().toString())).toArray(new Livre[DataService.getLivres().size()]);
                        }
//
                        if (livreSearchResult != null) {
                            ResultsTableModel resultsTableModel = new ResultsTableModel(livreSearchResult);
                            resultsTable.setModel(resultsTableModel);
                            resultsTable.setVisible(true);
                            consultPane.updateUI();
                        } else {
                            throw new Exception("aucun résultat");
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(consultPane, ex.getMessage(), "erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public void manageSearchPanel(){
        switch (searchType){
            case isbn -> {
                setAllFields(false);
                ISBNField.setVisible(true);
                ISBNLabel.setVisible(true);
                userSearchPanel.setVisible(true);
            }
            case titre -> {
                setAllFields(false);
                TitreField.setVisible(true);
                TitreLabel.setVisible(true);
                userSearchPanel.setVisible(true);
            }
            case categorie -> {
                setAllFields(false);
                categorieBox.setVisible(true);
                categorieLabel.setVisible(true);
                userSearchPanel.setVisible(true);
            }

            case noSearch -> {
                userSearchPanel.setVisible(false);
            }
        }
    }


    /*
     * affiche la boite de dialogue de choix du type de recherche et gère le choix de l'utilisateur
     */
    public void modifySearchType(){
        resultsTable.setModel(new ResultsTableModel(null));
        SearchDialog dialog = new SearchDialog(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                switch (e.getID()){
                    case SearchDialog.TITRE_SEARCH_ID -> {
                        searchType = SearchType.titre;
                    }
                    case SearchDialog.ISBN_SEARCH_ID -> {
                       searchType = SearchType.isbn;
                    }
                    case SearchDialog.CATEGORY_SEARCH_ID -> {
                        searchType = SearchType.categorie;
                    }

                }
                manageSearchPanel();
            }
        },consultPane);
        dialog.setLocationRelativeTo(consultPane);
        dialog.pack();
        dialog.setVisible(true);
    }
    /*
     * cache les JTextfield et leur JLabel concernant les particulier
     */
    public void setAllFields(boolean b){
        TitreField.setVisible(b);
        TitreLabel.setVisible(b);
        ISBNField.setVisible(b);
        ISBNLabel.setVisible(b);
        categorieBox.setVisible(b);
        categorieLabel.setVisible(b);


    }
    /*
     * affiche uniquement les zones de saisies concernant la recherche par nom
     */

    public JPanel getConsultPane() {
        return consultPane;
    }

    public JButton getValiderButton() {
        return validerButton;
    }
}
