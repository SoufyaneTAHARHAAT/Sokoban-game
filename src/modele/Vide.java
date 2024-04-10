package modele;

public class Vide extends Case {

    public Vide(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutEtreParcouru(Entite EQuiVeutParcourir, Direction DirectionSource) {
        if(!(EQuiVeutParcourir instanceof Heros)){
            return ( (e == null) && (CaseAimant.CaseActuelPeutPousserAilleur(this, jeu)) ); // ici c'est au cas où la case est vide => pas de if(e != null) => pas de pousser => pas de verif: on verifie ici
        }
        return e==null;
    }

    @Override public boolean PeutEtreQuitter(Direction DirectionProchainCase){
        if(!(e instanceof Heros)){
            return CaseAimant.CaseActuelPeutPousserAilleur(this, jeu); // ici c'est au cas où la case est vide => pas de if(e != null) => pas de pousser => pas de verif: on verifie ici
        }
        return true;
    }



}
