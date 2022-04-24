package group22.viking.game.tests;

import com.badlogic.gdx.math.Vector3;
import group22.viking.game.controller.VikingGame;

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

    @Test
    public void updateSpawnerPositions()
    {
        SpawnerController spawnerController = new SpawnerController(4);
        SpawnerController spawnerController2 = new SpawnerController(4);
        ArrayList<Spawner> spawners = spawnerController.getSpawners();

        for(int i=0;i < spawnerController.getSpawners().size();i++){
            if (i == 0) spawners.get(i).setPosition(new Vector3(0,0,0));
            else if (i == 1) spawners.get(i).setPosition(new Vector3(VikingGame.SCREEN_WIDTH,0,0));
            else if (i == 2) spawners.get(i).setPosition(new Vector3(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,0));
            else if (i == 3) spawners.get(i).setPosition(new Vector3(0,VikingGame.SCREEN_HEIGHT,0));
            else{

                float x = Math.round(((float)Math.random())*(VikingGame.SCREEN_WIDTH)*100f)/100f;
                spawners.get(i).setPosition(new Vector3(x,0,0));
            }
        }
        for (int i=0; i < spawnerController.getSpawners().size();i++)
        {
                Assert.assertEquals(spawnerController.getSpawners().get(i).getPosition(),spawnerController2.getSpawners().get(i).getPosition());
        }
    }
}