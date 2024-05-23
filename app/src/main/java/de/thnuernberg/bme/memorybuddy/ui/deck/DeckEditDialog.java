package de.thnuernberg.bme.memorybuddy.ui.deck;

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
import de.thnuernberg.bme.memorybuddy.ui.card.Card;
import de.thnuernberg.bme.memorybuddy.ui.deck.Deck;
import de.thnuernberg.bme.memorybuddy.ui.deck.DeckFragment;

public class DeckEditDialog extends DialogFragment {
    private DeckFragment.DeckAdapter adapter;
    private List<Deck> Decks;
    private List<Card> cards;

    public DeckEditDialog(DeckFragment.DeckAdapter adapter, List<Deck> Decks) {
        this.adapter = adapter;
        this.Decks = Decks;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_deck_edit, null);


        TextInputLayout editTextName = view.findViewById(R.id.editTextName);

        builder.setView(view)
                .setTitle("Add Deck")
                .setMessage("Please fill in your Decks information.")
                .setPositiveButton("Save", (dialog, which) -> {
                    // Retrieve input values
                    String name = editTextName.getEditText().getText().toString();

                    // Pass values back to the fragment
                    assert getParentFragment() != null;
                    int cardCount = 1;
                    ((DeckFragment) getParentFragment()).onDeckSaved(name,cards,cardCount);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}
