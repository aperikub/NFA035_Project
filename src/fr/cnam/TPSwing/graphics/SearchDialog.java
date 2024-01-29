/*
 * Nom de classe : SearchDialog
 *
 * Description   : boite de dialogue permettant la selection du type de recherche
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchDialog extends JDialog {
    private JPanel contentPane;
    private JButton searchByTitleButton;
    private JButton searchByISBNButton;
    public static final String TITRE_SEARCH_COMMAND = "titreSearch";
    public static final String ISBN_SEARCH_COMMAND = "ISBNSearch";
    public static final String CATEGORY_SEARCH_COMMAND = "categorieSearch";
    public static final int TITRE_SEARCH_ID = -300;
    public static final int ISBN_SEARCH_ID = -290;
    public static final int CATEGORY_SEARCH_ID = -280;
    private JButton searchByCategoryButton;
    private JButton searchByTypeButton;
    private ActionListener listener;


    public SearchDialog(ActionListener _listener, Component owner) {
        listener = _listener;
        this.setTitle("type de recherche");
        this.setSize(400, 400);
        this.setLocationRelativeTo(owner);


//        getRootPane().setDefaultButton(searchByISBNButton);
        setContentPane(contentPane);
        setModal(true);

        /*
        * ces listeners modifient l'affichage des zones de saisies selon le type de recherche
        */
        searchByTitleButton.addActionListener(e -> {
            listener.actionPerformed(new ActionEvent(searchByTitleButton, TITRE_SEARCH_ID, TITRE_SEARCH_COMMAND));
            dispose();
        });
        searchByISBNButton.addActionListener(e ->{
            listener.actionPerformed(new ActionEvent(searchByISBNButton, ISBN_SEARCH_ID, ISBN_SEARCH_COMMAND));
            dispose();
        });
        searchByCategoryButton.addActionListener(e ->{
            listener.actionPerformed(new ActionEvent(searchByCategoryButton, CATEGORY_SEARCH_ID, CATEGORY_SEARCH_COMMAND));
            dispose();
        });



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void onCancel() {
        listener.actionPerformed(new ActionEvent(searchByTitleButton, TITRE_SEARCH_ID, TITRE_SEARCH_COMMAND)); //en l'absence de choix, la recherche par nom est définie par défaut
        dispose();
    }



    @Override
    public JPanel getContentPane() {
        return contentPane;
    }


}
