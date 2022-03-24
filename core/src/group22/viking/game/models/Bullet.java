package group22.viking.game.models;

public class Bullet {
    private Integer damage;
    private Integer speed;


    public Bullet(Integer damage, Integer speed) {
        this.damage = damage;
        this.speed = speed;
    }


    public Integer getDamage() {
        return damage;
    }

    /* Not sure if we need to set it other than when its created
    public void setDamage(Integer damage) {
        this.damage = damage;
    }
    */
   
    public Integer getSpeed() {
        return speed;
    }

    /* Not sure if we need to set it other than when its created
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    */
    

    
    
}
