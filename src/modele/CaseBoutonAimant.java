package modele;

public class CaseBoutonAimant extends Vide{
    public CaseBoutonAimant(Jeu _jeu){
        super(_jeu);
    }

    @Override public boolean entrerSurLaCase(Entite e){
        setEntite(e);
        for(int i=0; i<CaseAimant.NbCaseAimant; i++){
            CaseAimant.TabCaseAimant[i].IncrementerIndiceLocaleMAJPointABloquer();
        }
        return true;
    }
}
