/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

public abstract class Case extends Obj {
    public boolean PeutEtreQuitter(Direction DirectionProchainCase){
        return true;
    }

    protected Entite e;
    public abstract boolean peutEtreParcouru(Entite EQuiVeutParcourir, Direction DirectionSource);


    // Cette fonction (a redéfinir) détermine le comportement (qui peut être complexe) lorsque l'entité entre dans la case
    public boolean entrerSurLaCase(Entite e, Direction DirectionSource) {

        //Case c = e.getCase();
        //if (c !=null) {
        //    c.quitterLaCase();
        //}

        setEntite(e);
        e.setCase(this); // A voir si ça ne crée pas de problème
        return true;
    }

    public void quitterLaCase() {
        e = null;
    }



    public Case(Jeu _jeu) {
        super(_jeu);
    }

    public Entite getEntite() {
        return e;
    }

    public void setEntite(Entite _e) {

        e = _e;
        e.setCase(this);
    }


}





   /**
    * - la donnée membre "Entite e" contient l'élément qui est dans cette case
    * - "boolean entrerSurLaCase" les choses à faire avant de rentrer
    *   dans une case
    * - "quitterLaCase" les choses à faire après avoir quitter une case
    * - "contructeur" appelle juste le constructeur de Obj
    * - "getEntite" renvoie juste l'élément qui est dans cette case :
    *   son donnée membre "Entite e".
    * - "setEntite" modifie la valeur du donnée membre entité.
    * */
   /*
   * Obj -> Case
   * */
