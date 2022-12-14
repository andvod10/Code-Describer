package com.research.codedescriber.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.research.codedescriber.R;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.idFragmentRetrieve);

        Button retrieveBtn = root.findViewById(R.id.idBtnRetrieve);
        retrieveBtn.setOnClickListener(new OnClickListenerRetrieveEntry(root, new OnLongClickListenerEntry(root)));
        Button createBtn = root.findViewById(R.id.idBtnCreate);
        createBtn.setOnClickListener(new OnClickListenerCreateEntry(root));

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
