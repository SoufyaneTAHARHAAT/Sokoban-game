package modele;

/**
 * Entités amenées à bouger
 */
public abstract class Entite extends Obj {

    protected Case c;

    public Entite(Jeu _jeu, Case _c) { super(_jeu); c = _c; c.setEntite(this);}

    public void quitterCase() {
        c = null;
    }

    public Case getCase() {
        return c;
    }

    public void setCase(Case _c) {
        c = _c;
    }

    public void allerSurCase(Case _c) {
        c = _c;
    }

    public boolean pousser(Direction d) {
        return false;
    }


    public boolean avancerDirectionChoisie(Direction d) {

        return jeu.deplacerEntite(this, d);
    }

    public abstract Direction getDirection();
}

/**
 * - Entités amenées à bouger. ex: bloc, héro etc
 * - La classe entité hérite d'Obj
 * - la classe Entité contient un donnée membre de type case qui contient
 *   l'entité
 * - le constructeur de Entite stocke la case qui la contienne ET informe
 *   aussi la case qui la contienne en faisant "c.setEntite(this)".
 * - "getCase" retourne la case qui contient l'entité.
 * - "setCase" et "allerSurCase" change la case qui contient cette entité
 * */
