import java.util.LinkedList;
public class Partie {
    public LinkedList<Carte> piocheJ;
    public LinkedList<Carte> piocheO;
    public LinkedList<Carte> jeu;
    public Carte actuel;

    public void jouer(Carte c)
    {
        if(compare(actuel,c))
        {
            actuel = c;
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
