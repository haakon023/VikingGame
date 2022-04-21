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
        this.spawners = createSpawners(spawnerAmount);
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

    private ArrayList<Spawner> createSpawners(int amount)
    {
        Vector3 position = new Vector3(50,50,0);
        Spawner spawner = new Spawner(new Sprite(),position,new Vector2(20,20),new Vector2(0,0),0);
        ArrayList<Spawner> spawners = new ArrayList<Spawner>();
        for (int i=0; i<amount; i++)
        {
            spawners.add(spawner);
        }
        return spawners;
    }

    private void updateSpawnerPositions()
    {
        for(int i=0; i < spawners.size();i++)
        {
            if (i == 0) spawners.get(i).setPosition(new Vector3(0,0,0));
            else if (i == 1) spawners.get(i).setPosition(new Vector3(VikingGame.SCREEN_WIDTH,0,0));
            else if (i == 2) spawners.get(i).setPosition(new Vector3(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,0));
            else if (i == 3) spawners.get(i).setPosition(new Vector3(0,VikingGame.SCREEN_HEIGHT,0));
            else{

                float x = Math.round(((float)Math.random())*(VikingGame.SCREEN_WIDTH)*100f)/100f;
                spawners.get(i).setPosition(new Vector3(x,0,0));
            }
        }
    }

    public ArrayList<Spawner> getSpawners() {
        return spawners;
    }

    public void setSpawners(ArrayList<Spawner> spawners) {
        this.spawners = spawners;
    }
}
