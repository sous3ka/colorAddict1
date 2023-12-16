import javax.swing.*;

public class ImageUtilitaire {
    static protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = UserInterface.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    static public String imageCartes(Carte carte) {
        if (carte.couleur == null) {
            String file = "carteImages/Joker.png";
            return file;
        }

        String file = "carteImages/ecrit" + carte.couleur.toString() + carte.ecrit.toString() + ".png";
        String descr = carte.couleur.toString() + " et " + carte.ecrit.toString();
        return file;
    }


}
