package group22.viking.game.ECS.components;

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

    private float damage = 10;

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
}
