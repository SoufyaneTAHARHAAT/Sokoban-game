package modele;
public class Niveau {

    private String [] TabNiveau;
    public int IndiceNiveauActuel;

    public Niveau(){
        IndiceNiveauActuel = -1;
        int nombreNiveaux = 7;
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

}

/**-------Resumé des niveau
 * - Niveau 0 = bloc cible + case cible
 * - Niveau 1 = bloc cible + case cible
 * - Niveau 2 = glace
 * - Niveau 5 = Vitre
 * - Niveau 6 = teleporter
 * */
