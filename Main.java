import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Partie p = new Partie(3);
        JFrame pagePrincipale = new JFrame();
        //UserInterface.imageCartes();
        UserInterface2.creerPage(pagePrincipale);
        UserInterface2.pageAccueil(pagePrincipale);

/*        for(int i = 0; i < p.jeu.size();i++)
        {
            System.out.println(p.jeu.get(i).toString() + i);
        }*/
    }
}
