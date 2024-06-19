package de.thnuernberg.bme.memorybuddy.ui.card;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import de.thnuernberg.bme.memorybuddy.R;

public class CardEditDialog extends DialogFragment {
    private List<Card> cards;

    public CardEditDialog(List<Card> cards) {

        this.cards = cards;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_card_edit, null);


        TextInputLayout editTextCategory = view.findViewById(R.id.editTextCategory);
        TextInputLayout editTextFront = view.findViewById(R.id.editTextFront);
        TextInputLayout editTextBack = view.findViewById(R.id.editTextBack);
        TextInputLayout editTextDeck = view.findViewById(R.id.editTextDeck);


        builder.setView(view)
                .setTitle("Add Card")
                .setMessage("Please fill in your cards information.")
                .setPositiveButton("Save", (dialog, which) -> {
                    // Retrieve input values
                    String category = editTextCategory.getEditText().getText().toString();
                    String frontText = editTextFront.getEditText().getText().toString();
                    String backText = editTextBack.getEditText().getText().toString();


                    String deck = editTextDeck.getEditText().getText().toString();

                    // Pass values back to the fragment
                    assert getParentFragment() != null;
                    ((CardFragment) getParentFragment()).onCardSaved(category,frontText,backText,deck);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}