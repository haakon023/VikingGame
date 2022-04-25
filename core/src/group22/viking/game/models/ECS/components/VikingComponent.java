package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;

//have damage and attackrate passed through Constructor?
//In other words do we need setters?
public class VikingComponent implements Component {

    public static final float DEFAULT_ATTACK_RATE = 2F;  //attack at a rate of once per 2 seconds
    public static final float DEFAULT_SPEED = 8F;
    public static final float DEFAULT_DAMAGE = 50;
    public static final float DEFAULT_HEALTH = 200;
    public static final long DEFAULT_SCORE_REWARD = 10;
    // default
    private float health = DEFAULT_HEALTH;
    private float damage = DEFAULT_DAMAGE;
    private float attackRate = DEFAULT_ATTACK_RATE;
    private long scoreReward = DEFAULT_SCORE_REWARD;
    public float speed = DEFAULT_SPEED;

    private float timeSinceLastAttack = 0;

    public float getSpeed() {
        return speed;
    }

    public VikingComponent init() {
        this.health = DEFAULT_HEALTH;
        this.damage = DEFAULT_DAMAGE;
        this.attackRate = DEFAULT_ATTACK_RATE;
        this.scoreReward = DEFAULT_SCORE_REWARD;
        this.speed = DEFAULT_SPEED;
        this.timeSinceLastAttack = 0;
        return this;
    }

    public VikingComponent init(float health, float damage, float attackRate, float speed, long scoreReward) {
        this.health = health;
        this.damage = damage;
        this.attackRate = attackRate;
        this.scoreReward = scoreReward;
        this.speed = speed;
        this.timeSinceLastAttack = 0;
        return this;
    }

    public void dealDamage(float amount){
        health -= amount;
    }

    public float getAttackRate() {
        return attackRate;
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

    public long getScoreReward() {
        return scoreReward;
    }

    public float getDamage() {
        return damage;
    }
}
