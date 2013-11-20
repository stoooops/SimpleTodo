/**
 * Created by Cory Gabrielsen on 11/16/13.
 */
package com.example.thetodoapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thetodoapp.R;
/**
 * A fragment for the TO-DO view
 */
public class ListsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_lists, container, false);
        final TextView textView = (TextView) rootView.findViewById(R.id.temp_text);
        textView.setText("Lists");
        Toast.makeText(getActivity(), "TODO", Toast.LENGTH_SHORT).show();
        return rootView;
    }
}