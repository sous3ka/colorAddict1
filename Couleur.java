public enum Couleur {
    BLEU(0),
    JAUNE(1),
    ROUGE(2),
    VERT (3),
    ORANGE (4),
    ROSE (5),
    NOIR (6);
    public final int valeur;

    private Couleur(int valeur)
    {
        this.valeur = valeur;
    }

    public int getValeur(){
        return valeur;
    }
}
