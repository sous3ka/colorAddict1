import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class UserInterface {
    static void creerPage(JFrame page) {
        page.setVisible(true);//Assure la visibilité de la page
        page.setLayout(null);//Permet de gérer manuellement tout les composants de la page
        page.setSize(1000, 720);//Taille de la page
        page.setTitle("Le jeu des couleurs");//Titre de la page
        page.setResizable(false);//rend impossible le redimentionnement de la page
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Assure que lorsqu'on clique sur la croix, la page se ferme et ne consomme plus de RAM
    }

    static void pageAccueil(JFrame page){
        final JTextArea[][] nomsJoueurs = new JTextArea[1][];//variables stockant les valeurs des noms
        final JCheckBox[][] estUnJoueur = new JCheckBox[1][];//et du type des joueurs

        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.setBounds(440,310,120,80);
        boutonJouer.setEnabled(false);//rend le bouton cliquable après avoir choisit le nombre de joueurs

        JLabel cocheExpliquation = new JLabel("cochez les cases représentants les joueurs réels");
        cocheExpliquation.setBounds(350,280,300,30);
        cocheExpliquation.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(cocheExpliquation);

        JLabel selectionNombreJoueurs = new JLabel("Selectionnez le nombre de joueurs");
        selectionNombreJoueurs.setBounds(390,20,220,30);
        selectionNombreJoueurs.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(selectionNombreJoueurs);
        JComboBox<Integer> nombreJoueursComboBox = new JComboBox<>(new Integer[]{2, 3, 4});//permet de choisir
        nombreJoueursComboBox.setBounds(450, 50, 100, 30);//le nombre de joueurs, entre 2 et 4
        page.add(nombreJoueursComboBox);

        JPanel nombreJoueursPanel = new JPanel();
        nombreJoueursPanel.setBounds(400,100,200,200);
        nombreJoueursPanel.setLayout(null);

        nombreJoueursComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//créer autant de JTextArea que de joueurs, entre 2 et 4
                int nombreDeJoueurs = (int) nombreJoueursComboBox.getSelectedItem();
                nombreJoueursPanel.removeAll();
                nomsJoueurs[0] = new JTextArea[nombreDeJoueurs];
                estUnJoueur[0] = new JCheckBox[nombreDeJoueurs];
                for ( int i = 0 ; i < nombreDeJoueurs ; i++ ){
                    nomsJoueurs[0][i] = new JTextArea("joueur " + (i+1));
                    nomsJoueurs[0][i].setBounds(0,(i*40),100,30);
                    nombreJoueursPanel.add(nomsJoueurs[0][i]);

                    estUnJoueur[0][i] = new JCheckBox();
                    estUnJoueur[0][i].setBounds(110,(i*40),20,20);
                    nombreJoueursPanel.add(estUnJoueur[0][i]);
                }
                nombreJoueursPanel.revalidate();
                nombreJoueursPanel.repaint();
                boutonJouer.setEnabled(true);//rend le bouton pour lancer la partie cliquable
            }
        });
        page.add(nombreJoueursPanel);
        boutonJouer.addActionListener(new ActionListener() {//permet de lancer la partie
            @Override
            public void actionPerformed(ActionEvent e) {
                Partie enCours;
                do {//ce "do" permet de vérifier que la partie créee ne commence pas avec un Joker
                    enCours = new Partie((Integer) nombreJoueursComboBox.getSelectedItem());
                }while (enCours.actuel.couleur==null);
                for (int i =0;i<nomsJoueurs[0].length;i++){//mets les valeurs des JTextArea dans
                    enCours.nomJoueurs.add(nomsJoueurs[0][i].getText());//l'arrayList nomJoueurs
                    enCours.Humains[i] = estUnJoueur[0][i].isSelected();//définit si le joueur i est humain ou ordinateur
                }
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                jeu1(page,enCours);
            }
        });
        page.add(boutonJouer);
        page.repaint();
    }

    static void jeu1(JFrame page,Partie enCours){

        affichageCarteActuelle(page,enCours);

        switch (enCours.nb_joueurs){
            case 2:
                if (enCours.Humains[0])
                    joueurBasUI(page,enCours,0);
                else ordinateurBasUI(page,enCours,0);
                if (enCours.Humains[1])
                    joueurHautUI(page,enCours,1);
                else ordinateurHautUI(page,enCours,1);
                break;
            case 3:
                if (enCours.Humains[0])
                    joueurBasUI(page,enCours,0);
                else ordinateurBasUI(page,enCours,0);
                if (enCours.Humains[1])
                    joueurGaucheUI(page,enCours,1);
                else ordinateurGaucheUI(page,enCours,1);
                if (enCours.Humains[2])
                    joueurHautUI(page,enCours,2);
                else ordinateurHautUI(page,enCours,2);
                break;
            case 4:
                if (enCours.Humains[0])
                    joueurBasUI(page,enCours,0);
                else ordinateurBasUI(page,enCours,0);
                if (enCours.Humains[1])
                    joueurGaucheUI(page,enCours,1);
                else ordinateurGaucheUI(page,enCours,1);
                if (enCours.Humains[2])
                    joueurHautUI(page,enCours,2);
                else ordinateurHautUI(page,enCours,2);
                if (enCours.Humains[3])
                    joueurDroitUI(page,enCours,3);
                else ordinateurDroitUI(page,enCours,3);
                break;
        }

        if (!enCours.Humains[enCours.tour]){
            tourOrdinateurUI(page, enCours);
        }//tour de l'ordinateur

        for (int i=0;i<enCours.nb_joueurs;i++){
            if (enCours.Mains.get(i).isEmpty()){
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                finPartie(page,enCours.nomJoueurs.get(i));
            }
        }
    }

    static void finPartie(JFrame page, String gagnant){

        JLabel vainqueur = new JLabel();
        vainqueur.setText("Félicitation "+ gagnant +", tu as gagné la partie");
        vainqueur.setBounds(300,200,400,60);
        vainqueur.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(vainqueur);

        JLabel texteRetour = new JLabel("Retour au menu principal");
        texteRetour.setBounds(300,280,400,40);
        texteRetour.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(texteRetour);

        JButton retourMenu = new JButton("Menu");
        retourMenu.setBounds(450,330,100,50);
        retourMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                pageAccueil(page);
            }
        });
        page.add(retourMenu);
    }
    //ouvre un JDialog quand un joker est joué qui permet de choisir la nouvelle carte
    static void jokerPlayed(JFrame page, Partie partie){
        JDialog pageChoixCouleur = new JDialog(page,"ChoixJoker",true);
        pageChoixCouleur.setLocationRelativeTo(page);

        pageChoixCouleur.setSize(400,300);
        pageChoixCouleur.setResizable(false);
        pageChoixCouleur.setDefaultCloseOperation(0);

        JLabel question = new JLabel("quelles couleurs choisis tu?");
        question.setBounds(30,0,200,40);
        pageChoixCouleur.add(question);

        JComboBox<Couleur> choixEcrit = new JComboBox<>(Couleur.values());
        choixEcrit.setBounds(30,50,100,30);
        pageChoixCouleur.add(choixEcrit);

        JComboBox<Couleur> choixCouleur = new JComboBox<>(Couleur.values());
        choixCouleur.setBounds(150,50,100,30);
        pageChoixCouleur.add(choixCouleur);

        JButton valideChoix = new JButton("VALIDER");
        valideChoix.setBounds(30,150,100,30);
        pageChoixCouleur.add(valideChoix);

        valideChoix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                partie.actuel = new Carte((Couleur) choixCouleur.getSelectedItem(),(Couleur) choixEcrit.getSelectedItem());
                pageChoixCouleur.dispose();
            }
        });

        pageChoixCouleur.setLayout(null);
        pageChoixCouleur.setVisible(true);
    }

    //créer la carte sur laquelle les joueurs doivent jouer
    //ainsi que 2 textes utiles à la compréhension du texte
    static void affichageCarteActuelle(JFrame page, Partie partie){
        ImageIcon carteActuelleImage = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(partie.actuel),"carteActuelle");
        JLabel carteActuelle = new JLabel();
        carteActuelle.setBounds(460,290,80,120);
        carteActuelleImage.setImage(carteActuelleImage.getImage().getScaledInstance((carteActuelle.getWidth()),carteActuelle.getHeight(), Image.SCALE_SMOOTH));
        carteActuelle.setIcon(carteActuelleImage);
        page.add(carteActuelle);

        JLabel auTourDe = new JLabel("Au tour de "+partie.nomJoueurs.get(partie.tour));
        auTourDe.setBounds(440,220,140,30);
        auTourDe.setOpaque(true);
        page.add(auTourDe);
        JLabel debloquer = new JLabel("Partie bloquée, joue n'importe quelle carte");
        debloquer.setBounds(400,255,260,30);
        debloquer.setVisible(false);
        page.add(debloquer);
        if (partie.nb_passe>=partie.nb_joueurs){
            debloquer.setVisible(true);
        }
    }
    static void tourOrdinateurUI(JFrame page, Partie partie){
        Timer timer = new Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                partie.tour_ordinateur(partie.tour);
                page.getContentPane().removeAll();
                page.revalidate();
                page.repaint();
                jeu1(page,partie);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    static void ordinateurHautUI(JFrame page, Partie partie, int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setBounds(450,10,100,30);
        page.add(nomJoueur);

        JPanel carteOrdinateurPanel = new JPanel();
        carteOrdinateurPanel.setBounds(250,50,500,120);
        carteOrdinateurPanel.setLayout(null);
        for ( int i = 0 ; i < partie.Mains.get(numeroJoueur).size() ; i++ ){
            JLabel carteOrdinateurHaut = new JLabel();
            carteOrdinateurHaut.setBounds(400-(i*100),0,80,120);
            carteOrdinateurHaut.setOpaque(true);
            carteOrdinateurHaut.setBackground(Color.ORANGE);
            carteOrdinateurHaut.setText("colorAddict");
            carteOrdinateurHaut.setHorizontalAlignment(SwingConstants.CENTER);
            carteOrdinateurPanel.add(carteOrdinateurHaut);
        }
        page.add(carteOrdinateurPanel);
        JLabel piocheordinateurHaut = new JLabel("Pioche");
        piocheordinateurHaut.setBounds(340,180,80,60);
        piocheordinateurHaut.setOpaque(true);
        piocheordinateurHaut.setBackground(Color.RED);
        piocheordinateurHaut.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(piocheordinateurHaut);
        if (partie.Decks.get(numeroJoueur).isEmpty()){
            piocheordinateurHaut.setText("Vide");
        }
    }
    static void joueurHautUI(JFrame page, Partie partie,int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setBounds(450,10,100,30);
        page.add(nomJoueur);

        JPanel cartesJoueurPanel = new JPanel();
        cartesJoueurPanel.setBounds(250,50,500,120);
        cartesJoueurPanel.setLayout(null);

        for (int j=0;j<partie.Mains.get(numeroJoueur).size();j++){
            final int Finalj = j;
            JButton carteJoueur = new JButton();
            carteJoueur.setBounds((400-(j*100)),0,80,120);
            //carteJoueur.setPreferredSize(new Dimension(80,125));
            ImageIcon carteJoueurImage = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(partie.Mains.get(numeroJoueur).get(j)),"carte" + j +  "joueur1");
            carteJoueurImage.setImage(carteJoueurImage.getImage().getScaledInstance((carteJoueur.getWidth()),carteJoueur.getHeight(),Image.SCALE_SMOOTH));
            carteJoueur.setIcon(carteJoueurImage);
            if (partie.tour==numeroJoueur){
                carteJoueur.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        partie.jouer(Finalj);
                        if (partie.actuel.couleur==null){
                            jokerPlayed(page,partie);
                        }
                        page.getContentPane().removeAll();
                        page.revalidate();
                        page.repaint();
                        jeu1(page, partie);
                    }
                });
            }
            cartesJoueurPanel.add(carteJoueur);
        }
        page.add(cartesJoueurPanel);

        JButton piocheJoueur = new JButton("Pioche");
        if (partie.Decks.get(numeroJoueur).isEmpty())
            piocheJoueur.setText("Passer");
        piocheJoueur.setBounds(340,180,80,60);
        page.add(piocheJoueur);
        if (partie.tour==numeroJoueur){
            piocheJoueur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    partie.piocher(partie.tour);
                    page.getContentPane().removeAll();
                    page.revalidate();
                    page.repaint();
                    jeu1(page, partie);
                }
            });
        }//pioche tour joueur (fini)
    }
    static void ordinateurBasUI(JFrame page,Partie partie,int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setBounds(450,650,100,30);
        page.add(nomJoueur);

        JPanel carteOrdinateurPanel = new JPanel();
        carteOrdinateurPanel.setBounds(250,525,500,120);
        carteOrdinateurPanel.setLayout(null);
        for (int j=0;j<partie.Mains.get(numeroJoueur).size();j++){
            final int Finalj = j;
            JLabel carteOrdinateur = new JLabel();
            carteOrdinateur.setBounds((j*100),0,80,120);
            carteOrdinateur.setOpaque(true);
            carteOrdinateur.setBackground(Color.ORANGE);
            carteOrdinateur.setText("colorAddict");
            carteOrdinateur.setHorizontalAlignment(SwingConstants.CENTER);
            carteOrdinateurPanel.add(carteOrdinateur);
        }
        page.add(carteOrdinateurPanel);
        JLabel piocheordinateurBas = new JLabel("Pioche");
        piocheordinateurBas.setBounds(580,450,80,60);
        piocheordinateurBas.setOpaque(true);
        piocheordinateurBas.setBackground(Color.RED);
        piocheordinateurBas.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(piocheordinateurBas);
        if (partie.Decks.get(numeroJoueur).isEmpty()){
            piocheordinateurBas.setText("Vide");
        }
    }
    static void joueurBasUI(JFrame page, Partie partie,int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setBounds(450,650,100,30);
        page.add(nomJoueur);

        JPanel cartesJoueurPanel = new JPanel();
        cartesJoueurPanel.setBounds(250,525,500,120);
        cartesJoueurPanel.setLayout(null);

        for (int j=0;j<partie.Mains.get(numeroJoueur).size();j++){
            final int Finalj = j;
            JButton carteJoueur = new JButton();
            carteJoueur.setBounds((j*100),0,80,120);
            //carteJoueur.setPreferredSize(new Dimension(80,125));
            ImageIcon carteJoueurImage = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(partie.Mains.get(numeroJoueur).get(j)),"carte" + j +  "joueur1");
            carteJoueurImage.setImage(carteJoueurImage.getImage().getScaledInstance((carteJoueur.getWidth()),carteJoueur.getHeight(),Image.SCALE_SMOOTH));
            carteJoueur.setIcon(carteJoueurImage);
            if (partie.tour==numeroJoueur){
                carteJoueur.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        partie.jouer(Finalj);
                        if (partie.actuel.couleur==null){
                            jokerPlayed(page,partie);
                        }
                        page.getContentPane().removeAll();
                        page.revalidate();
                        page.repaint();
                        jeu1(page, partie);
                    }
                });
            }
            cartesJoueurPanel.add(carteJoueur);
        }
        page.add(cartesJoueurPanel);

        JButton piocheJoueur = new JButton("Pioche");
        if (partie.Decks.get(numeroJoueur).isEmpty())
            piocheJoueur.setText("Passer");
        piocheJoueur.setBounds(580,450,80,60);
        page.add(piocheJoueur);
        if (partie.tour==numeroJoueur){
            piocheJoueur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    partie.piocher(partie.tour);
                    page.getContentPane().removeAll();
                    page.revalidate();
                    page.repaint();
                    jeu1(page, partie);
                }
            });
        }//pioche tour joueur (fini)
    }
    static void joueurGaucheUI(JFrame page,Partie partie,int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.LEFT);
        nomJoueur.setBounds(185,330,100,30);
        page.add(nomJoueur);

        JPanel cartesJoueurPanel = new JPanel();
        cartesJoueurPanel.setBounds(50,110,120,500);
        cartesJoueurPanel.setLayout(null);

        for (int j=0;j<partie.Mains.get(numeroJoueur).size();j++){
            final int Finalj = j;
            JButton carteJoueur = new JButton();
            carteJoueur.setBounds(0,(j*100),120,80);
            ImageIcon carteJoueurImage = rotateImageIcon(ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(partie.Mains.get(numeroJoueur).get(j)),"carte" + j +  "joueur1"),Math.PI/2);
            carteJoueurImage.setImage(carteJoueurImage.getImage().getScaledInstance((carteJoueur.getWidth()),carteJoueur.getHeight(),Image.SCALE_SMOOTH));
            carteJoueur.setIcon(carteJoueurImage);
            if (partie.tour==numeroJoueur){
                carteJoueur.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        partie.jouer(Finalj);
                        if (partie.actuel.couleur==null){
                            jokerPlayed(page,partie);
                        }
                        page.getContentPane().removeAll();
                        page.revalidate();
                        page.repaint();
                        jeu1(page, partie);
                    }
                });
            }
            cartesJoueurPanel.add(carteJoueur);
        }
        page.add(cartesJoueurPanel);

        JButton piocheJoueur = new JButton("Pioche");
        if (partie.Decks.get(numeroJoueur).isEmpty())
            piocheJoueur.setText("Passer");
        piocheJoueur.setBounds(185,360,60,80);
        page.add(piocheJoueur);
        if (partie.tour==numeroJoueur){
            piocheJoueur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    partie.piocher(partie.tour);
                    page.getContentPane().removeAll();
                    page.revalidate();
                    page.repaint();
                    jeu1(page, partie);
                }
            });
        }//pioche tour joueur (fini)
    }
    static void ordinateurGaucheUI(JFrame page,Partie partie,int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.LEFT);
        nomJoueur.setBounds(185,330,100,30);
        page.add(nomJoueur);

        JPanel carteOrdinateurPanel = new JPanel();
        carteOrdinateurPanel.setBounds(50,110,120,500);
        carteOrdinateurPanel.setLayout(null);
        for (int j=0;j<partie.Mains.get(numeroJoueur).size();j++){
            final int Finalj = j;
            JLabel carteOrdinateur = new JLabel();
            carteOrdinateur.setBounds(0,(j*100),120,80);
            carteOrdinateur.setOpaque(true);
            carteOrdinateur.setBackground(Color.ORANGE);
            carteOrdinateur.setText("colorAddict");
            carteOrdinateur.setHorizontalAlignment(SwingConstants.CENTER);
            carteOrdinateurPanel.add(carteOrdinateur);
        }
        page.add(carteOrdinateurPanel);
        JLabel piocheordinateur = new JLabel("Pioche");
        piocheordinateur.setBounds(185,360,60,80);
        piocheordinateur.setOpaque(true);
        piocheordinateur.setBackground(Color.RED);
        piocheordinateur.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(piocheordinateur);
        if (partie.Decks.get(numeroJoueur).isEmpty()){
            piocheordinateur.setText("Vide");
        }
    }
    static void joueurDroitUI(JFrame page,Partie partie,int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.RIGHT);
        nomJoueur.setBounds(725,250,100,30);
        page.add(nomJoueur);

        JPanel cartesJoueurPanel = new JPanel();
        cartesJoueurPanel.setBounds(830,110,120,500);
        cartesJoueurPanel.setLayout(null);

        for (int j=0;j<partie.Mains.get(numeroJoueur).size();j++){
            final int Finalj = j;
            JButton carteJoueur = new JButton();
            carteJoueur.setBounds(0,400-(j*100),120,80);
            ImageIcon carteJoueurImage = rotateImageIcon(ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(partie.Mains.get(numeroJoueur).get(j)),"carte" + j +  "joueur1"),Math.PI/2);
            carteJoueurImage.setImage(carteJoueurImage.getImage().getScaledInstance((carteJoueur.getWidth()),carteJoueur.getHeight(),Image.SCALE_SMOOTH));
            carteJoueur.setIcon(carteJoueurImage);
            if (partie.tour==numeroJoueur){
                carteJoueur.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        partie.jouer(Finalj);
                        if (partie.actuel.couleur==null){
                            jokerPlayed(page,partie);
                        }
                        page.getContentPane().removeAll();
                        page.revalidate();
                        page.repaint();
                        jeu1(page, partie);
                    }
                });
            }
            cartesJoueurPanel.add(carteJoueur);
        }
        page.add(cartesJoueurPanel);

        JButton piocheJoueur = new JButton("Pioche");
        if (partie.Decks.get(numeroJoueur).isEmpty())
            piocheJoueur.setText("Passer");
        piocheJoueur.setBounds(755,280,60,80);
        page.add(piocheJoueur);
        if (partie.tour==numeroJoueur){
            piocheJoueur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    partie.piocher(partie.tour);
                    page.getContentPane().removeAll();
                    page.revalidate();
                    page.repaint();
                    jeu1(page, partie);
                }
            });
        }//pioche tour joueur (fini)
    }
    static void ordinateurDroitUI(JFrame page,Partie partie,int numeroJoueur){
        JLabel nomJoueur = new JLabel(partie.nomJoueurs.get(numeroJoueur));
        nomJoueur.setHorizontalAlignment(SwingConstants.RIGHT);
        nomJoueur.setBounds(725,250,100,30);
        page.add(nomJoueur);

        JPanel carteOrdinateurPanel = new JPanel();
        carteOrdinateurPanel.setBounds(830,110,120,500);
        carteOrdinateurPanel.setLayout(null);
        for (int j=0;j<partie.Mains.get(numeroJoueur).size();j++){
            final int Finalj = j;
            JLabel carteOrdinateur = new JLabel();
            carteOrdinateur.setBounds(0,400-(j*100),120,80);
            carteOrdinateur.setOpaque(true);
            carteOrdinateur.setBackground(Color.ORANGE);
            carteOrdinateur.setText("colorAddict");
            carteOrdinateur.setHorizontalAlignment(SwingConstants.CENTER);
            carteOrdinateurPanel.add(carteOrdinateur);
        }
        page.add(carteOrdinateurPanel);
        JLabel piocheordinateur = new JLabel("Pioche");
        piocheordinateur.setBounds(755,280,60,80);
        piocheordinateur.setOpaque(true);
        piocheordinateur.setBackground(Color.RED);
        piocheordinateur.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(piocheordinateur);
        if (partie.Decks.get(numeroJoueur).isEmpty()){
            piocheordinateur.setText("Vide");
        }
    }

    //fonction trouvée sur internet qui prend une ImageIcon et un angle en radian afin de lui faire
    //subir une rotation
    static private ImageIcon rotateImageIcon(ImageIcon picture, double angle) {
        int w = picture.getIconWidth();
        int h = picture.getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = image.createGraphics();
        double x = (h - w) / 2.0;
        double y = (w - h) / 2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(angle, w / 2.0, h / 2.0);
        g2.drawImage(picture.getImage(), at, null);
        g2.dispose();
        picture = new ImageIcon(image);

        return picture;
    }


}
