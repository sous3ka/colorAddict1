import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame fenetre = new JFrame();
        makeFrameFullSize(fenetre);
        fenetre.setVisible(true);
        fenetre.setTitle("Color Addict");
    }

    //fonction (trouvée sur un forum) pour mettre les dimensions d'un jFrame au maximum
    static private void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }
}

