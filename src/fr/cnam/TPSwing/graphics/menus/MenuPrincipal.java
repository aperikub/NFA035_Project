/*
 * Nom de classe : MenuPrincipal
 *
 * Description   : pilote le menu principal
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



import fr.cnam.TPSwing.graphics.MenuBar;
import fr.cnam.TPSwing.graphics.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal implements ActionListener { // implémente ActionListener pour être à l'écoute de certains évènements
    private JPanel menuPrincipalPanel;
    private JButton consulterButton;
    private JTextArea title;
    private JButton modifierButton;
    private JButton ajouterButton;
    private JButton quitterButton;
    private JButton connecterButton;
    private JLabel statutTextField;

    MenuBar menuBar;
    MyWindow myWindow;

    public MenuPrincipal(MyWindow myWindow) {
        this.myWindow = myWindow;

    }

    /*
     * initialisation du menu principal*/
    public void init() {

        menuBar = new MenuBar();
        menuBar.getReturnToMain().setVisible(false);
        myWindow.setJMenuBar(menuBar);
        myWindow.setVisible(true);
        myWindow.pack();
        myWindow.setVisible(true);
        myWindow.pack();
        myWindow.setContentPane(menuPrincipalPanel);
        myWindow.setMinimumSize(new Dimension(600, 600));



//        modifierButton.addActionListener(e -> {
//            try {
//
//                menuBar.getReturnToMain().setVisible(true);
//                MenuModifier menuModifier = new MenuModifier(this);
//                myWindow.setContentPane(menuModifier.getModifierPane());
//
//            } catch (Exception err) {
//                JOptionPane.showMessageDialog(myWindow, err.getMessage());
//            }
//        });

        consulterButton.addActionListener(e -> {
            try {
                menuBar.getReturnToMain().setVisible(true);
                MenuConsulter menuConsulter = new MenuConsulter();
                myWindow.setContentPane(menuConsulter.getConsultPane()); // setContent Pane applique un contenu à la fenetre
            } catch (Exception err) {
                JOptionPane.showMessageDialog(myWindow, err.getMessage());
            }
        });


        ajouterButton.addActionListener(e -> {
            try {

                menuBar.getReturnToMain().setVisible(true);
                MenuAjouter menuAjouter = new MenuAjouter();
                myWindow.setContentPane(menuAjouter.getMenuAjouterPanel());

            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err.toString());
            }
        });//fin du listener menu ajouter

        quitterButton.addActionListener(e -> System.exit(0));
        menuBar.getQuit().addActionListener(e -> System.exit(0));
        menuBar.getReturnToMain().addActionListener(this);

    }





    public JButton getConnecterButton() {
        return connecterButton;
    }


    public JPanel getMenuPrincipalPanel() {
        return menuPrincipalPanel;
    }

    public JButton getConsulterButton() {
        return consulterButton;
    }

    public JTextArea getTitle() {
        return title;
    }

    public JButton getModifierButton() {
        return modifierButton;
    }

    public JButton getAjouterButton() {
        return ajouterButton;
    }

    public JButton getQuitterButton() {
        return quitterButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuBar.getReturnToMain()){ // capture de l'event retourner au menu principal de la barre des taches

            myWindow.setContentPane(menuPrincipalPanel);
            menuBar.getReturnToMain().setVisible(false);
        }
    }


}
