package VueControleur;

import javax.swing.*;
import modele.Jeu;
import java.awt.*;

public class Message extends JFrame {

    private Jeu jeu;

    public Message(Jeu _jeu) {
        jeu = _jeu;
        setTitle("Message");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout()); // Utilisation de BorderLayout pour le conteneur principal

        //------------------------
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Utilisation de BoxLayout avec orientation verticale

        JLabel texteRaccourcis = new JLabel("R : recommencer");
        textPanel.add(texteRaccourcis); // Ajoutez le JLabel
        //------------------------
        texteRaccourcis = new JLabel("U: revenir en arrière");
        textPanel.add(texteRaccourcis); // Ajoutez le JLabel
        //------------------------
        texteRaccourcis = new JLabel("S: passer au niveau suivant");
        textPanel.add(texteRaccourcis); // Ajoutez le JLabel
        //------------------------
        texteRaccourcis = new JLabel("P: revenir au niveau précédant");
        textPanel.add(texteRaccourcis); // Ajoutez le JLabel
        //------------------------
        texteRaccourcis = new JLabel("Remarque: Si vous avez fini le dernier niveau, le jeu recommence depuis le niveau 1");
        textPanel.add(texteRaccourcis); // Ajoutez le JLabel

        JScrollPane scrollPane = new JScrollPane(textPanel); // Ajoutez un JScrollPane pour le texte
        add(scrollPane, BorderLayout.CENTER);

        //------------------------
        JButton BCommencer = new JButton("Commencer");
        BCommencer.addActionListener(e -> {
            setVisible(false); // laisser la fenêtre ouverte après le démarrage de la partie
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(BCommencer);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
    }
}
