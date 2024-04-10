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

    public FinJeu testfinjeu;

    public Vector<CaseObjectif> TabCaseObjectif = new Vector<>();// tableau contenant les cases objectifs


    private Heros heros;

    public HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    public HashMap<Case, Point> getMap() {
        return map;
    }
    private Case[][] grilleEntites = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées


    private int compteurPasHero;

    public Niveau niveau;

    public int getCompteurPasHero() {
        return compteurPasHero;
    }

    public Jeu() {
        niveau = new Niveau();

        compteurPasHero = 0;

        testfinjeu = new FinJeu(this);

        initialisationNiveau();
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


    public void initialisationNiveau(){
        compteurPasHero=0;
        testfinjeu.Fin= false;
        CaseTeleporter.NbCaseTeleporter=0;
        LectureFichier tabNiveau = new LectureFichier(niveau.CheminNiveauSuivant(), SIZE_X, SIZE_Y);
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
                    Bloc b = new BlocObjectif(this, grilleEntites[x][y]); // chaque Entite stocque la case qui la contiene
                }
                if(tabNiveau.tab[x][y]=='G'){
                    addCase(new Glace(this), x, y);
                }
                if(tabNiveau.tab[x][y]=='V'){
                    addCase(new CaseVitre(this), x, y);
                }
                if(tabNiveau.tab[x][y]=='R'){
                    addCase(new CaseRail(this), x, y);
                }
                if(tabNiveau.tab[x][y]=='T') {
                    if(CaseTeleporter.NbCaseTeleporter<CaseTeleporter.TabCaseTeleporter.length){
                        CaseTeleporter VarTampon = new CaseTeleporter(this);
                        addCase(VarTampon, x, y);
                        VarTampon.DebugAfficheDonneeMembre(); // Appelle AVANT la nouvelle insertion !
                        CaseTeleporter.TabCaseTeleporter[CaseTeleporter.NbCaseTeleporter] = VarTampon;
                        CaseTeleporter.NbCaseTeleporter++;
                    }else {
                        addCase(new Vide(this), x, y);
                    }
                }
                if(tabNiveau.tab[x][y]=='A'){
                    if(CaseAimant.NbCaseAimant<CaseAimant.TabCaseAimant.length){
                        CaseAimant Tampon = new CaseAimant(this);
                        Tampon.IndiceLocalDirection = CaseAimant.IndiceStaticDirection;
                        CaseAimant.IndiceStaticDirection = (CaseAimant.IndiceStaticDirection+1)%4; // 4 = nbr de direction dans TabDirection
                        addCase(Tampon, x, y);
                        Tampon.InisialiserPointCaseABloquer();
                        CaseAimant.TabCaseAimant[CaseAimant.NbCaseAimant]= Tampon;
                        CaseAimant.NbCaseAimant++;
                    } else {
                        addCase(new Vide(this), x, y);
                    }
                }
                if(tabNiveau.tab[x][y]=='C'){
                    addCase(new CaseBoutonAimant(this), x, y);
                }
            }
        }
    }


    /** "addCase" permet d'ajouter une case dans le tableau de case "grilleEntites" */
    public void addCase(Case e, int x, int y) {
        grilleEntites[x][y] = e;  // on stocke la case "e" dans la grilleEntites puis
        map.put(e, new Point(x, y)); //on associe à la case "e" la coordonnée (x,y)
    }
    

    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        if(testfinjeu.Fin){
            return false; // on n'execute rien et on sort directement
        }
        boolean retour = true;
        
        Point pCourant = map.get(e.getCase()); // récupérer la case de l'entité de la paramettre formel
        
        Point pCible = calculerPointCible(pCourant, d);  // "calculerPointCible" renvoi le coordonné Point de la NOUVELLE case souhaité par rapport à la direction d


        if (contenuDansGrille(pCible)) {
            Entite eCible = caseALaPosition(pCible).getEntite(); // "getEntite" retourne l'entité contenu dans la case cible
            if (eCible != null) { // Si la futur case contient déjà un entité, on va essayer de le pousser
                eCible.pousser(d); // si l'entité est quelque chose et non pas un Null,  on la pousse par rapport à la direction:
            } // Bloc.pousser -> jeu.deplacerEntite => boolean: true si la case à la position pCible peutEtreParcouru
            // fonction recursive

            // si la case est libérée => le pousser() dans le if d'avant a réussi
            if (caseALaPosition(pCible).peutEtreParcouru(e, d) && (e.getCase().PeutEtreQuitter(d))) { // Case.Mur.peutEtreParcouru = return false ; Case.Vide.peutEtreParcouru = return Case.entitérive == null
                e.getCase().quitterLaCase(); // les choses à faire avant de quitter l'ancienne case : mettre l'entité dans la cae=se à null
                caseALaPosition(pCible).entrerSurLaCase(e, d); // les choses à faire avant de rentrer dans la nouvelle case: stocker l'entité dans la variable entité de la case

            }
            // Vérifier si la case cible est une Case Glace
            if (caseALaPosition(pCible) instanceof Glace) {
                // Si c'est le cas, on effectue un deuxième déplacement dans la même direction
                Point pCibleSecondaire = calculerPointCible(pCible, d);
                if (contenuDansGrille(pCibleSecondaire) && caseALaPosition(pCibleSecondaire).peutEtreParcouru(e, d)) {
                    // Effectuer le deuxième déplacement
                    e.getCase().quitterLaCase();
                    caseALaPosition(pCibleSecondaire).entrerSurLaCase(e, d);
                } else {
                    retour = false; // Le deuxième déplacement n'est pas possible
                }
            } else { // c'est le elese pour if (caseALaPosition(pCible).peutEtreParcouru())
                retour = false;
            }

        } else {
            retour = false;
        }

        return retour;
    }





    /**
     * "calculerPointCible" renvoi le coordonné Point de la nouvelle case souhaité*/
    protected Point calculerPointCible(Point pCourant, Direction d) {
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
    protected boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

    /** "caseALaPosition" renvoie le case (et non pas son contenu) dont la coordonnée est passée en paramettre */
    protected Case caseALaPosition(Point p) {
        Case retour = null;
        
        if (contenuDansGrille(p)) { // Verifie si la coordonnée est correcte.
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }


}

/**
 * - Cette classe a la donnée membre "grilleEntité" qui permet de stocker
 *   toute les cases du jeux.
 * - la donnée membre "map" permet de récupérer la position d'une case
 *   à partir de sa référence
 * */