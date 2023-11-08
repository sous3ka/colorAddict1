// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
    Carte c1 = new Carte(Couleur.ROUGE, Couleur.BLEU);
    Carte c2 = new Carte(Couleur.BLEU, Couleur.ROUGE);
    System.out.println(Partie.compare(c1,c2));
    }
}