package modele;

import modele.Case;
import modele.Vide;
import java.awt.Point;

public class CaseVitre extends Vide {
    public int CompteurPassageVitre;
    public CaseVitre(Jeu _jeu){
        super(_jeu);
        CompteurPassageVitre = 0;
    }

    @Override public void quitterLaCase() {
        e = null;
    }

    @Override public boolean entrerSurLaCase(Entite e) {
        if(CompteurPassageVitre == 0){ // => VitreCasse = 0 => 1er passage dedans: ça se casse à la sortie
            CompteurPassageVitre++;
            setEntite(e);
            e.setCase(this);
        }
        else if(CompteurPassageVitre == 1){ // => VitreCasse = 1 => 2eme passage dedans => absorbe l'entité qui y passe
            CompteurPassageVitre++;
            //e.getCase().setEntite(null); // erreur car setEntite(null) => null.setCase(this) = erreur;
            e.setCase(null); // => l'entité "existe" encore dans la mémoire mais n'est dans aucune case. Or le Vue =Controleur
                             // fait l'affichage en verifiant l'entité dans les case => il ne sera pas afficher
            //----Première idée: créer carrément une nouvelle case de type Vide à l'entrée de la Deuxième entité qui ne sera plus absorbé contrairement au 1er
                //Point PositionCase = jeu.map.get(this);
                //jeu.addCase(new Vide(jeu), PositionCase.x, PositionCase.y);
            //---
        }
        else if(CompteurPassageVitre == 2){ // => disparition de la case vitre pour devenir une case normale
            super.entrerSurLaCase(e); // Deuxième idée: on garde cette case de type case vide mais on utilise la fonction entrerSurLaCase de la case vide + icone de la case Vide dans VurControleur
        }
        System.out.print("\nCompteur de passage dans chaque caseVitre: " + CompteurPassageVitre + "\n");
        return true;
    }
}
