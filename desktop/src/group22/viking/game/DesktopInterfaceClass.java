package group22.viking.game;

import group22.viking.game.controller.firebase.FirebaseInterface;

public class DesktopInterfaceClass implements FirebaseInterface {

    @Override
    public void someFunction() {
        System.out.println("DESKTOP");
    }

    @Override
    public void FirstFireBaseTest() {

    }

    @Override
    public void SetOnValueChangedListener() {

    }

    @Override
    public void SetValueInDb(String target, String value) {

    }
}
