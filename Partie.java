import java.util.Collections;
import java.util.LinkedList;
public class Partie {
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
        nb_joueurs = nbj;
        Decks = new LinkedList<LinkedList<Carte>>();
        Humains = new boolean[nbj];
        for(int i = 0; i < nbj; i++)
        {
            Humains[i] = i<nbh;
        }
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
    public void jouer(int i)
    {
        if(compare(actuel,Mains.get(tour).get(i)))
        {
            actuel = Mains.get(tour).get(i);
            Mains.get(tour).remove(i);

            if (Mains.get(tour).size()<3)
                piocher(tour);
        }
    }

    //permet au joueur actuel de piocher SEULEMENT SI IL LE PEUT
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
    public void tour_ordinateur()
    {
        if(peut_jouer()[0] == 1)
        {
            jouer(peut_jouer()[1]);
        }
        else
        {
            if (!Decks.get(tour).isEmpty())
                piocher(tour);
        }
    }
}
