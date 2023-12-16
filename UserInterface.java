import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UserInterface {


    static void creerPage(JFrame page) {
        //JFrame pagePrincipale = new JFrame();//Créé la page
        page.setVisible(true);//Assure la visibilité de la page
        page.setLayout(null);//Permet de gérer manuellement tout les composants de la page
        page.setSize(1000, 720);//Taille de la page
        page.setTitle("Le jeu des couleurs");//Titre de la page
        page.setResizable(false);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Assure que lorsqu'on clique sur la croix, la page se ferme et ne consomme plus de RAM
    }

    static void pageAccueil(JFrame page){
        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.setBounds(454,310,120,80);
        JLabel textJouer = new JLabel("Lancer le jeu");
        JTextArea nomJoueur = new JTextArea("joueur");

        nomJoueur.setBounds(450,150,100,30);
        textJouer.setBounds(470,225,75,50);

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


        JLabel nomAdversaire = new JLabel("Adversaire");
        nomAdversaire.setHorizontalAlignment(SwingConstants.CENTER);
        nomAdversaire.setBounds(450,10,100,30);

        JLabel nomJoueur = new JLabel(nomJoueurString);
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setBounds(450,650,100,30);



        ImageIcon carteActuelleImage = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(enCours.actuel),"carteActuelle");
        JLabel carteActuelle = new JLabel();

        carteActuelle.setBounds(460,280,80,125);
        carteActuelleImage.setImage(carteActuelleImage.getImage().getScaledInstance((carteActuelle.getWidth()),carteActuelle.getHeight(),Image.SCALE_SMOOTH));
        carteActuelle.setIcon(carteActuelleImage);

        //carteActuelle.setText(enCours.actuel.toString());

        JLabel carteAdversaire1 = new JLabel();
        JLabel carteAdversaire2 = new JLabel();
        JLabel carteAdversaire3 = new JLabel();
        carteAdversaire1.setBounds(350,50,80,125);
        carteAdversaire2.setBounds(450,50,80,125);
        carteAdversaire3.setBounds(550,50,80,125);
        carteAdversaire1.setOpaque(true);
        carteAdversaire2.setOpaque(true);
        carteAdversaire3.setOpaque(true);
        carteAdversaire1.setBackground(Color.GREEN);
        carteAdversaire2.setBackground(Color.BLUE);
        carteAdversaire3.setBackground(Color.PINK);
        carteAdversaire1.setText(enCours.Mains.get(1).get(0).toString());
        carteAdversaire2.setText(enCours.Mains.get(1).get(1).toString());
        carteAdversaire3.setText(enCours.Mains.get(1).get(2).toString());

        JButton carteJoueur1 = new JButton();
        JButton carteJoueur2 = new JButton();
        JButton carteJoueur3 = new JButton();

        carteJoueur1.setBounds(350,520,80,125);
        carteJoueur2.setBounds(450,520,80,125);
        carteJoueur3.setBounds(550,520,80,125);

        ImageIcon carteJoueur1Image = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(enCours.Mains.get(0).get(0)),"carte 1 joueur1");
        carteJoueur1Image.setImage(carteJoueur1Image.getImage().getScaledInstance((carteJoueur1.getWidth()),carteJoueur1.getHeight(),Image.SCALE_SMOOTH));
        ImageIcon carteJoueur2Image = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(enCours.Mains.get(0).get(1)),"carte 2 joueur1");
        carteJoueur2Image.setImage(carteJoueur2Image.getImage().getScaledInstance((carteJoueur2.getWidth()),carteJoueur2.getHeight(),Image.SCALE_SMOOTH));
        ImageIcon carteJoueur3Image = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(enCours.Mains.get(0).get(2)),"carte 3 joueur1");
        carteJoueur3Image.setImage(carteJoueur3Image.getImage().getScaledInstance((carteJoueur3.getWidth()),carteJoueur3.getHeight(),Image.SCALE_SMOOTH));

        carteJoueur1.setIcon(carteJoueur1Image);
        carteJoueur2.setIcon(carteJoueur2Image);
        carteJoueur3.setIcon(carteJoueur3Image);
        //carteJoueur1.setText(enCours.Mains.get(0).get(0).toString());
        //carteJoueur1.setBackground(Color.ORANGE);

        //carteJoueur2.setText(enCours.Mains.get(0).get(1).toString());
        //carteJoueur2.setBackground(Color.RED);

        //carteJoueur3.setText(enCours.Mains.get(0).get(2).toString());
        //carteJoueur3.setBackground(Color.PINK);

        JButton piocheJoueur = new JButton();
        piocheJoueur.setBounds(650,520,80,60);
        piocheJoueur.setText(enCours.Decks.get(0).get(1).toString());

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

        piocheJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enCours.piocher();
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                jeu1(page, nomJoueurString,enCours);
            }
        });

        System.out.println("tour du joueur: "+enCours.tour);

        if (enCours.tour == 1){
            System.out.println("Tour ordinateur");
            enCours.tour_ordinateur();
            page.getContentPane().removeAll();
            page.revalidate();
            page.repaint();
            jeu1(page,nomJoueurString,enCours);
        }



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

        page.add(carteAdversaire1);
        page.add(carteAdversaire2);
        page.add(carteAdversaire3);

        page.add(carteJoueur1);
        page.add(carteJoueur2);
        page.add(carteJoueur3);

        page.add(piocheJoueur);
    }






}
