package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;

//have damage and attackrate passed through Constructor?
//In other words do we need setters?
public class VikingComponent implements Component {

    // default
    private float health = 200;
    private float damage = 50;
    private float attackRate = 2f;              //attack at a rate of once per 2 seconds
    public long scoreReward = 10;
    public float speed = 8;

    private float timeSinceLastAttack;

    public float getSpeed() {
        return speed;
    }

    public VikingComponent setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public void DealDamage(float amount){
        health -= amount;
    }

    public float getAttackRate() {
        return attackRate;
    }

    public VikingComponent setAttackRate(float attackRate) {
        this.attackRate = attackRate;
        return this;
    }

    public float getTimeSinceLastAttack() {
        return timeSinceLastAttack;
    }

    public void setTimeSinceLastAttack(float timeSinceLastAttack) {
        this.timeSinceLastAttack = timeSinceLastAttack;
    }

    public float getHealth() {
        return health;
    }

    public VikingComponent setHealth(float health) {
        this.health = health;
        return this;
    }

    public long getScoreReward() {
        return scoreReward;
    }

    public VikingComponent setScoreReward(long scoreReward) {
        this.scoreReward = scoreReward;
        return this;
    }

    public float getDamage() {
        return damage;
    }

    public VikingComponent setDamage(float damage) {
        this.damage = damage;
        return this;
    }
}
