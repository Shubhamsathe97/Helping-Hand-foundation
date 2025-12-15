package com.example.helpinghandfoundation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class CharityFragment extends Fragment {

    Button ngo1, ngo2, ngo3, ngo4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charity, container, false);

        ngo1 = rootView.findViewById(R.id.ngo1);
        ngo2 = rootView.findViewById(R.id.ngo2);
        ngo3 = rootView.findViewById(R.id.ngo3);
        ngo4 = rootView.findViewById(R.id.ngo4);

        // NGO 1
        ngo1.setOnClickListener(view -> openWebLink("https://www.akshayapatra.org"));

        // NGO 2
        ngo2.setOnClickListener(view -> openWebLink("https://www.goonj.org"));

        // NGO 3
        ngo3.setOnClickListener(view -> openWebLink("https://www.smilefoundationindia.org"));

        // NGO 4
        ngo4.setOnClickListener(view -> openWebLink("https://www.giveindia.org"));

        return rootView;
    }

    /**
     * Opens a web link in the default browser or notifies the user if no browser is available.
     */
    private void openWebLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Check if an app exists to handle the intent
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Notify the user that no application is available
            Toast.makeText(getContext(), "No application available to open the link. Please install a web browser.", Toast.LENGTH_SHORT).show();
        }
    }
}
