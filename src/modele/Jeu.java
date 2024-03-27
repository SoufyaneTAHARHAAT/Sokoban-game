/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;


import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.Vector;


public class Jeu extends Observable {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    public Vector<CaseObjectif> TabCaseObjectif = new Vector<>();// tableau contenant les cases objectifs


    private Heros heros;

    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    private Case[][] grilleEntites = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées

    public String[] TabFichierNiveau;
    public int CompteurNiveau = 0;
    private int compteurPasHero = 0;

    public int getCompteurPasHero() {
        return compteurPasHero;
    }

    public Jeu() {
        int nombreNiveaux = 5;
        TabFichierNiveau = new String[nombreNiveaux];

        for (int i = 0; i < nombreNiveaux; i++) {
            TabFichierNiveau[i] = "Niveaux/Niveau" + i + ".txt";
        }

        initialisationNiveau(CompteurNiveau);
    }


    
    public Case[][] getGrille() {
        return grilleEntites;
    }
    /**
     * "getGrille" retourne le tableau de grille en entier*/
    
    public Heros getHeros() {
        return heros;
    }

    /**
     * "getHeros" return le Heros*/

    public void deplacerHeros(Direction d) {
        heros.avancerDirectionChoisie(d);
        setChanged();
        compteurPasHero ++;// Compteur de pas du heros qui s'incremente avec chaque deplacement
        notifyObservers();
    }
    /**
     *  deplacerHeros -> entite.avancerDirectionChoisie -> jeu.deplacerEntite(this, d);*/


    public void initialisationNiveau(int NumNiveau){
        LectureFichier tabNiveau = new LectureFichier(TabFichierNiveau[NumNiveau], SIZE_X, SIZE_Y);
        for(int x = 0; x<SIZE_X; x++){
            for(int y= 0; y<SIZE_Y; y++){
                if(tabNiveau.tab[x][y]=='M'){
                    addCase(new Mur(this), x, y);
                }
                if(tabNiveau.tab[x][y] == ' '){
                    addCase(new Vide(this), x, y);
                }
                if(tabNiveau.tab[x][y]=='H'){
                    addCase(new Vide(this), x, y); // obligatoire car quand on inserera un heros dans la grilleEntites, le construteur de Entite stocke
                    heros = new Heros(this, grilleEntites[x][y]); // aussi la case où on met l'entité => cette case ne devrait pas être null => doit exister => on doit le addCase
                }
                if(tabNiveau.tab[x][y]=='B'){
                    addCase(new Vide(this), x, y);
                    Bloc b = new Bloc(this, grilleEntites[x][y]);
                }
                if(tabNiveau.tab[x][y]=='O'){
                    CaseObjectif nouvelleCase = new CaseObjectif(this);
                    addCase(nouvelleCase, x, y);
                    TabCaseObjectif.add(nouvelleCase);
                }
                if(tabNiveau.tab[x][y]=='X'){
                    addCase(new Vide(this), x, y);
                    Bloc b = new BlocObjectif(this, grilleEntites[x][y]);
                }
            }
        }
    }


    /** "addCase" permet d'ajouter une case dans le tableau de case "grilleEntites" */
    private void addCase(Case e, int x, int y) {
        grilleEntites[x][y] = e;  // on stocke la case "e" dans la grilleEntites puis
        map.put(e, new Point(x, y)); //on associe à la case "e" la coordonnée (x,y)
    }
    

    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = true;
        
        Point pCourant = map.get(e.getCase()); // récupérer la case de l'entité de la paramettre formel
        
        Point pCible = calculerPointCible(pCourant, d);  // "calculerPointCible" renvoi le coordonné Point de la NOUVELLE case souhaité par rapport à la direction d


        if (contenuDansGrille(pCible)) {
            Entite eCible = caseALaPosition(pCible).getEntite(); // "getEntite" retourne l'entité contenu dans la case depuis laquelle on l'a appellé
            if (eCible != null) { // Si la futur case contient déjà un entité, on va essayer de le pousser
                eCible.pousser(d); // si l'entité est quelque chose et non pas un Null,  on la pousse par rapport à la direction:
            } // Bloc.pousser -> jeu.deplacerEntite => boolean: true si la case à la position pCible peutEtreParcouru
            // fonction recursive

            // si la case est libérée
            if (caseALaPosition(pCible).peutEtreParcouru()) { // Case.Mur.peutEtreParcouru = return false ; Case.Vide.peutEtreParcouru = return Case.entitérive == null
                e.getCase().quitterLaCase(); // les choses à faire avant de quitter l'ancienne case
                caseALaPosition(pCible).entrerSurLaCase(e); // les choses à faire avant de rentrer dans la nouvelle case

            } else {
                retour = false;
            }

        } else {
            retour = false;
        }

        return retour;
    }





    /**
     * "calculerPointCible" renvoi le coordonné Point de la nouvelle case souhaité*/
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;

        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;

        }

        return pCible;
    }


    
    /** "contenuDansGrille" retourne un boolean qui indique si p est une coordonnée valide pour une case */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

    /** "caseALaPosition" renvoie le case (et non pas son contenu) dont la coordonnée est passée en paramettre */
    private Case caseALaPosition(Point p) {
        Case retour = null;
        
        if (contenuDansGrille(p)) { // Verifie si la coordonnée est correcte.
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }


    //Fonction pour verifier la fin du jeu
    public boolean FinJeu(){
        for (int i=0; i< TabCaseObjectif.size(); i++){
            if(!(TabCaseObjectif.get(i).e instanceof BlocObjectif)){
                return false;
            }
        }
        compteurPasHero = -1;
        return true;
    }

}

/**
 * - Cette classe a la donnée membre "grilleEntité" qui permet de stocker
 *   toute les cases du jeux.
 * - la donnée membre "map" permet de récupérer la position d'une case
 *   à partir de sa référence
 * */