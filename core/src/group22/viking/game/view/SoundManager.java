package group22.viking.game.view;

import com.badlogic.gdx.Preferences;

import group22.viking.game.controller.states.State;
import group22.viking.game.models.Assets;

public class SoundManager {

    public static void buttonClickSound(Preferences preferences){
        if(preferences.getBoolean("sound_preference")){
            long soundButtonId = Assets.SOUNDBUTTON.play(1f);
            Assets.SOUNDBUTTON.setPitch(soundButtonId,2f);
            Assets.SOUNDBUTTON.setLooping(soundButtonId,false);
        }
    }

    public static void avatarSwooshSound(Preferences preferences){
        if(preferences.getBoolean("sound_preference")){
            long swooshId = Assets.SOUNDSHWOOSH.play(1f);
            Assets.SOUNDSHWOOSH.setPitch(swooshId,2f);
            Assets.SOUNDSHWOOSH.setLooping(swooshId,false);
        }
    }

    public static void errorSound(Preferences preferences){
        if(preferences.getBoolean("sound_preference")){
            long swooshId = Assets.SOUNDERROR.play(1f);
            Assets.SOUNDERROR.setPitch(swooshId,2f);
            Assets.SOUNDERROR.setLooping(swooshId,false);
        }
    }

    public static void playMusic(State state, Preferences preferences){
        //state.getGame().getPreferences().putBoolean(ProfileCollection.PREFERENCES_SOUND_KEY, true);
        //state.getGame().getPreferences().flush();
        System.out.println("SOUND ON IS:"  +  state.getGame().getProfileCollection().getPreferences().getBoolean("sound_preference"));
        if(preferences.getBoolean("sound_preference")){
            System.out.println("playing music> if passed");
            if(state.getClass().getName().equals("group22.viking.game.controller.states.MenuState")){
                Assets.GAMEMUSIC.stop();
                Assets.LOBBYMUSIC.stop();
                Assets.MENUMUSIC.play();
                Assets.MENUMUSIC.setVolume(1f);
                Assets.MENUMUSIC.setLooping(true);
                System.out.println("playing music> loading passed");
            }else if(state.getClass().getName().equals("group22.viking.game.controller.states.LobbyState")){
                Assets.GAMEMUSIC.stop();
                Assets.MENUMUSIC.stop();
                Assets.LOBBYMUSIC.play();
                Assets.LOBBYMUSIC.setVolume(1f);
                Assets.LOBBYMUSIC.setLooping(true);
            }else if(state.getClass().getName().equals("group22.viking.game.controller.states.PlayState")){
                Assets.LOBBYMUSIC.stop();
                Assets.MENUMUSIC.stop();
                Assets.GAMEMUSIC.play();
                Assets.GAMEMUSIC.setVolume(1f);
                Assets.GAMEMUSIC.setLooping(true);
            }
        }
    }


}
