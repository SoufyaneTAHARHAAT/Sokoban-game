package modele;
public class Niveau {

    private String [] TabNiveau;
    private int IndiceNiveauActuel;

    public Niveau(){
        IndiceNiveauActuel = -1;
        int nombreNiveaux = 5;
        TabNiveau = new String[nombreNiveaux];
        for (int i = 0; i < nombreNiveaux; i++) {
            TabNiveau[i] = "Niveaux/Niveau" + i + ".txt";
        }
    }

    public String NiveauSuivant(){
        IndiceNiveauActuel++;
        System.out.print("\n"+IndiceNiveauActuel+"\n");
        return TabNiveau[IndiceNiveauActuel];
    }

}
