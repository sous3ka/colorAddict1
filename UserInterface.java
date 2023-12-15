import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface {

    static void creerPage(JFrame page) {
        //JFrame pagePrincipale = new JFrame();//Créé la page
        page.setVisible(true);//Assure la visibilité de la page
        page.setLayout(null);//Permet de gérer manuellement tout les composants de la page
        page.setSize(1000, 700);//Taille de la page
        page.setTitle("Le jeu des couleurs");//Titre de la page
        //page.setResizable(false);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Assure que lorsqu'on clique sur la croix, la page se ferme et ne consomme plus de RAM
    }

    static void pageAccueil(JFrame page){
        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.setBounds(350,275,100,50);
        JLabel textJouer = new JLabel("Lancer le jeu");
        JTextArea nomJoueur = new JTextArea("joueur");

        nomJoueur.setBounds(350,100,100,30);
        textJouer.setBounds(362,225,75,50);

        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Partie enCours = new Partie(2,1);
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                jeu1(page, nomJoueur.getText(),enCours);
            }
        });
        page.add(boutonJouer);
        page.add(textJouer);
        page.add(nomJoueur);
        page.repaint();
    }

    static void jeu1(JFrame page, String nomJoueurString, Partie enCours){
        //faire les tours de jeu + les images cartes
        //1ere idée : faire des zones pour les cartes +
        // au dessus des boutons pour les jouer si la carte est compatible

        //JPanel panelJoueur = new JPanel();
        //JPanel panelAdversaire = new JPanel();

        JLabel nomAdversaire = new JLabel("Adversaire");
        nomAdversaire.setHorizontalAlignment(SwingConstants.CENTER);
        nomAdversaire.setBounds(350,20,100,30);

        JLabel nomJoueur = new JLabel(nomJoueurString);
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setBounds(350,500,100,30);

        //panelJoueur.add(nomJoueur);
        //panelAdversaire.add(nomAdversaire);
        //panelAdversaire.setBounds(100,50,300,250);Mettre les éléments dans des JPanel afin de mieux les séparer et les distinguer mais je n'y arrive aps.

        JLabel carteActuelle = new JLabel();
        carteActuelle.setBounds(365,200,200,100);
        carteActuelle.setOpaque(true);
        carteActuelle.setBackground(Color.BLUE);
        carteActuelle.setText(enCours.actuel.toString());

        JLabel carteAdversaire = new JLabel();
        carteAdversaire.setBounds(150,100,200,100);
        carteAdversaire.setOpaque(true);
        carteAdversaire.setBackground(Color.GREEN);
        carteAdversaire.setText(enCours.Mains.get(enCours.tour).get(0).toString());

        JButton carteJoueur1 = new JButton("blablabla");
        JButton carteJoueur2 = new JButton("blablabla");
        JButton carteJoueur3 = new JButton("blablabla");

        carteJoueur1.setBounds(100,350,200,100);
        carteJoueur2.setBounds(310,350,200,100);
        carteJoueur3.setBounds(420,350,200,100);

        carteJoueur1.setText(enCours.Mains.get(enCours.tour).get(0).toString());
        carteJoueur1.setBackground(Color.ORANGE);

        carteJoueur2.setText(enCours.Mains.get(enCours.tour).get(1).toString());
        carteJoueur2.setBackground(Color.RED);

        carteJoueur3.setText(enCours.Mains.get(enCours.tour).get(2).toString());
        carteJoueur3.setBackground(Color.PINK);

        carteJoueur1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enCours.jouer(0);
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                jeu1(page, nomJoueurString,enCours);
            }
        });
        carteJoueur2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enCours.jouer(1);
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                jeu1(page, nomJoueurString,enCours);
            }
        });
        carteJoueur3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//permet de jouer la carte 3
                enCours.jouer(2);
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                jeu1(page, nomJoueurString,enCours);
            }
        });
        System.out.println("tour du joueur: "+enCours.tour);

/*        if (enCours.compare(enCours.actuel,enCours.Mains.get(enCours.tour).get(0))){
            carteJoueur1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }*/

        page.add(nomJoueur);

        page.add(nomAdversaire);

        page.add(carteActuelle);

        page.add(carteAdversaire);

        page.add(carteJoueur1);
        page.add(carteJoueur2);
        page.add(carteJoueur3);
    }
}