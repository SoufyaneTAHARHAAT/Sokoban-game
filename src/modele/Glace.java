package modele;

import java.awt.Point;

public class Glace extends Case {
    private Jeu jeu;

    public Glace(Jeu _jeu) {
        super(_jeu);
        this.jeu = _jeu;
    }

    @Override
    public boolean peutEtreParcouru() {
        return true;
    }

    @Override
    public boolean entrerSurLaCase(Entite e) {
        boolean entreeReussie = super.entrerSurLaCase(e);
        if (entreeReussie && e instanceof BlocObjectif) {
            // Si un BlocObjectif entre sur une case de glace, il effectue un deuxième déplacement dans la même direction
            Direction direction = e.getDirection();
            Point pCourant = jeu.getMap().get(this);
            Point pCibleSecondaire = jeu.calculerPointCible(pCourant, direction);
            if (jeu.contenuDansGrille(pCibleSecondaire) && jeu.caseALaPosition(pCibleSecondaire).peutEtreParcouru()) {
                // Effectuer le deuxième déplacement
                e.getCase().quitterLaCase();
                jeu.caseALaPosition(pCibleSecondaire).entrerSurLaCase(e);
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
