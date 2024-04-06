package modele;

/**
 * Entités amenées à bouger
 */

/**
 * RAPPELLE !!!!
 * chaque objet non basique (int etc) sont passé par référence et non pas par copie en java
 * Une objet est supprimé par le garbage collector automatiquement si aucun pointeur ne pointe dessus
 * La seul chose qui pointe sur une case en permanance c'est la case qui la contienne car la variable qui la crée depuis
 * l'initialisation du jeu est détruit à chaque boucle pour la nouvelle boucle */
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
        //return false;
        if(!(c.e instanceof Heros)){ // remarque: c.e = this bloc mais impossible d'utiliser le this directement car il ne peut pas downcaster this bloc to herso
            return ( (CaseAimant.CaseActuelPeutPousserAilleur(c, jeu)) && (jeu.deplacerEntite(this, d)) );
        }
        return jeu.deplacerEntite(this, d);
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
