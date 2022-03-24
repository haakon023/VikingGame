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
    public void setOnValueChangedListener(String collection, String document_id) {
        DocumentReference documentReference = db.collection(collection).document(document_id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
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

    @Override
    public void addDocument(String collection, String document_id, Map<String, Object> values) {
        // TODO make sure, that document does not exist already!!!
        if (document_id == null || document_id.isEmpty() || document_id.trim().isEmpty()) {
            this.addDocumentWithGeneratedId(collection, values);
            return;
        }
        db.collection(collection)
            .document(document_id)
            .set(values)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + document_id);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });
    }

    public void addDocumentWithGeneratedId(String collection, Map<String, Object> values) {
        db.collection(collection)
            .add(values)
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
    public void update(String collection, String document_id, Map<String, Object> values) {

    }

    @Override
    public void get(String collection, String document_id) {
        db.collection(collection)
            .document(document_id)
            .get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    // TODO
                }
            });
    }

    public void getAll(String collection) {
        db.collection(collection)
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
}
