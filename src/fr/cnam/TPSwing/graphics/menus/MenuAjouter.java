/*
 * Nom de classe : MenuAjouter
 *
 * Description   : Pilote le menu permettant d'ajouter des utilisateurs.
 *
 * Auteurs       : Steven Besnard, Agnes Laurencon, Olivier Baylac, Benjamin Launay.
 *
 * Version       : 1.0
 *
 * Date          : 09/01/2022
 *
 * Copyright     : CC-BY-SA
 */

package fr.cnam.TPSwing.graphics.menus;



import fr.cnam.TPSwing.Client;
import fr.cnam.TPSwing.Livre;

import javax.swing.*;
import java.time.LocalDate;

public class MenuAjouter{
    private JPanel menuAjouterPanel;
    private JLabel menuAjouterTitle;
    private JTextField titreField;
    private JButton validerButton;
    private JLabel titreLabel;
    private JTextField prixField;
    private JLabel prixLabel;
    private JTextField dateParutionField;
    private JLabel dateLabel;
    private JComboBox categorieBox;

    private JTextField isbnField;
    private JLabel isbnLabel;

    private JTextField quantityField;
    private JLabel quantityLabel;
    private JComboBox dayComboBox;
    private JComboBox monthComboBox;
    private JComboBox yearComboBox;


    public MenuAjouter() {

        /*
         * listener du choix du type d'utilisateur ajouté
         */


        /*
         * listener du bouton valider
         */
        validerButton.addActionListener(e -> {
            try {
                isbnField.setText(isbnField.getText().toLowerCase());
                String isbn = isbnField.getText();
                String titre = titreField.getText();
                float prix = Float.parseFloat(prixField.getText());
                LocalDate date = LocalDate.of(Integer.parseInt(yearComboBox.getSelectedItem().toString()),Integer.parseInt(monthComboBox.getSelectedItem().toString()),Integer.parseInt(dayComboBox.getSelectedItem().toString()));
                System.out.println("tCategorie to string : "+categorieBox.getSelectedItem().toString());
                Livre.TCategorie categorie = Livre.TCategorie.valueOf(categorieBox.getSelectedItem().toString());
                int quantity = Integer.parseInt(quantityField.getText().toString());

                Client.library.ajouterLivre(new Livre(isbn,titre,categorie,prix,quantity,date));



            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuAjouterPanel, ex.getMessage(), "erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * place des indices dans les zones de saisie
     */




    public JPanel getMenuAjouterPanel() {
        return menuAjouterPanel;
    }
}
