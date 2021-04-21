package com.github.kozosjavak.asteroidmining.core.commands;

import com.github.kozosjavak.asteroidmining.core.Asteroid;
import com.github.kozosjavak.asteroidmining.core.Game;
import com.github.kozosjavak.asteroidmining.core.Settler;

public class CreateSettlerCommand implements Command {
    private final int asteroidID;

    public CreateSettlerCommand(int asteroidID) {
        this.asteroidID = asteroidID;
    }

    @Override
    public void apply(Game game) {
        if (game.getObjectFromID(asteroidID) instanceof Asteroid asteroid) {
            Settler settler = new Settler(asteroid);
            game.putInIdList(settler);
        } else {
            System.out.println("Invalid Asteroid ID!\n");
        }
    }
}
