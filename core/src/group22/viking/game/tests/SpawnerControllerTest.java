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
        Vector3 position = new Vector3(50,50,0);
        Spawner spawner = new Spawner(new Sprite(),position,new Vector2(20,20),new Vector2(0,0),0);
        ArrayList<Spawner> spawners = new ArrayList<Spawner>();
        for (int i=0; i<3; i++)
        {
            spawners.add(spawner);
        }
        SpawnerController spawnerController = new SpawnerController(spawners);
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
        Vector3 position = new Vector3(50,50,0);
        Spawner spawner = new Spawner(new Sprite(),position,new Vector2(20,20),new Vector2(0,0),0);
        ArrayList<Spawner> spawners = new ArrayList<Spawner>();
        for (int i=0; i<3; i++)
        {
            spawners.add(spawner);
        }
        SpawnerController spawnerController = new SpawnerController(spawners);
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
}