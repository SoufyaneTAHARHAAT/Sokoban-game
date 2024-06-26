/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 * Héros du jeu
 */
public class Heros extends Entite
{
    public Heros(Jeu _jeu, Case c) {
        super(_jeu, c);
    }

    private Direction direction;

    @Override
    public Direction getDirection() {
        return direction;
    }

    // Méthode pour définir la direction du héros
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}

/**
 * - Cette classe hérite de Entité => son constructeur doit appeller
 *   celle de Entite pour lui passer le jeu ainsi que la case qui va
 *   contenir ce Heros
 * */


