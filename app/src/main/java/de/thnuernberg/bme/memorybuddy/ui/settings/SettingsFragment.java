package de.thnuernberg.bme.memorybuddy.ui.settings;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.thnuernberg.bme.memorybuddy.databinding.FragmentSettingsBinding;
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = de.thnuernberg.bme.memorybuddy.databinding.FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}