package modele;

import java.awt.Point;

public class CaseTeleporter extends Vide{

    public static CaseTeleporter [] TabCaseTeleporter = new CaseTeleporter[5]; // on ne peut pas l'initialiser dans le constructeur car ça voudrai dire qu'on crée une nouvelle tableau à chaque création de nouvelle instance
    public static int NbCaseTeleporter = 0; // on ne peut pas l'initialiser dans le constructeur car ça voudrai dire qu'on le remet à 0 à chaque création de nouvelle instance

    public static Direction [] TabDirection;
    public CaseTeleporter(Jeu _jeu) {
        super(_jeu);
        TabDirection = new Direction[4];
        TabDirection[0] = Direction.haut;
        TabDirection[1] = Direction.bas;
        TabDirection[2] = Direction.gauche;
        TabDirection[3] = Direction.droite;
    }

    public void DebugAfficheDonneeMembre(){
        System.out.print("\n--------------Debut affichage------------------\n");
        System.out.print("\nAffichage du tableau TabCaseTeleporter: \n");
        for(int i=0; i<NbCaseTeleporter; i++){
            System.out.print("\n" + TabCaseTeleporter[i] + "\n");
        }
        System.out.print("\nAffichage du NbCaseTeleporter: "+ NbCaseTeleporter +"\n");
        System.out.print("\n-------------Fin affichage-------------------\n");
    }

    @Override public boolean entrerSurLaCase(Entite e) {
        for(int i=0; i<NbCaseTeleporter; i++){
            boolean TeleportationPossible=false;

            //----------Debut de Debug
                System.out.print("\nDifférent: "+ (TabCaseTeleporter[i] != this) + " avec i = "+ i +" et NbCaseTeleporter = "
                        + NbCaseTeleporter + " et taille TabCaseTeleporter =" + TabCaseTeleporter.length + "\n"); // cout pour le debug
                DebugAfficheDonneeMembre();
            //----------Fin de Debug

            if((TabCaseTeleporter[i] != this) && (TabCaseTeleporter[i].peutEtreParcouru(this.e))){ // toute le comportement d'une case Teleporteur ne s'active que si il peut être parcouru d'abord => son entité e = null
                Point pCourant = jeu.map.get(TabCaseTeleporter[i]); // récupère le point de la case passé en paramèttre
                int d = 0;
                do{
                    Point pCible = jeu.calculerPointCible(pCourant, TabDirection[d]);  // "calculerPointCible" renvoi le coordonné Point de la NOUVELLE case souhaité par rapport à la direction d
                    if (jeu.contenuDansGrille(pCible)){
                        TeleportationPossible = jeu.caseALaPosition(pCible).peutEtreParcouru(this.e);
                        if (TeleportationPossible) { // Case.Mur.peutEtreParcouru = return false ; Case.Vide.peutEtreParcouru = return Case.entitérive == null
                            e.getCase().quitterLaCase(); // les choses à faire avant de quitter l'ancienne case : mettre l'entité dans la cae=se à null
                            jeu.caseALaPosition(pCible).entrerSurLaCase(e); // les choses à faire avant de rentrer dans la nouvelle case: stocker l'entité dans la variable entité de la case
                            return true;
                        }
                    }
                    d++;
                }while ((!(TeleportationPossible)) && (d < 4));
            }
        }

        //Si le if fe toute les itération de la boucle est faux => entité e des autre transporter que this est null => on fait la comportement normale d'une case Vide
        // On sait que pour rentrer dans l'appelle de la fonction depuis le jeu, c'est que cette case peut être parcouru
        setEntite(e);
        e.setCase(this); // A voir si ça ne crée pas de problème
        return true;
    }

}
