package modele;

public class Vide extends Case {

    public Vide(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutEtreParcouru(Entite EQuiVeutParcourir) {
        if(!(EQuiVeutParcourir instanceof Heros)){
            return ( (e == null) && (CaseAimant.CaseActuelPeutPousserAilleur(this, jeu)) ); // ici c'est au cas où la case est vide => pas de if(e != null) => pas de pousser => pas de verif: on verifie ici
        }
        return e==null;
    }



}
