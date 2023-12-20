import javax.swing.*;

public class ImageUtilitaire {
    static protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = ImageUtilitaire.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    static public String imageCartes(Carte carte) {
        String file;
        if (carte.couleur == null) {
            file = "carteImages/Joker.png";
            return file;
        }

        file = "carteImages/ecrit" + carte.couleur + carte.ecrit + ".png";

        //String descr = carte.couleur.toString() + " et " + carte.ecrit.toString();
        return file;
    }


}
