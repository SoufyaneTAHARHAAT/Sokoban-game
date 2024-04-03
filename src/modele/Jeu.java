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

    private HashMap<Case, Point> map = new HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    public HashMap<Case, Point> getMap() {
        return map;
    }

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

    public Heros getHeros() {
        return heros;
    }

    public void deplacerHeros(Direction d) {
        heros.avancerDirectionChoisie(d);
        setChanged();
        compteurPasHero++;// Compteur de pas du heros qui s'incremente avec chaque deplacement
        notifyObservers();
    }

    public void initialisationNiveau(int NumNiveau) {
        LectureFichier tabNiveau = new LectureFichier(TabFichierNiveau[NumNiveau], SIZE_X, SIZE_Y);
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (tabNiveau.tab[x][y] == 'M') {
                    addCase(new Mur(this), x, y);
                }
                if (tabNiveau.tab[x][y] == ' ') {
                    addCase(new Vide(this), x, y);
                }
                if (tabNiveau.tab[x][y] == 'H') {
                    addCase(new Vide(this), x, y); // obligatoire car quand on inserera un heros dans la grilleEntites, le construteur de Entite stocke
                    heros = new Heros(this, grilleEntites[x][y]); // aussi la case où on met l'entité => cette case ne devrait pas être null => doit exister => on doit le addCase
                }
                if (tabNiveau.tab[x][y] == 'B') {
                    addCase(new Vide(this), x, y);
                    Bloc b = new Bloc(this, grilleEntites[x][y]);
                }
                if (tabNiveau.tab[x][y] == 'O') {
                    CaseObjectif nouvelleCase = new CaseObjectif(this);
                    addCase(nouvelleCase, x, y);
                    TabCaseObjectif.add(nouvelleCase);
                }
                if (tabNiveau.tab[x][y] == 'X') {
                    addCase(new Vide(this), x, y);
                    Bloc b = new BlocObjectif(this, grilleEntites[x][y]);
                }
                if (tabNiveau.tab[x][y] == 'G') {
                    addCase(new Glace(this), x, y);
                }
            }
        }
    }

    private void addCase(Case e, int x, int y) {
        grilleEntites[x][y] = e;  // on stocke la case "e" dans la grilleEntites puis
        map.put(e, new Point(x, y)); //on associe à la case "e" la coordonnée (x,y)
    }

    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = true;

        Point pCourant = map.get(e.getCase()); // récupérer la case de l'entité de la paramettre formel

        Point pCible = calculerPointCible(pCourant, d);  // "calculerPointCible" renvoi le coordonné Point de la NOUVELLE case souhaité par rapport à la direction d

        if (contenuDansGrille(pCible)) {
            Entite eCible = caseALaPosition(pCible).getEntite();
            if (eCible != null) {
                eCible.pousser(d);
            }

            if (caseALaPosition(pCible).peutEtreParcouru()) {
                e.getCase().quitterLaCase();
                caseALaPosition(pCible).entrerSurLaCase(e);

                // Vérifier si la case cible est une Case Glace
                if (caseALaPosition(pCible) instanceof Glace) {
                    // Si c'est le cas, on effectue un deuxième déplacement dans la même direction
                    Point pCibleSecondaire = calculerPointCible(pCible, d);
                    if (contenuDansGrille(pCibleSecondaire) && caseALaPosition(pCibleSecondaire).peutEtreParcouru()) {
                        // Effectuer le deuxième déplacement
                        e.getCase().quitterLaCase();
                        caseALaPosition(pCibleSecondaire).entrerSurLaCase(e);
                    } else {
                        retour = false; // Le deuxième déplacement n'est pas possible
                    }
                }
            } else {
                retour = false;
            }
        } else {
            retour = false;
        }

        return retour;
    }

    protected Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;

        switch (d) {
            case haut:
                pCible = new Point(pCourant.x, pCourant.y - 1);
                break;
            case bas:
                pCible = new Point(pCourant.x, pCourant.y + 1);
                break;
            case gauche:
                pCible = new Point(pCourant.x - 1, pCourant.y);
                break;
            case droite:
                pCible = new Point(pCourant.x + 1, pCourant.y);
                break;

        }

        return pCible;
    }

    protected boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

    protected Case caseALaPosition(Point p) {
        Case retour = null;

        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }

        return retour;
    }

    //Fonction pour verifier la fin du jeu
    public boolean FinJeu() {
        for (CaseObjectif caseObjectif : TabCaseObjectif) {
            if (!(caseObjectif.e instanceof BlocObjectif)) {
                return false;
            }
        }
        compteurPasHero = -1;
        return true;
    }

    public void setFinJeu(boolean finJeu) {
        // Implémenter la logique pour modifier finJeu
    }

}
