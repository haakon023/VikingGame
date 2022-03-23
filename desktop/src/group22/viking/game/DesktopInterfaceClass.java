package group22.viking.game;

import java.util.Map;

import group22.viking.game.controller.firebase.FirebaseInterface;

public class DesktopInterfaceClass implements FirebaseInterface {


    @Override
    public void setOnValueChangedListener(String collection, String document_id) {

    }

    @Override
    public void addDocument(String collection, String document_id, Map<String, Object> values) {

    }

    @Override
    public void update(String collection, String document_id, Map<String, Object> values) {

    }

    @Override
    public void get(String collection, String document_id) {

    }

    @Override
    public void getAll(String collection) {

    }
}
