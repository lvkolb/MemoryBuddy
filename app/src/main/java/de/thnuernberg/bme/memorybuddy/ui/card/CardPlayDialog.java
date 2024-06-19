package de.thnuernberg.bme.memorybuddy.ui.card;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import de.thnuernberg.bme.memorybuddy.R;
import de.thnuernberg.bme.memorybuddy.ui.FragmentContainer;

public class CardPlayDialog extends DialogFragment {

    private Card card;

    public CardPlayDialog( Card card) {

        this.card = card;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_card_play, null);

        MaterialTextView textAnswer = view.findViewById(R.id.textAnswer);
        textAnswer.setText(card.getBack());
        textAnswer.setVisibility(View.INVISIBLE);

        MaterialTextView textRating = view.findViewById(R.id.textRating);
        textRating.setVisibility(View.INVISIBLE);

        Slider editRatingSlider = view.findViewById(R.id.sliderRating);
        editRatingSlider.setValue(card.getRating());
        editRatingSlider.setVisibility(View.INVISIBLE);

        Button btnGiveAnswer = view.findViewById(R.id.btnGiveAnswer);

        btnGiveAnswer.setOnClickListener(v -> {
                    textAnswer.setVisibility(View.VISIBLE);
                    editRatingSlider.setVisibility(View.VISIBLE);
                    textRating.setVisibility(View.VISIBLE);
                }
        );

        builder.setView(view)
                .setTitle(card.getFront())
                .setPositiveButton("Done", (dialog, which) -> {
                    // Retrieve input values

                    int rating = (int) editRatingSlider.getValue();
                    // Pass values back to the fragment
                    Fragment parentFragment = getParentFragment();
                    if (parentFragment instanceof FragmentContainer) {
                        ((FragmentContainer) parentFragment).onCardPlayed(
                                card.getID(), card.getCategory(), card.getFront(), card.getBack(),
                                card.getDeck(), rating, card.getRecommendation(), card.getReviewCount());
                    } else {
                        Log.e("CardPlayDialog", "Parent fragment does not implement FragmentContainer interface");
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}