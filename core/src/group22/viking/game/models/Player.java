package group22.viking.game.models;

public class Player {
    private String name;
    private int avatarId;
    private int health;
    private boolean isLocal;

    public Player(String name, int avatarId) {
        this.name = name;
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }
    
    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    
    

}
