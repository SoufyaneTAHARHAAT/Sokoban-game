package modele;

public class CaseObjectif extends Vide{
    public CaseObjectif(Jeu _jeu) {super(_jeu);}

    @Override
    public boolean peutEtreParcouru(){
        return e==null;
    } // et non pas return true;
}
