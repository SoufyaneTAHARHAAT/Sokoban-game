package modele;

public class BlocObjectif extends Bloc{
    public BlocObjectif(Jeu _jeu, Case c) {
        super(_jeu, c);
    }
    public boolean pousser(Direction d) {
        return jeu.deplacerEntite(this, d);
    }
}
