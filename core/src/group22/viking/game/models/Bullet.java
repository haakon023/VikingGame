package group22.viking.game.models;

public class Bullet extends Entity{
    private int damage;
    private int speed;


    public Bullet(int damage, int speed) {
        this.damage = damage;
        this.speed = speed;
    }


    public int getDamage() {
        return damage;
    }
   
    public int getSpeed() {
        return speed;
    }    
}
