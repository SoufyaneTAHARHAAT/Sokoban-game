
import VueControleur.VueControleur;
import modele.Jeu;
import modele.LectureFichier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Main {
    public static void main(String[] args) {
        Jeu jeu = new Jeu();

        //LectureFichier L = new LectureFichier("Niveaux/Niveau1.txt", Jeu.SIZE_X, Jeu.SIZE_Y);
        
        VueControleur vc = new VueControleur(jeu);
        vc.setVisible(true);

    }
}
