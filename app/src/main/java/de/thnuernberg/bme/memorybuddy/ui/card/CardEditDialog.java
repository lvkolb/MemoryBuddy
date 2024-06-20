package de.thnuernberg.bme.memorybuddy.ui.card;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import de.thnuernberg.bme.memorybuddy.R;
import de.thnuernberg.bme.memorybuddy.ui.deck.Deck;
import de.thnuernberg.bme.memorybuddy.ui.deck.DeckCreateDialog;

public class CardEditDialog extends DialogFragment {
    private List<Card> cards;
    private List<Deck> deckList;

    public CardEditDialog(List<Card> cards, List<Deck> deckList) {

        this.cards = cards;
        this.deckList = deckList;
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
        Spinner spinnerDeck = view.findViewById(R.id.spinnerDeck);

        ArrayAdapter<Deck> spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, deckList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeck.setAdapter(spinnerAdapter);
        spinnerAdapter.add(new Deck("Create New Deck", null, 0));
        spinnerDeck.setAdapter(spinnerAdapter);

        spinnerDeck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Deck selectedDeck = (Deck) parent.getItemAtPosition(position);
                if (selectedDeck.getName().equals("Create New Deck")) {
                    // Handle creating a new deck
                    showCreateDeckDialog();
                } else {
                    // Handle selecting an existing deck
                    // You can also pass the selectedDeck to other methods or store it for later use
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case where no deck is selected
            }
        });

        builder.setView(view)
                .setTitle("Add Card")
                .setMessage("Please fill in your cards information.")
                .setPositiveButton("Save", (dialog, which) -> {
                    // Retrieve input values
                    String category = editTextCategory.getEditText().getText().toString();
                    String frontText = editTextFront.getEditText().getText().toString();
                    String backText = editTextBack.getEditText().getText().toString();
                    Deck selectedDeck = (Deck) spinnerDeck.getSelectedItem();

                    // Pass values back to the fragment
                    assert getParentFragment() != null;
                    ((CardFragment) getParentFragment()).onCardSaved(category,frontText,backText,selectedDeck.getId());
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
    private void showCreateDeckDialog() {
        // Implement logic to show a dialog for creating a new deck
        // You can use another DialogFragment or a custom dialog here
        // Example:
        DeckCreateDialog createDeckDialog = new DeckCreateDialog();
        createDeckDialog.show(getParentFragmentManager(), "CreateDeckDialog");
    }
}