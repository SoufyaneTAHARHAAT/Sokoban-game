package modele;
public class Niveau {

    /**
     * !!!!!!!!!!!!!!!!!! CLASSE NON FINI DONC NON INSTENTIABLE !!!!!!!!!!!!!!!!!!!!*/
    private String [] TabNiveau;
    public int IndiceNiveauActuel;
    Jeu jeu;

    public Niveau(Jeu _jeu){
        jeu = _jeu;
        IndiceNiveauActuel = -1;
        int nombreNiveaux = 9;
        TabNiveau = new String[nombreNiveaux];
        for (int i = 0; i < nombreNiveaux; i++) {
            TabNiveau[i] = "Niveaux/Niveau" + i + ".txt";
        }
    }

    public String CheminNiveauSuivant(){
        IndiceNiveauActuel= (IndiceNiveauActuel + 1)%TabNiveau.length;
        System.out.print("\n"+IndiceNiveauActuel+"\n");
        return TabNiveau[IndiceNiveauActuel];
    }
    public void RecommencerNiveau(){
        jeu.niveau.IndiceNiveauActuel--; // pour compenser l'incrémentation automatique de CheminNiveauSuivant dans jeu.initialisationNiveau car ce dernier appelle Niveau.CheminNiveauSuivant
        jeu.initialisationNiveau();
    }

    public void NiveauPrecedant(){
        jeu.niveau.IndiceNiveauActuel -= 2; // pour compenser l'incrémentation automatique de CheminNiveauSuivant dans jeu.initialisationNiveau
        if(jeu.niveau.IndiceNiveauActuel < -1){
            jeu.niveau.IndiceNiveauActuel = -1;
        }
        jeu.initialisationNiveau();
    }

    public void NiveauSuivant(){
        jeu.initialisationNiveau();
    }

}

/**-------Resumé des niveau
 * - Niveau 0 = bloc cible + case cible
 * - Niveau 1 = bloc cible + case cible
 * - Niveau 2 = glace
 * - Niveau 5 = Vitre
 * - Niveau 6 = teleporter
 * - Niveau 7 = Aimant
 * - Niveau 8 = Rail
 * */
