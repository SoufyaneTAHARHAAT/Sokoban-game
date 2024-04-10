package modele;

import java.awt.Point;

public class CaseRail extends Vide{

    Direction [] TabDirection;
    Point [] TabCoordDirection;
    public CaseRail(Jeu _jeu){
        super(_jeu);
        TabDirection = new Direction[4];
        TabCoordDirection = new Point[4];
        TabDirection[0]= Direction.haut;
        TabDirection[1]= Direction.droite;
        TabDirection[2]= Direction.gauche;
        TabDirection[3]= Direction.bas;
        TabCoordDirection[0]= new Point(0, -1);
        TabCoordDirection[1]= new Point(1, 0);
        TabCoordDirection[2]= new Point(-1, 0);
        TabCoordDirection[3]= new Point(0, 1);
    }
    @Override public boolean peutEtreParcouru(Entite EQuiVeutParcourir, Direction DirectionSource) {
        if(EQuiVeutParcourir instanceof Heros){
            //System.out.print("\n le heros veut rentrer \n");
            System.out.print("\n--------------\n");
            return e==null;
        }
        Direction [] TabDirectionPossible = new Direction[4];
        int nbrDirectionPossible = 0;
        Point pcourant =jeu.map.get(this);
        for(int i=0; i<4; i++){ // Recherche de case rail autour pour savoir les direction qu'on peut prendre depuis this
            Point pcible = jeu.calculerPointCible(pcourant, TabDirection[i]);
            if(jeu.contenuDansGrille(pcible)){
                if(jeu.getGrille()[pcible.x][pcible.y] instanceof CaseRail){
                    TabDirectionPossible[nbrDirectionPossible]= TabDirection[i];
                    nbrDirectionPossible++;
                }
            }
        }

        //System.out.print("\n" + nbrDirectionPossible + "\n");

        if(nbrDirectionPossible==0){ // pas d'autre case rail autour
            System.out.print("\n--------------\n");
            return e==null;
        }

        else if(nbrDirectionPossible==1){ // il est en contacte avec un seul case rail: c'est un point d'entrée de l'asemble de rail
            for(int i=0; i<4; i++){
                if(TabDirectionPossible[0]== TabDirection[i]){
                    TabDirectionPossible[nbrDirectionPossible]= TabDirection[3-i];
                    nbrDirectionPossible++;
                }
            }
        } else { // entouré d'au moins deux autres case rail => pas un point d'entré => il faut inverser les direction que this accepte car sa
            // gauche = la droite l'entité qui veut entrer dans this. Rappelle : on est dans la fonction peutEtreParcouru dont la direction qu'on
            //recevera ici ne sera pas par rapport au point de vue de this mais de l'entité qui veut entrer dans this
            for(int i=0; i<nbrDirectionPossible; i++){
                for(int j=0; j<4; j++){
                    if(TabDirectionPossible[i]== TabDirection[j]){
                        System.out.print("\nAvant: "+ TabDirectionPossible[i]);
                        TabDirectionPossible[i]= TabDirection[3-j];
                        System.out.print(" Après: "+ TabDirectionPossible[i] + "\n");
                        break; // si non il va continuer l'iteration du j => remodifier la modification qu'on vient de faire
                    }
                }
            }
        }

        for (int i=0; i<nbrDirectionPossible; i++){
            if(DirectionSource==TabDirectionPossible[i]){
                System.out.print("\n--------------\n");
                return e==null;
            }
        }
        System.out.print("\n--------------\n");
        return false;
    }

    @Override public boolean PeutEtreQuitter(Direction DirectionProchainCase){
        if(e instanceof Heros){
            System.out.print("\n--------------\n");
            return true;
        }
        Direction [] TabDirectionPossible = new Direction[4];
        int nbrDirectionPossible = 0;
        Point pcourant =jeu.map.get(this);
        for(int i=0; i<4; i++){ // Recherche de case rail autour pour savoir les direction qu'on peut prendre depuis this
            Point pcible = jeu.calculerPointCible(pcourant, TabDirection[i]);
            if(jeu.contenuDansGrille(pcible)){
                if(jeu.getGrille()[pcible.x][pcible.y] instanceof CaseRail){
                    TabDirectionPossible[nbrDirectionPossible]= TabDirection[i];
                    nbrDirectionPossible++;
                }
            }
        }


        if(nbrDirectionPossible==0){ // pas d'autre case rail autour
            System.out.print("\n--------------\n");
            return true;
        }

        else if(nbrDirectionPossible==1){ // il est en contacte avec un seul case rail: c'est un point d'entrée de l'asemble de rail
            for(int i=0; i<4; i++){
                if(TabDirectionPossible[0]== TabDirection[i]){
                    TabDirectionPossible[nbrDirectionPossible]= TabDirection[3-i];
                    nbrDirectionPossible++;
                    break;
                }
            }
        }

        for (int i=0; i<nbrDirectionPossible; i++){
            if(DirectionProchainCase==TabDirectionPossible[i]){
                System.out.print("\n--------------\n");
                return true;
            }
        }
        System.out.print("\n--------------\n");
        return false;
    }
}