public class User {
    private String username;
    private DefenderSprite avatar;
    
    public User(username, avatar) {
        this.username = username; 
        this.avatar = avatar; 
    }

    public String getUsername() {
        return username; 
    }

    public DefenderSprite getAvatar() {
        return avatar;
    }
    
    //FR14 (modifiability?)
    public void setAvatar(DefenderSprite avatar) {
        this.avatar = avatar;
    }
}