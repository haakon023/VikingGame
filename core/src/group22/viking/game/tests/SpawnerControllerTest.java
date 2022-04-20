package group22.viking.game.tests;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import group22.viking.game.controller.spawnlogic.Spawner;
import group22.viking.game.controller.spawnlogic.SpawnerController;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SpawnerControllerTest {

    @Test
    public void amountOfAttackersToSpawn() {
        SpawnerController spawnerController = new SpawnerController(3);
        int expected = 6;
        int result = spawnerController.amountOfAttackersToSpawn(0);
        Assert.assertEquals(expected,result);

        expected = 0;
        result = spawnerController.amountOfAttackersToSpawn(35);
        Assert.assertEquals(expected,result);

        expected = 10;
        result = spawnerController.amountOfAttackersToSpawn(180);
        Assert.assertEquals(expected,result);

        expected = 17;
        result = spawnerController.amountOfAttackersToSpawn(360);
        Assert.assertEquals(expected,result);

    }

    @Test
    public void amountOfAttackersToSpawnForEachSpawner() {
        SpawnerController spawnerController = new SpawnerController(3);
        int expected = 2;
        int result = spawnerController.amountOfAttackersToSpawnForEachSpawner(0);
        Assert.assertEquals(expected,result);

        expected = 0;
        result = spawnerController.amountOfAttackersToSpawnForEachSpawner(35);
        Assert.assertEquals(expected,result);

        expected = 3;
        result = spawnerController.amountOfAttackersToSpawnForEachSpawner(180);
        Assert.assertEquals(expected,result);

        expected = 5;
        result = spawnerController.amountOfAttackersToSpawnForEachSpawner(360);
        Assert.assertEquals(expected,result);
    }

    @Test
    public void createSpawners()
    {
        SpawnerController spawnerController = new SpawnerController(3);
        int expected = 3;
        int result = spawnerController.getSpawners().size();
        Assert.assertEquals(expected, result);
    }
}