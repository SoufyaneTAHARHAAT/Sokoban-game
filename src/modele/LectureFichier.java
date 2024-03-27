package modele;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LectureFichier {
    BufferedReader contenu;
    char [][] tab;

    public LectureFichier(String cheminFichier, int TailleX, int TailleY){
        tab = new char[TailleX][TailleY];
        int NumLigne = 0;
        try {
            File fichier = new File(cheminFichier); // Créez un objet File en spécifiant le chemin du fichier

            // Créez un objet FileReader pour lire le contenu du fichier
            FileReader lecteurFichier = new FileReader(fichier); // On précise le fichier à lire


            contenu = new BufferedReader(lecteurFichier); // Utilisez BufferedReader pour lire efficacement le contenu du fichier ligne par ligne

            String ligne;
            // Lisez chaque ligne du fichier jusqu'à ce qu'il n'y en ait plus
            while ((ligne = contenu.readLine()) != null) {
                for (int i = 0; i < ligne.length(); i++) {
                    tab[i][NumLigne]= ligne.charAt(i); // if( Character.isWhitespace(ligne.charAt(i)) ) Vérifier si le caractère est un espace
                }
                NumLigne++;
            }

            // Fermez le lecteur une fois que vous avez fini de lire le fichier
            contenu.close();


            for(int y =0; y<TailleY-1; y++){
                for(int x=0; x<TailleX; x++){
                    System.out.print(tab[x][y]);
                }
                System.out.print("\n");
            }


        } catch (IOException e) {
            // Gérez les exceptions liées à la lecture du fichier
            e.printStackTrace();
        }
    }
}
