package modele;

public class FinJeu {
    public boolean Fin;

    public Jeu jeu;

    public boolean NiveauReussi;

    public FinJeu(Jeu _jeu){
        jeu = _jeu;
        Fin = false; // Manipulé par la case vitre => si le héros tombe alors true
        NiveauReussi = true; // Manipulé par la case vitre => si le héros tombe alors false
    }

    public boolean FinDuJeu(){
        if(Fin){
            return Fin;
        } else {
            for (CaseObjectif caseObjectif : jeu.TabCaseObjectif) {
                if (!(caseObjectif.e instanceof BlocObjectif)) {
                    return Fin;
                }
            }
            Fin = true;
        }
        return Fin;
    }
}
