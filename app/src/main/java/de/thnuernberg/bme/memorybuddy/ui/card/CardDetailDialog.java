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

public class CardDetailDialog extends DialogFragment {

    private Card card;

    public CardDetailDialog( Card card) {

        this.card = card;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_card_detail, null);


        TextInputLayout editTextCategory = view.findViewById(R.id.editTextCategory);
        editTextCategory.setHint(card.getCategory());
        editTextCategory.setEnabled(false);
        editTextCategory.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE);

        TextInputLayout editTextFront = view.findViewById(R.id.editTextFront);
        editTextFront.setHint(card.getFront());
        editTextFront.setEnabled(false);
        editTextFront.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE);

        TextInputLayout editTextBack = view.findViewById(R.id.editTextBack);
        editTextBack.setHint(card.getBack());
        editTextBack.setEnabled(false);
        editTextBack.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE);

        TextInputLayout editTextDeck = view.findViewById(R.id.editTextDeck);
        editTextDeck.setHint(card.getDeck());
        editTextDeck.setEnabled(false);
        editTextDeck.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE);

        Slider editRatingSlider = view.findViewById(R.id.sliderRating);
        editRatingSlider.setValue(card.getRating());
        editRatingSlider.setEnabled(false);

        MaterialTextView textRecommended = view.findViewById(R.id.textRecommended);
        int recommendationValue = card.getRecommendation();
        textRecommended.setText(String.valueOf(recommendationValue));

        MaterialTextView textReviewCount = view.findViewById(R.id.textReviewCount);
        int reviewCountValue = card.getReviewCount();
        textReviewCount.setText(String.valueOf(reviewCountValue));

        Button btnMakeEdit = view.findViewById(R.id.btnMakeEdit);

        btnMakeEdit.setOnClickListener(v -> {
            if(editTextBack.isEnabled()){
                editTextCategory.setEnabled(false);
                editTextFront.setEnabled(false);
                editTextBack.setEnabled(false);
                editTextDeck.setEnabled(false);
                editRatingSlider.setEnabled(false);
            }
            else {
                editTextCategory.setEnabled(true);
                editTextFront.setEnabled(true);
                editTextBack.setEnabled(true);
                editTextDeck.setEnabled(true);
                editRatingSlider.setEnabled(true);
            }
        }
        );



        builder.setView(view)
                .setTitle("Karten Details")

                .setPositiveButton("Update", (dialog, which) -> {
                    // Retrieve input values
                    String category;
                    if(editTextCategory.getEditText().getText().toString().isEmpty())
                    {
                         category = card.getCategory();
                    }
                    else {
                         category = editTextCategory.getEditText().getText().toString();
                    }

                    String frontText;
                    if(editTextFront.getEditText().getText().toString().isEmpty())
                    {
                        frontText = card.getFront();
                    }
                    else {
                        frontText = editTextFront.getEditText().getText().toString();
                    }


                    String backText;
                    if(editTextBack.getEditText().getText().toString().isEmpty())
                    {
                        backText = card.getBack();
                    }
                    else {
                        backText = editTextBack.getEditText().getText().toString();
                    }

                    String deck;
                    if(editTextDeck.getEditText().getText().toString().isEmpty())
                    {
                        deck = card.getDeck();
                    }
                    else {
                        deck = editTextDeck.getEditText().getText().toString();
                    }
                    int rating = (int) editRatingSlider.getValue();

                    int id = card.getID();

                    Fragment parentFragment = getParentFragment();
                    if (parentFragment instanceof FragmentContainer) {
                        ((FragmentContainer) parentFragment).onCardUpdated(
                                card.getID(), card.getCategory(), card.getFront(), card.getBack(),
                                card.getDeck(), rating, card.getRecommendation(), card.getReviewCount());
                    } else {
                        Log.e("CardDetailDialog", "Parent fragment does not implement FragmentContainer interface");
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}