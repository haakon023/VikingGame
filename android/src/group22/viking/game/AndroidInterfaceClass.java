package group22.viking.game;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import group22.viking.game.controller.firebase.FirebaseInterface;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class AndroidInterfaceClass implements FirebaseInterface {

    FirebaseFirestore db;
    final String GAMES_COLLECTION_PATH = "games_test";

    public AndroidInterfaceClass() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void createGame(Integer p1_health, Boolean p1_wins, Integer p2_health, Boolean p2_wins, Boolean playing) {
        // Create a new user with a first and last name
        Map<String, Object> game = new HashMap<>();
        game.put("player1_life", p1_health);
        game.put("player1_wins", p1_wins);
        game.put("player2_life", p2_health);
        game.put("player2_wins", p2_wins);
        game.put("playing", playing);

        // Add a new document with a generated ID
        db.collection(GAMES_COLLECTION_PATH)
            .add(game)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });
    }

    @Override
    public void getGame() {
        db.collection(GAMES_COLLECTION_PATH)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
    }

    @Override
    public void setOnValueChangedGameListener(String game_id) {
        final DocumentReference docRef = db.collection(GAMES_COLLECTION_PATH).document(game_id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, source + " data: " + snapshot.getData());
                } else {
                    Log.d(TAG, source + " data: null");
                }
            }
        });
    }



}
