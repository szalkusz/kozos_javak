package com.github.kozosjavak.asteroidmining.core;

import com.github.kozosjavak.asteroidmining.core.materials.InventoryIsFullException;
import com.github.kozosjavak.asteroidmining.core.materials.Material;
import com.github.kozosjavak.asteroidmining.core.materials.NotEnoughMaterialException;
import com.github.kozosjavak.asteroidmining.core.materials.types.Coal;
import com.github.kozosjavak.asteroidmining.core.materials.types.Iron;
import com.github.kozosjavak.asteroidmining.core.materials.types.Uranium;
import com.github.kozosjavak.asteroidmining.core.materials.types.Waterice;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AsteroidTest {

    private Location testLocation1;
    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game(100, 100);
        testLocation1 = new Location(game, 1.0, 3.0);
    }

    @Test
    public void should_get_back_set_spaceships() {
        Location testLocation = new Location(game, 2.2, 2.5);
        Asteroid testAsteroid = new Asteroid(testLocation, 2, null);
        Settler testSpaceship = new Settler(testAsteroid); // addSpaceship()

        assertEquals(testSpaceship, testAsteroid.getResidence().get(0));

        Robot testSpaceship2 = new Robot(testAsteroid); // addSpaceship()
        testAsteroid.removeSpaceship(testSpaceship);

        assertEquals(testSpaceship2, testAsteroid.getResidence().get(0));
    }

    @Test(expected = AsteroidIsNotMineable.class)
    public void shouldnt_be_able_to_mine_if_not_fully_drilled() throws AsteroidIsNotMineable, AsteroidAlreadyMinedException {
        Location testLocation = new Location(game, 2.2, 2.5);
        Material testMaterial = new Iron();
        Asteroid testAsteroid = new Asteroid(testLocation, 1, testMaterial);

        Material mined = testAsteroid.mine();
    }

    @Test
    public void should_mine_only_if_fully_drilled() throws Exception {
        Location testLocation = new Location(game, 2.2, 2.5);
        Material testMaterial = new Iron();
        Asteroid testAsteroid = new Asteroid(testLocation, 1, testMaterial);

        testAsteroid.drill();
        Material mined = testAsteroid.mine();

        assertEquals(mined, testMaterial);
    }

    @Test
    public void spaceships_should_experience_solarstorm() {
        Asteroid testAsteroid = new Asteroid(new Location(game, 2.2, 2.5), 1, null);
        Settler testSpaceship = new Settler(testAsteroid);

        testAsteroid.experienceSolarStorm();

        assertEquals(new ArrayList<Spaceship>(), testAsteroid.getResidence()); // empty residence list
    }

    @Test(expected = SurfaceThicknessIsZeroException.class)
    public void surfacethickness_should_be_return_the_proper_value() throws Exception {
        Asteroid testAsteroid = new Asteroid(new Location(game, 2.2, 2.5), 6, null);

        testAsteroid.drill(); // 6->5

        assertEquals(5, testAsteroid.getSurfaceThickness());

        testAsteroid.drill(); // 5->4
        testAsteroid.drill(); // 4->3
        testAsteroid.drill(); // 3->2
        testAsteroid.drill(); // 2->1
        testAsteroid.drill(); // 1->0
        testAsteroid.drill(); // throws SurfaceThicknessIsZeroException
    }

    @Test
    public void explosion_should_clear_location_also() throws Exception {
        Location testLocation = new Location(game, 2.2, 2.5);
        Teleport testTeleport = new Teleport();
        testLocation.setTeleport(testTeleport);
        Asteroid testAsteroid = new Asteroid(testLocation, 1, null);

        testAsteroid.explode();

        assertEquals(null, testLocation.getTeleport());
    }

    @Test
    public void should_be_able_to_insert_and_remove_materials() throws AsteroidNotMinedException, InventoryIsFullException, NotEnoughMaterialException {
        Location testLocation = new Location(game, 2.2, 2.5);
        Material testMaterial1 = new Iron();
        Material testMaterial2 = new Coal();
        Asteroid testAsteroid = new Asteroid(testLocation, 0, null);

        testAsteroid.insertMaterial(testMaterial1);
        testAsteroid.insertMaterial(testMaterial2);
        List<Material> comparatorList = new ArrayList<>();
        comparatorList.add(testMaterial1);
        comparatorList.add(testMaterial2);
        assertEquals(comparatorList, testAsteroid.getMaterials());

        testAsteroid.removeMaterial(testMaterial1);
        comparatorList.remove(testMaterial1);
        assertEquals(comparatorList, testAsteroid.getMaterials());
    }

    @Test(expected = AsteroidNotMinedException.class)
    public void should_not_be_able_to_insert_material() throws AsteroidNotMinedException, InventoryIsFullException {
        Location testLocation = new Location(game, 2.2, 2.5);
        Material testMaterial = new Iron();
        Asteroid testAsteroid = new Asteroid(testLocation, 5, null);

        testAsteroid.insertMaterial(testMaterial);
    }

    /**
     * buildBase is not tested here
     */

    /**
     * experienceSolarStrom is an empty implementation, can't be tested
     */
    @Ignore
    @Test(expected = AsteroidIsNotMineable.class)
    public void waterice_should_disappear() throws Exception {
        Location testLocation = new Location(game, 2.2, 2.5);
        Material testMaterial = new Waterice();
        Asteroid testAsteroid = new Asteroid(testLocation, 0, testMaterial);

        testAsteroid.experienceExtremeHeat();

        testAsteroid.mine();
    }

    @Test
    public void uranium_should_explode() throws Exception {
        Location testLocation = new Location(game, 2.2, 2.5);
        Material testMaterial = new Uranium();
        Asteroid testAsteroid = new Asteroid(testLocation, 0, testMaterial);

        testAsteroid.experienceExtremeHeat();

        assertEquals(testAsteroid, testLocation.getCelestialBody());

        testAsteroid.experienceExtremeHeat();
        testAsteroid.experienceExtremeHeat();

        assertEquals(null, testLocation.getCelestialBody());
    }
}
