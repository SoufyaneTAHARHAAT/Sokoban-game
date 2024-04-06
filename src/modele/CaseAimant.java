package modele;

import java.awt.Point;

public class CaseAimant extends Mur{
    public static int IndiceStaticDirection= 0; // modifier à chaque ajout de case aimant puis on le stocke dans
    public int IndiceLocalDirection; // IndiceLocalDirection pour que la modification soit propre à chaque instance de case Aimant
    public Direction [] TabDirection;
    public Point PointCaseABloquer;

    public static CaseAimant [] TabCaseAimant = new CaseAimant[10];
    public static int NbCaseAimant = 0;
    public CaseAimant(Jeu _jeu){
        super(_jeu);
        TabDirection = new Direction[4];
        TabDirection[0]= Direction.haut;
        TabDirection[1]= Direction.droite;
        TabDirection[2]= Direction.bas;
        TabDirection[3]= Direction.gauche;
    }

    public void InisialiserPointCaseABloquer(){ // son contenu ne peut pas être dans le constructeur car quand on le crée, il n'est pas tout de suite dans grilleEntite => calculerPointCible => jeu.map.get(this) = null
        Point PointThis = jeu.map.get(this); // donc calculerPointCible(PointThis... => PointThis.x ... = erreur
        PointCaseABloquer = jeu.calculerPointCible(PointThis, TabDirection[IndiceLocalDirection]); // "calculerPointCible" renvoi le coordonné Point de la nouvelle case souhaité
    }

    public void IncrementerIndiceLocaleMAJPointABloquer(){
        IndiceLocalDirection = (IndiceLocalDirection+1)%TabDirection.length;
        Point PointThis = jeu.map.get(this);
        PointCaseABloquer = jeu.calculerPointCible(PointThis, TabDirection[IndiceLocalDirection]); // "calculerPointCible" renvoi le coordonné Point de la nouvelle case souhaité
    }


    /**Cette fonction sera utiliser dans la fonction pousser dans toute les autres case qui hérite de la case Bloc et des case Bloc lui même donc la Bloc qui l'utilisera existe forcement*/
    public static boolean CaseActuelPeutPousserAilleur(Case c, Jeu jeu){ // peutEtreParcouru() est un bolean qui permet ou pas d'appeller les fonctions quitterLaCase puis entrerSurLaCase dans Jeu.deplacerentité
        if((!(c.e instanceof Heros)) && (!(c instanceof CaseBoutonAimant)) && (!(c instanceof CaseAimant))){
            Point pointC = jeu.map.get(c); //Rappelle: une fonction ne peut pas être static sinon elle ne peut pas utiliser la donnée membre jeu car pas propre à un instance (qui stocke les donné et
            for(int i=0; i<NbCaseAimant; i++){ // methode non static) mais à la classe (qui ne peut manipuler que les methodes et données membres static).
                if((TabCaseAimant[i].PointCaseABloquer.x == pointC.x) && (TabCaseAimant[i].PointCaseABloquer.y == pointC.y)){   //(TabCaseAimant[i].PointCaseABloquer == pointC) ne marche pas car pas objet de type primitive => comparaison des adresse memoires.
                    return false;
                }
            }
        }
        return true;
    }
}

/***
 * Lecture de fichier txt = En colonne de haut en bas puis de gauche vers la droite
 */

