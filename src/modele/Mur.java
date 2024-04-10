package modele;

public class Mur extends Case {
    public Mur(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutEtreParcouru(Entite EQuiVeutParcourir, Direction DirectionSource) {
        return false;
    }
}
