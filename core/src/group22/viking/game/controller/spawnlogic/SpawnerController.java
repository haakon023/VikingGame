package group22.viking.game.controller.spawnlogic;

import java.util.ArrayList;

public class SpawnerController {
    private ArrayList<Spawner> spawners = new ArrayList<Spawner>();
    private int cycle;
    private float factor;
    public SpawnerController(ArrayList<Spawner> spawners)
    {
        this.spawners = spawners;
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
        return amountOfAttackersToSpawn(time) / spawners.size();
    }
}
