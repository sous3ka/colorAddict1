import java.util.LinkedList;
public class Partie {
    public LinkedList<Carte> pioche1;
    public LinkedList<Carte> pioche2;
    public LinkedList<Carte> pioche3;
    public LinkedList<Carte> pioche4;
    public LinkedList<Carte> pioche5;
    public LinkedList<Carte> pioche6;
    public LinkedList<Carte> jeu;
    public Carte actuel;
    public int tour;
    public int nb_joueurs;

    //fonction qui change la valeur du booleen tour
    public void change()
    {
        if(tour == true)
        {
            tour = false;
        }
        else
        {
            tour = true;
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




    //fonction pour vérifier si l'on peut jouer la carte c2 sur la carte c1
    //c1 ne peut donc pas être un joker car elle est déja sur la table
    static public boolean compare(Carte c1, Carte c2)
    {
        return(c1.couleur == c2.couleur
        || c1.couleur == c2.ecrit
        ||c1.ecrit == c2.couleur
        ||c1.ecrit == c2.ecrit
        ||c2 instanceof Joker);
    }
}
