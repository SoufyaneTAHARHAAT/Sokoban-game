package modele;

public class Bloc extends Entite {


    public Bloc(Jeu _jeu, Case c) {
        super(_jeu, c);
    }

    public boolean pousser(Direction d) {
        return jeu.deplacerEntite(this, d);
    }

    @Override
    public Direction getDirection() {
        // Retourner une valeur par défaut (par exemple, Direction.haut) ou
        // lever une exception si cela n'a pas de sens pour votre application
        return Direction.haut;
    }
}

/**
 * - Cette classe hérite de Entité => son constructeur doit appeller
 *   celle de Entite pour lui passer le jeu ainsi que la case qui va
 *   contenir cette bloc
 * */
