package group22.viking.game;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.FirebaseInterface;
import group22.viking.game.controller.firebase.OnGetDataListener;
import group22.viking.game.controller.firebase.OnPostDataListener;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class AndroidInterfaceClass implements FirebaseInterface {

    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;

    private final Map<FirebaseDocument, ListenerRegistration> serverListeners;

    private final boolean isOnline;

    public AndroidInterfaceClass() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);

        serverListeners = new HashMap<>();

        isOnline = reachGoogle();

        mAuth.signInAnonymously()
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "signInAnonymously:success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "signInAnonymously:failure", e);
                    }
                });
    }

    /**
     * https://codegrepr.com/question/check-for-active-internet-connection-android/
     *
     * @return boolean
     */
    public boolean reachGoogle() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            return returnVal == 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setOnValueChangedListener(String collection,
                                          FirebaseDocument document,
                                          OnGetDataListener listener) {
        // Remove old listener if existing
        if(serverListeners.containsKey(document)) {
            serverListeners.get(document).remove();
        }

        DocumentReference documentReference = db.collection(collection).document(document.getId());
        ListenerRegistration serverListener = documentReference.addSnapshotListener(
                (@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        listener.onFailure();
                        return;
                    }

                    String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, source + " data: " + snapshot.getData());
                        listener.onGetData(document.getId(), snapshot.getData());
                    } else {
                        Log.d(TAG, source + " data: null");
                        listener.onFailure(); // TODO @Sacha right? Is this a failure
                    }
                });

        serverListeners.put(document, serverListener);
    }
    
    public void removeOnValueChangedListener(FirebaseDocument document) {
        if(!serverListeners.containsKey(document)) return;
        serverListeners.get(document).remove();
        serverListeners.remove(document);
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
            .addOnSuccessListener((Void unused) -> listener.onSuccess(documentId))
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
                .addOnSuccessListener((DocumentReference documentReference) ->
                        listener.onSuccess(documentReference.getId()))
                .addOnFailureListener((@NonNull Exception e) -> {
                    Log.w(TAG, "Error loading document", e);
                    listener.onFailure();
                });
    }


    @Override
    public void get(String collection, String documentId, OnGetDataListener listener) {
        try {
            db.collection(collection)
                    .document(documentId)
                    .get()
                    .addOnSuccessListener((DocumentSnapshot documentSnapshot) ->
                            listener.onGetData(documentId, documentSnapshot.getData()));
        } catch (NullPointerException e) {
            VikingGame.LOG.log(Level.WARNING, "No document with id " + documentId);
            listener.onFailure();
        }
    }

    @Override
    public void getAll(String collection, String orderBy, int limit, OnGetDataListener listener) {
        Query query = db.collection(collection);

        if(orderBy != null && !orderBy.isEmpty()) {
            query = query.orderBy(orderBy, Query.Direction.DESCENDING);
        }

        if(limit > 0 ) {
            query = query.limit(limit);
        }

        query.get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        listener.onGetData(document.getId(), document.getData());
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                    listener.onFailure();
                }
            });
    }

    @Override
    public void removeDocument(
            String collection,
            FirebaseDocument document,
            OnPostDataListener listener)
    {
        db.collection(collection)
                .document(document.getId())
                .delete()
                .addOnSuccessListener((Void unused) -> listener.onSuccess(document.getId()))
                .addOnFailureListener((@NonNull Exception e) -> {
                    Log.w(TAG, "Error deleting document", e);
                    listener.onFailure();
                });
    }

    @Override
    public boolean isOnline() {
        return isOnline;
    }
}
