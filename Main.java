public class Main {
    public static void main(String[] args) {
        Partie p = new Partie(2);
        for(int i = 0; i < p.jeu.size();i++)
        {
            System.out.println(p.jeu.get(i).toString());
        }
    }
}
