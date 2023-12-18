import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class UserInterface {

    static void creerPage(JFrame page) {
        page.setVisible(true);//Assure la visibilité de la page
        page.setLayout(null);//Permet de gérer manuellement tout les composants de la page
        page.setSize(1000, 720);//Taille de la page
        page.setTitle("Le jeu des couleurs");//Titre de la page
        page.setResizable(false);
        page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Assure que lorsqu'on clique sur la croix, la page se ferme et ne consomme plus de RAM
    }

    static void pageAccueil(JFrame page){

        final JTextArea[][] nomsJoueurs = new JTextArea[1][];
        final JCheckBox[][] estUnJoueur = new JCheckBox[1][];

        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.setBounds(440,310,120,80);
        boutonJouer.setEnabled(false);

        JComboBox<Integer> nombreJoueursComboBox = new JComboBox<>(new Integer[]{2, 3, 4});
        nombreJoueursComboBox.setBounds(450, 50, 100, 30);
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
                    //System.out.println(nomsJoueurs[0][i].getText());
                }
                nombreJoueursPanel.revalidate();
                nombreJoueursPanel.repaint();
                boutonJouer.setEnabled(true);//rend le bouton pour lancer la partie cliquable
            }
        });
        page.add(nombreJoueursPanel);
        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Partie enCours;
                do {//ce "do" permet de vérifier que la partie créee ne commence pas avec un Joker
                    enCours = new Partie(2,1);
                }while (enCours.actuel.couleur==null);
                for (int i =0;i<nomsJoueurs[0].length;i++){//mets les valeurs des JTextArea dans
                    enCours.nomJoueurs.add(nomsJoueurs[0][i].getText());//l'arrayList nomJoueurs
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

        System.out.println(enCours.actuel);
        for (int i=0;i<enCours.nomJoueurs.size();i++){
            System.out.println(enCours.nomJoueurs.get(i).toString());
        }

        JLabel nomAdversaire = new JLabel(enCours.nomJoueurs.get(1));
        nomAdversaire.setHorizontalAlignment(SwingConstants.CENTER);
        nomAdversaire.setBounds(450,10,100,30);

        JLabel nomJoueur = new JLabel(enCours.nomJoueurs.get(0));
        nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        nomJoueur.setBounds(450,650,100,30);

        ImageIcon carteActuelleImage = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(enCours.actuel),"carteActuelle");

        JLabel carteActuelle = new JLabel();
        carteActuelle.setBounds(460,280,80,125);
        carteActuelleImage.setImage(carteActuelleImage.getImage().getScaledInstance((carteActuelle.getWidth()),carteActuelle.getHeight(), Image.SCALE_SMOOTH));
        carteActuelle.setIcon(carteActuelleImage);
        page.add(carteActuelle);


        for ( int i = 0 ; i < enCours.Mains.get(1).size() ; i++ ){
            JLabel carteAdversaire1 = new JLabel();
            carteAdversaire1.setBounds(350+(i*100),50,80,125);
            carteAdversaire1.setOpaque(true);
            carteAdversaire1.setBackground(Color.ORANGE);
            carteAdversaire1.setText(enCours.Mains.get(1).get(i).toString());
            page.add(carteAdversaire1);
        }

        JPanel cartesJoueurPanel = new JPanel();
        cartesJoueurPanel.setBounds(250,520,500,125);
        cartesJoueurPanel.setLayout(null);

        for (int j=0;j<enCours.Mains.get(0).size();j++){
            final int Finalj = j;
            System.out.println("carte numéro"+ (j+1));
            JButton carteJoueur = new JButton();
            carteJoueur.setBounds((j*100),0,80,125);
            carteJoueur.setPreferredSize(new Dimension(80,125));
            ImageIcon carteJoueurImage = ImageUtilitaire.createImageIcon(ImageUtilitaire.imageCartes(enCours.Mains.get(0).get(j)),"carte" + j +  "joueur1");
            carteJoueurImage.setImage(carteJoueurImage.getImage().getScaledInstance((carteJoueur.getWidth()),carteJoueur.getHeight(),Image.SCALE_SMOOTH));
            carteJoueur.setIcon(carteJoueurImage);
            if (enCours.tour==0){
                carteJoueur.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("carte cliquée : "+enCours.Mains.get(0).get(Finalj).toString());
                        enCours.jouer(Finalj);
                        if (enCours.actuel.couleur==null){
                            jokerPlayed(page,enCours);
                        }
                        page.getContentPane().removeAll();
                        page.revalidate();
                        page.repaint();
                        jeu1(page, enCours);
                    }
                });
            }
            cartesJoueurPanel.add(carteJoueur);
        }
        page.add(cartesJoueurPanel);

        JButton piocheJoueur = new JButton("Pioche");
        if (enCours.Decks.get(0).isEmpty())
            piocheJoueur.setText("Passer");
        piocheJoueur.setBounds(650,420,80,60);
        page.add(piocheJoueur);
        if (enCours.tour==0){
            piocheJoueur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    enCours.piocher(enCours.tour);
                    page.getContentPane().removeAll();
                    page.revalidate();
                    page.repaint();
                    jeu1(page, enCours);
                }
            });
        }//pioche tour joueur (fini)

        System.out.println("tour du joueur: "+ enCours.tour +" "+ enCours.nomJoueurs.get(enCours.tour));

        if (enCours.tour == 1){
            System.out.println("Tour ordinateur");
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    enCours.tour_ordinateur(1);
                    page.getContentPane().removeAll();
                    page.revalidate();
                    page.repaint();
                    jeu1(page,enCours);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }//tour de l'ordinateur (fini)
        
        page.add(nomJoueur);
        page.add(nomAdversaire);



    }

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

}
