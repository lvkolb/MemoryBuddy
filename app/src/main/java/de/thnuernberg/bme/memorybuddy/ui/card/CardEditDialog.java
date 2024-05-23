package de.thnuernberg.bme.memorybuddy.ui.card;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import de.thnuernberg.bme.memorybuddy.R;

public class CardEditDialog extends DialogFragment {
    private CardFragment.CardAdapter adapter;
    private List<Card> cards;

    public CardEditDialog(CardFragment.CardAdapter adapter, List<Card> cards) {
        this.adapter = adapter;
        this.cards = cards;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_card_edit, null);

        EditText editTextTag = view.findViewById(R.id.editTextTag);
        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextDeck = view.findViewById(R.id.editTextDeck);
        EditText editTextFront = view.findViewById(R.id.editTextFront);
        EditText editTextBack = view.findViewById(R.id.editTextBack);

        builder.setView(view)
                .setTitle("Add Card")
                .setPositiveButton("Save", (dialog, which) -> {
                    // Retrieve input values
                    String tag = editTextTag.getText().toString();
                    String name = editTextName.getText().toString();
                    String deck = editTextDeck.getText().toString();
                    String frontText = editTextFront.getText().toString();
                    String backText = editTextBack.getText().toString();

                    // Pass values back to the fragment
                    assert getParentFragment() != null;
                    ((CardFragment) getParentFragment()).onCardSaved(tag, name, deck, frontText, backText);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}