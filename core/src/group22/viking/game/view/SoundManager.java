package group22.viking.game.view;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.State;
import group22.viking.game.models.Assets;

public class SoundManager {

    private static String PREFERENCE_KEY_SOUND = "sound_preference";

    public static void buttonClickSound(){
        if(isSoundOn()){
            long soundButtonId = Assets.SOUND_BUTTON.play(1f);
            Assets.SOUND_BUTTON.setPitch(soundButtonId,1f);
            Assets.SOUND_BUTTON.setLooping(soundButtonId,false);
        }
    }

    public static void avatarSwooshSound(){
        if(isSoundOn()){
            long swooshId = Assets.SOUND_SHWOOSH.play(1f);
            Assets.SOUND_SHWOOSH.setPitch(swooshId,1f);
            Assets.SOUND_SHWOOSH.setLooping(swooshId,false);
        }
    }

    public static void errorSound(){
        if(isSoundOn()){
            long swooshId = Assets.SOUND_ERROR.play(1f);
            Assets.SOUND_ERROR.setPitch(swooshId,1f);
            Assets.SOUND_ERROR.setLooping(swooshId,false);
        }
    }

    public static void mumbleSound(){
        if(isSoundOn()){
            long mumbleId = Assets.SOUND_MUMBLE.play(1f);
            Assets.SOUND_MUMBLE.setPitch(mumbleId,1f);
            Assets.SOUND_MUMBLE.setLooping(mumbleId,false);
        }
    }

    public static void playMusic(State state){
        if(isSoundOn()){
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
            }else if(
                    state.getClass().getName().equals("group22.viking.game.controller.states.OnlinePlayState") ||
                    state.getClass().getName().equals("group22.viking.game.controller.states.OfflinePlayState")
            ){
                Assets.LOBBY_MUSIC.stop();
                Assets.MENU_MUSIC.stop();
                Assets.GAME_MUSIC.play();
                Assets.GAME_MUSIC.setVolume(1f);
                Assets.GAME_MUSIC.setLooping(true);
            }
        }
    }

    public static boolean isSoundOn() {
        return VikingGame.getPreferences().getBoolean(PREFERENCE_KEY_SOUND);
    }

}
