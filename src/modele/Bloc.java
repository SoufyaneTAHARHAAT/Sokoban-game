package modele;

public class Bloc extends Entite {


    public Bloc(Jeu _jeu, Case c) {
        super(_jeu, c);
    }

    public boolean pousser(Direction d) {
        return jeu.deplacerEntite(this, d);
    }

}

/**
 * - Cette classe hérite de Entité => son constructeur doit appeller
 *   celle de Entite pour lui passer le jeu ainsi que la case qui va
 *   contenir cette bloc
 * */
