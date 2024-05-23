package de.thnuernberg.bme.memorybuddy.ui.statistic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.thnuernberg.bme.memorybuddy.databinding.FragmentSettingsBinding;
import de.thnuernberg.bme.memorybuddy.databinding.FragmentStatisticBinding;
import de.thnuernberg.bme.memorybuddy.ui.settings.SettingsViewModel;

public class StatisticFragment extends Fragment {

    private FragmentStatisticBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatisticViewModel statisticViewModel =
                new ViewModelProvider(this).get(StatisticViewModel.class);

        binding = de.thnuernberg.bme.memorybuddy.databinding.FragmentStatisticBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
