package group22.viking.game;

import android.util.Log;

import com.google.android.gms.tasks.Task;

import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import group22.viking.game.controller.firebase.FirebaseCollection;
import group22.viking.game.controller.firebase.FirebaseInterface;
import group22.viking.game.controller.firebase.OnGetDataListener;
import group22.viking.game.controller.firebase.OnPostDataListener;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import java.util.Map;

public class AndroidInterfaceClass implements FirebaseInterface {

    private FirebaseFirestore db;

    public AndroidInterfaceClass() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void setOnValueChangedListener(String collection,
                                          String document_id,
                                          OnGetDataListener listener) {

        DocumentReference documentReference = db.collection(collection).document(document_id);
        documentReference.addSnapshotListener((@Nullable DocumentSnapshot snapshot,
                                               @Nullable FirebaseFirestoreException e) -> {
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
        });
    }

    @Override
    public void addDocument(String collection,
                            String document_id,
                            Map<String, Object> values,
                            OnPostDataListener listener) {
        // TODO make sure, that document does not exist already!!!
        if (document_id == null || document_id.isEmpty() || document_id.trim().isEmpty()) {
            //this.addDocumentWithGeneratedId(collection, values); //TODO
            return;
        }
        db.collection(collection)
            .document(document_id)
            .set(values)
            .addOnSuccessListener((Void unused) -> {
                Log.d(TAG, "DocumentSnapshot added with ID: " + document_id);
            })
            .addOnFailureListener((@NonNull Exception e) -> {
                Log.w(TAG, "Error adding document", e);
            });
    }

    @Override
    public void addDocumentWithGeneratedId(String collection,
                                           Map<String, Object> values,
                                           OnPostDataListener listener) {
        db.collection(collection)
                .add(values)
                .addOnSuccessListener((DocumentReference) -> {
                    listener.onSuccess(DocumentReference.getId());
                })
                .addOnFailureListener((@NonNull Exception e) -> {
                    Log.w(TAG, "Error loading document", e);
                    listener.onFailure();
                });
    }

    @Override
    public void update(String collection,
                       String document_id,
                       Map<String, Object> values,
                       OnPostDataListener listener) {

    }

    @Override
    public void get(String collection, String documentId, OnGetDataListener listener) {
        db.collection(collection)
            .document(documentId)
            .get()
            .addOnSuccessListener((DocumentSnapshot documentSnapshot) -> {
                listener.onSuccess(documentId, documentSnapshot.getData());
            });
    }

    @Override
    public void getAll(String collection, OnGetDataListener listener) {
        db.collection(collection)
            .get()
            .addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            });
    }
}
