package modele;

import java.awt.Point;

public class Glace extends Vide {
    private Jeu jeu;

    public Glace(Jeu _jeu) {
        super(_jeu);
        this.jeu = _jeu;
    }

    @Override
    public boolean entrerSurLaCase(Entite e, Direction DirectionSource) {
        boolean entreeReussie = super.entrerSurLaCase(e, DirectionSource);
        if (entreeReussie && e instanceof BlocObjectif) {
            // Si un BlocObjectif entre sur une case de glace, il effectue un deuxième déplacement dans la même direction
            Direction direction = e.getDirection();
            Point pCourant = jeu.getMap().get(this);
            Point pCibleSecondaire = jeu.calculerPointCible(pCourant, direction);
            if (jeu.contenuDansGrille(pCibleSecondaire) && jeu.caseALaPosition(pCibleSecondaire).peutEtreParcouru(this.e, DirectionSource)) {
                // Effectuer le deuxième déplacement
                e.getCase().quitterLaCase();
                jeu.caseALaPosition(pCibleSecondaire).entrerSurLaCase(e, DirectionSource);
            }
        }
        return entreeReussie;
    }

    @Override
    public void quitterLaCase() {
        // La méthode quitterLaCase par défaut suffit pour une case de glace
        super.quitterLaCase();
    }
}