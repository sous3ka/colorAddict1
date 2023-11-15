public class Carte {
    public Couleur couleur;
    public Couleur ecrit;

    public Carte(Couleur c, Couleur e)
    {
        couleur = e;
        ecrit = c;
    }

    //foction pour DEBUG
    public String toString()
    {
        return "couleur : "+couleur+", ecrit : "+ecrit;
    }

}
