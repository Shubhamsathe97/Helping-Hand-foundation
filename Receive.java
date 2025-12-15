package com.example.helpinghandfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Receive extends AppCompatActivity {

    // Declare variables
    EditText name, number;
    Button rcvBtn;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        // Initialize Firebase Auth and Firestore
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // Bind views
        name = findViewById(R.id.editTextName_R);
        number = findViewById(R.id.editTextPhoneNumber_R);
        rcvBtn = findViewById(R.id.buttonRecv);

        // Set up button click listener
        rcvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String fullname = name.getText().toString().trim();
                String phone = number.getText().toString().trim();
                String type = "Receiver";

                // Input validation
                if (TextUtils.isEmpty(fullname)) {
                    name.setError("Name is Required.");
                    return;
                }
                if (phone.length() < 10) {
                    number.setError("Phone Number Must be at least 10 characters.");
                    return;
                }

                // Check if user is logged in
                if (fAuth.getCurrentUser() != null) {
                    userID = fAuth.getCurrentUser().getUid();
                    CollectionReference collectionReference = fStore.collection("user receiver");

                    // Prepare data to be saved
                    Map<String, Object> user = new HashMap<>();
                    user.put("timestamp", FieldValue.serverTimestamp());
                    user.put("name", fullname);
                    user.put("phone", phone);
                    user.put("userid", userID);
                    user.put("type", type);

                    // Add data to Firestore
                    collectionReference.add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Request submitted successfully!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Data successfully added to Firestore!");

                                    // Redirect to donorRequest activity
                                    Intent intent = new Intent(Receive.this, donorRequest.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error submitting request!", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
