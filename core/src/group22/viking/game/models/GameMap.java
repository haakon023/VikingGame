package group22.viking.game.models;

import java.util.ArrayList;

public class GameMap {
    private Defender defender;
    private ArrayList<Attacker> attackers = new ArrayList<Attacker>();
    private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public GameMap(Defender defender) {
        this.defender = defender;
    }

    public Defender getDefender() {
        return defender;
    }

    public void setDefender(Defender defender) {
        this.defender = defender;
    }

    public ArrayList<Attacker> getAttackers() {
        return attackers;
    }

    public void setAttackers(ArrayList<Attacker> attackers) {
        this.attackers = attackers;
    }

    public void addAttacker(Attacker attacker) {
        this.attackers.add(attacker);
    }

    public void removeAttacker(Attacker attacker){
        for(int i = 0; i < attackers.size(); i++){
            if(attacker == attackers.get(i)){
                attackers.remove(i);
            }
        }
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public void addPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }

    public void removePowerUp(PowerUp pu){
        for(int i = 0; i < powerUps.size(); i++){
            if(pu == powerUps.get(i)){
                powerUps.remove(i);
            }
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void addBullet(Bullet bullets) {
        this.bullets.add(bullets);
    }

}
