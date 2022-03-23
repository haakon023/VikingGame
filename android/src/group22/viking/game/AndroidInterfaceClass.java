package group22.viking.game;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import group22.viking.game.controller.firebase.FirebaseInterface;

import static android.content.ContentValues.TAG;

public class AndroidInterfaceClass implements FirebaseInterface {

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myRef;

    public AndroidInterfaceClass() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    @Override
    public void someFunction() {
        System.out.println("ANDROID");
    }

    @Override
    public void FirstFireBaseTest() {
        if(myRef != null) {
            myRef.setValue("Hello, World!");
        }
        else {
            System.out.println("Database reference not set, could not write to DB");
        }
    }

    @Override
    public void SetOnValueChangedListener() {
        // myRef is pointing to "message"
        // we can use the EventListener for getting updates on player health
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void SetValueInDb(String target, String value) {
        myRef = database.getReference(target);
        myRef.setValue(value);
    }
}
