import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Partie {
    public ArrayList<String> nomJoueurs;// stock les noms des joueurs
    public LinkedList<LinkedList<Carte>> Decks; // decks (pioche) des joueurs
    public LinkedList<LinkedList<Carte>> Mains; // mains des joueurs
    public boolean[] Humains; // nombre d'humains dans la partie
    public LinkedList<Carte> jeu; //Sert pour créer les cartes, les mélanger et les distribuer.
    public Carte actuel; // Carte sur laquelle jouer
    public int tour; //indice du joueur qui joue
    public int nb_joueurs;

    //constructeur de la partie, reçoit en paramètre le nombre de joueurs de la partie
    //prepare la partie (création + distribution des cartes + choix aléatoire du premier joueur)
    public Partie(int nbj, int nbh)
    {
        nomJoueurs = new ArrayList<>();
        nb_joueurs = nbj;
        Decks = new LinkedList<LinkedList<Carte>>();
        Humains = new boolean[nbj];
        Mains = new LinkedList<LinkedList<Carte>>();
        for(int i = 0; i < nbj; i++)
        {
            LinkedList<Carte> d = new LinkedList<Carte>();
            LinkedList<Carte> m = new LinkedList<Carte>();
            Decks.add(d);
            Mains.add(m);
        }
        creerDeck();
        Collections.shuffle(jeu);
        tour = (int)(Math.random() * nbj);
        while(jeu.size()>1)
        {
            Decks.get(tour).add(jeu.get(0));
            jeu.remove(0);
            change();
        }
        actuel = jeu.get(0);
        jeu.remove(0);
        for(int i = 0; i < 3*nbj; i++)
        {
            piocher(tour);
        }
    }

    //fonction qui change la valeur du tour
    public void change()
    {
        tour++;
        if(tour>=nb_joueurs)
        {
            tour = 0;
        }
    }

    //permet au joueur actuel de jouer la carte d'indice i dans sa main SEULEMENT SI IL LE PEUT
    //si ensuite il a moins de 3 cartes en main, il pioche.
    public void jouer(int i)
    {
        if(compare(actuel,Mains.get(tour).get(i)))
        {
            actuel = Mains.get(tour).get(i);
            Mains.get(tour).remove(i);

            if (Mains.get(tour).size()<3)
                piocher(tour);
            else change();
        }
    }

    //permet au joueur actuel de piocher si il a des cartes dans sa pioche et - de 5 cartes en mains
    //puis passe son tour
    public void piocher(int i)
    {
        if(!Decks.get(tour).isEmpty() && Mains.get(i).size()<5)
        {
            Mains.get(tour).add(Decks.get(tour).get(0));
            Decks.get(tour).remove(0);
        }
        change();
    }

    //fonction pour vérifier si l'on peut jouer la carte c2 sur la carte c1
    //c1 ne peut donc pas être un joker car elle est déja sur la table
    public boolean compare(Carte c1, Carte c2)
    {
        return(c1.couleur == c2.couleur
                || c1.couleur == c2.ecrit
                ||c1.ecrit == c2.couleur
                ||c1.ecrit == c2.ecrit
                ||c2 instanceof Joker);
    }

    //fonction qui créé le deck avec 2 exemplaires de chaques cartes + 12 joker, soit 49*2 + 12 = 110 cartes
    public void creerDeck() {
        jeu = new LinkedList<Carte>();
        for (Couleur c1 : Couleur.values()) {
            for (Couleur c2 : Couleur.values()) {
                for (int exemplaire = 1; exemplaire <= 2; exemplaire++) {
                    Carte carteAAjouter = new Carte(c1, c2);
                    jeu.add(carteAAjouter);
                }
            }
        }
        Carte carteJoker = new Joker();
        for (int i=0;i<12;i++){
            jeu.add(carteJoker);
        }
    }

    //fonction permettant de vérifier que le joueur actuel peut jouer
    //si ce dernier peut jouer, alors la premiere valeur du tableau retourné sera 1 et la deuxieme sera l'indice de la carte qu'il peut jouer
    //sinon, la premiere valeur du tableau retourné sera 0 et la deuxieme n'aura pas d'importance
    public int[] peut_jouer()
    {
        int[] rep = new int[2];
        rep[0] = 0;
        int i = 0;
        while(!(rep[0] == 1) && i < Mains.get(tour).size())
        {
            if(compare(actuel,Mains.get(tour).get(i)))
            {
                rep[0] = 1;
                rep[1] = i;
            }
            i++;
        }
        return rep;
    }

    //fonction permettant à l'ordinateur de jouer si il le peut, sinon il pioche
    public void tour_ordinateur(int tourOrdinateur)
    {
        if(peut_jouer()[0] == 1)
        {
            jouer(peut_jouer()[1]);
            if (actuel.couleur==null){  //si l'ordinateur joue un joker, il le transforme une une carte qui
                // a une couleur qu'il possede et un écrit qu'il possède
                int i = (int)(Math.random()*Mains.get(tourOrdinateur).size());
                //tour = (int)(Math.random() * nbj);
                actuel.couleur = Mains.get(tourOrdinateur).get(i).couleur;
                i = (int)(Math.random()*Mains.get(tourOrdinateur).size());
                actuel.ecrit = Mains.get(tourOrdinateur).get(i).ecrit;
            }
        }
        else
        {
            piocher(tour);
        }
    }
}
