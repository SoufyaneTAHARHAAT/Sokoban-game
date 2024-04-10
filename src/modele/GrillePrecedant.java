package modele;

public class GrillePrecedant {
    private Case [][] grillePrecedante;
    Jeu jeu;
    public GrillePrecedant(Jeu _jeu){
        jeu = _jeu;
        grillePrecedante = new Case[Jeu.SIZE_Y][Jeu.SIZE_Y];
    }

    public void setGrillePrecedante() {
        for(int i=0; i<Jeu.SIZE_X; i++){
            for(int j=0; j<Jeu.SIZE_Y; j++){
                grillePrecedante[i][j]= jeu.getGrille()[i][j];
            }
        }
    }

    public void RevenirUnPasArriere(){
        for(int i=0; i<Jeu.SIZE_X; i++){
            for(int j=0; j<Jeu.SIZE_Y; j++){
                jeu.grilleEntites[i][j]= grillePrecedante[i][j];
                //pas finis
            }
        }
    }
}
