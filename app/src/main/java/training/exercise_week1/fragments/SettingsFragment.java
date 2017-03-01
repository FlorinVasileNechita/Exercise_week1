package training.exercise_week1.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import training.exercise_week1.R;
import training.exercise_week1.SomeFunctions;

/**
 * Created by florinnechita on 28/02/17.
 */

public class SettingsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private SomeFunctions sf = new SomeFunctions();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        final EditText firstNameEditText = (EditText) view.findViewById(R.id.firstNameEditText);
        final EditText lastNameEditText = (EditText) view.findViewById(R.id.lastNameEditText);

        sharedPreferences = getActivity().getSharedPreferences(SettingsFragment.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            firstNameEditText.setText(sharedPreferences.getString("firstName", ""));
            lastNameEditText.setText(sharedPreferences.getString("lastName", ""));
        }

        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sharedPreferencesEditor = getActivity().getSharedPreferences(SettingsFragment.class.getName(), Context.MODE_PRIVATE).edit();
                sharedPreferencesEditor.putString("firstName", firstNameEditText.getText().toString());
                sharedPreferencesEditor.putString("lastName", lastNameEditText.getText().toString());
                sharedPreferencesEditor.apply();
                sf.showToastMessage(v.getContext(), "Changes were saved!", true);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
