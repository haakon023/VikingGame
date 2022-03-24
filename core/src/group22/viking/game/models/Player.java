package group22.viking.game.models;

public class Player {
    private String name;
    private Integer avatarId;
    private Integer health;
    private boolean isLocal;

    public Player(String name, Integer avatarId) {
        this.name = name;
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    /* Not sure if it is necessary to be able to change you name.
    public void setName(String name) {
        this.name = name;
    }
    */
    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    
    

}
