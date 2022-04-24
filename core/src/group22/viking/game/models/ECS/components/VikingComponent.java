package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;

//have damage and attackrate passed through Constructor?
//In other words do we need setters?
public class VikingComponent implements Component {

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    private float damage = 50;
    public long scoreReward = 10;

    public float getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(float attackRate) {
        this.attackRate = attackRate;
    }

    public float getTimeSinceLastAttack() {
        return timeSinceLastAttack;
    }

    public void setTimeSinceLastAttack(float timeSinceLastAttack) {
        this.timeSinceLastAttack = timeSinceLastAttack;
    }

    //attack at a rate of once per 2 secounds
    private float attackRate = 2f;
    private float timeSinceLastAttack;

    public float getHealth() {
        return health;
    }

    private float health = 100; //set 100 as default, dunno.png

    public void DealDamage(float amount){
        health -= amount;
    }
}
