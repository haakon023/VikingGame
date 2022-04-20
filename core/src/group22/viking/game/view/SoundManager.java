package group22.viking.game.view;

import com.badlogic.gdx.Preferences;

import group22.viking.game.controller.states.State;
import group22.viking.game.models.Assets;

public class SoundManager {

    public static void buttonClickSound(Preferences preferences){
        if(preferences.getBoolean("sound_preference")){
            long soundButtonId = Assets.SOUND_BUTTON.play(1f);
            Assets.SOUND_BUTTON.setPitch(soundButtonId,2f);
            Assets.SOUND_BUTTON.setLooping(soundButtonId,false);
        }
    }

    public static void avatarSwooshSound(Preferences preferences){
        if(preferences.getBoolean("sound_preference")){
            long swooshId = Assets.SOUND_SHWOOSH.play(1f);
            Assets.SOUND_SHWOOSH.setPitch(swooshId,2f);
            Assets.SOUND_SHWOOSH.setLooping(swooshId,false);
        }
    }

    public static void errorSound(Preferences preferences){
        if(preferences.getBoolean("sound_preference")){
            long swooshId = Assets.SOUND_ERROR.play(1f);
            Assets.SOUND_ERROR.setPitch(swooshId,2f);
            Assets.SOUND_ERROR.setLooping(swooshId,false);
        }
    }

    public static void playMusic(State state, Preferences preferences){
        System.out.println("SOUND ON IS:"  +  state.getGame().getProfileCollection().getPreferences().getBoolean("sound_preference"));
        if(preferences.getBoolean("sound_preference")){
            System.out.println("playing music> if passed");
            if(state.getClass().getName().equals("group22.viking.game.controller.states.MenuState")){
                Assets.GAME_MUSIC.stop();
                Assets.LOBBY_MUSIC.stop();
                Assets.MENU_MUSIC.play();
                Assets.MENU_MUSIC.setVolume(1f);
                Assets.MENU_MUSIC.setLooping(true);
                System.out.println("playing music> loading passed");
            }else if(state.getClass().getName().equals("group22.viking.game.controller.states.LobbyState")){
                Assets.GAME_MUSIC.stop();
                Assets.MENU_MUSIC.stop();
                Assets.LOBBY_MUSIC.play();
                Assets.LOBBY_MUSIC.setVolume(1f);
                Assets.LOBBY_MUSIC.setLooping(true);
            }else if(state.getClass().getName().equals("group22.viking.game.controller.states.PlayState")){
                Assets.LOBBY_MUSIC.stop();
                Assets.MENU_MUSIC.stop();
                Assets.GAME_MUSIC.play();
                Assets.GAME_MUSIC.setVolume(1f);
                Assets.GAME_MUSIC.setLooping(true);
            }
        }
    }


}
