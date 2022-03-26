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
                                          String documentId,
                                          OnGetDataListener listener) {

        DocumentReference documentReference = db.collection(collection).document(documentId);
        documentReference.addSnapshotListener((@Nullable DocumentSnapshot snapshot,
                                               @Nullable FirebaseFirestoreException e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                listener.onFailure();
                return;
            }

            String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                ? "Local" : "Server";

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, source + " data: " + snapshot.getData());
                listener.onSuccess(documentId, snapshot.getData());
            } else {
                Log.d(TAG, source + " data: null");
                listener.onFailure(); // TODO @Sacha right? Is this a failure
            }
        });
    }

    @Override
    public void addOrUpdateDocument(String collection,
                            String documentId,
                            Map<String, Object> values,
                            OnPostDataListener listener) {

        if (documentId == null || documentId.isEmpty() || documentId.trim().isEmpty()) {
            this.addDocumentWithGeneratedId(collection, values, listener);
            return;
        }
        db.collection(collection)
            .document(documentId)
            .set(values)
            .addOnSuccessListener((Void unused) -> {
                listener.onSuccess(documentId);
            })
            .addOnFailureListener((@NonNull Exception e) -> {
                Log.w(TAG, "Error adding/editing document", e);
                listener.onFailure();
            });
    }

    @Override
    public void addDocumentWithGeneratedId(String collection,
                                           Map<String, Object> values,
                                           OnPostDataListener listener) {
        db.collection(collection)
                .add(values)
                .addOnSuccessListener((DocumentReference documentReference) -> {
                    listener.onSuccess(documentReference.getId());
                })
                .addOnFailureListener((@NonNull Exception e) -> {
                    Log.w(TAG, "Error loading document", e);
                    listener.onFailure();
                });
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
                        listener.onSuccess(document.getId(), document.getData()); // TODO check how to handle multiple documents
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                    listener.onFailure();
                }
            });
    }
}
