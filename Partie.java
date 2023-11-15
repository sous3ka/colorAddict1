import java.util.LinkedList;
public class Partie {
    public LinkedList<LinkedList<Carte>> Decks; // decks (pioche) des joueurs
    public LinkedList<LinkedList<Carte>> Mains; // mains des joueurs

    public LinkedList<Carte> jeu; //Sert pour créer les cartes, les mélanger et les distribuer.
    public Carte actuel; // Carte sur laquelle jouer
    public int tour; //indice du joueur qui joue
    public int nb_joueurs;

    //fonction qui change la valeur du tour
    public void change()
    {
        tour++;
        if(tour>=nb_joueurs)
        {
            tour = 0;
        }
    }

    public void jouer(Carte c)
    {
        if(compare(actuel,c))
        {
            actuel = c;
            change();
        }
    }

    public void piocher()
    {
        if(Decks.get(tour).get(0) != null)
        {
            Mains.get(tour).add(Decks.get(tour).get(0));
            Decks.get(tour).remove(0);
        }
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
        for (Couleur c1 : Couleur.values()) {
            for (Couleur c2 : Couleur.values()) {
                for (int exemplaire = 1; exemplaire <= 2; exemplaire++) {
                    Carte carteAAjouter = new Carte(c1, c2);
                    jeu.add(carteAAjouter);
                }
            }
        }
    }
}
