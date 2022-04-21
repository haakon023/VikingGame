package group22.viking.game.controller.spawnlogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import group22.viking.game.controller.VikingGame;

import java.util.ArrayList;

public class SpawnerController {
    private ArrayList<Spawner> spawners = new ArrayList<Spawner>();
    private int cycle;
    private float factor;
    public SpawnerController(int spawnerAmount)
    {
        createSpawners(spawnerAmount);
        updateSpawnerPositions();
        this.cycle = 1;
        this.factor = 1;
    }

    public int amountOfAttackersToSpawn(int time)
    {
        int amount = 0;
        if (time%30 == 0)
        {
            amount = 5; //Default amount to spawn will be 5
            amount += factor*cycle;

            factor += 1.5;
            cycle++;
        }
        return amount;
    }

    public int amountOfAttackersToSpawnForEachSpawner(int time)
    {
        int amount = amountOfAttackersToSpawn(time);
        int amountPerSpawner = amount / spawners.size();
        if (amountPerSpawner == 0 && amount != 0)
        {
            return spawners.size(); //Ensures that some enemies will always spawn
        }
        return amountPerSpawner;
    }

    private void createSpawners(int amount)
    {
        Vector3 position = new Vector3(50,50,0);
        for (int i=0; i<amount; i++)
        {
            Spawner spawner = new Spawner(new Sprite(),position,new Vector2(20,20),new Vector2(0,0),0);
            spawners.add(spawner);
        }
    }

    public void updateSpawnerPositions() //Works
    {

        spawners.get(0).setPosition(new Vector3(0+150f,0+150f,0));
        spawners.get(1).setPosition(new Vector3(VikingGame.SCREEN_WIDTH-150f,0+150f,0));
        spawners.get(2).setPosition(new Vector3(VikingGame.SCREEN_WIDTH-150f,VikingGame.SCREEN_HEIGHT-150f,0));
        spawners.get(3).setPosition(new Vector3(0+150f,VikingGame.SCREEN_HEIGHT-150f,0));

        //float x = Math.round(((float)Math.random())*(VikingGame.SCREEN_WIDTH)*100f)/100f;
        //spawners.get(t).setPosition(new Vector3(x,0,0));
    }

    public ArrayList<Spawner> getSpawners() {
        return spawners;
    }

    public void setSpawners(ArrayList<Spawner> spawners) {
        this.spawners = spawners;
    }
}
