package de.thnuernberg.bme.memorybuddy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.thnuernberg.bme.memorybuddy.R;
import de.thnuernberg.bme.memorybuddy.ui.card.Card;


public class HomeFragment extends Fragment {

    private de.thnuernberg.bme.memorybuddy.databinding.FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = de.thnuernberg.bme.memorybuddy.databinding.FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dummyRecent("dummy card","How much fun is android?","10/10","AndroidFunfacts","fun");
        dummyRecent("dummy card","How much fun is android?","10/10","AndroidFunfacts","fun");
        dummyRecent("dummy card","How much fun is android?","10/10","AndroidFunfacts","fun");

        dummyFavorite("dummy card","How much fun is android?","10/10","AndroidFunfacts","fun");
        dummyFavorite("dummy card","How much fun is android?","10/10","AndroidFunfacts","fun");
        dummyFavorite("dummy card","How much fun is android?","10/10","AndroidFunfacts","fun");

        return root;
    }
    public void dummyRecent(String name, String frontText, String backText, String deck, String tag) {
        // Create a new Card object
        Card newCard = new Card(name, frontText, backText, deck, tag);

        // Inflate the card layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cardView = inflater.inflate(R.layout.item_card, null, false);

        // Find and set the TextViews with card details
        TextView cardName = cardView.findViewById(R.id.textViewName);
        TextView cardFrontText = cardView.findViewById(R.id.textViewFront);
        TextView cardBackText = cardView.findViewById(R.id.textViewBack);
        TextView cardDeck = cardView.findViewById(R.id.textViewDeck);
        TextView cardTag = cardView.findViewById(R.id.textViewTag);

        cardName.setText(newCard.getName());
        cardFrontText.setText(newCard.getFront());
        cardBackText.setText(newCard.getBack());
        cardDeck.setText(newCard.getDeck());
        cardTag.setText(newCard.getTag());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 16, 16, 16);
        cardView.setLayoutParams(layoutParams);
        // Add the card view to a parent view in the fragment's layout
        LinearLayout cardContainer1 = binding.getRoot().findViewById(R.id.card_container1);
        cardContainer1.addView(cardView);

    }
    public void dummyFavorite(String name, String frontText, String backText, String deck, String tag) {
        // Create a new Card object
        Card newCard = new Card(name, frontText, backText, deck, tag);

        // Inflate the card layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cardView2 = inflater.inflate(R.layout.item_card, null, false);

        // Find and set the TextViews with card details
        TextView cardName = cardView2.findViewById(R.id.textViewName);
        TextView cardFrontText = cardView2.findViewById(R.id.textViewFront);
        TextView cardBackText = cardView2.findViewById(R.id.textViewBack);
        TextView cardDeck = cardView2.findViewById(R.id.textViewDeck);
        TextView cardTag = cardView2.findViewById(R.id.textViewTag);

        cardName.setText(newCard.getName());
        cardFrontText.setText(newCard.getFront());
        cardBackText.setText(newCard.getBack());
        cardDeck.setText(newCard.getDeck());
        cardTag.setText(newCard.getTag());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 16, 16, 16); // Adjust the margin values as needed
        cardView2.setLayoutParams(layoutParams);
        // Add the card view to a parent view in the fragment's layout
        LinearLayout cardContainer2 = binding.getRoot().findViewById(R.id.card_container2);
        cardContainer2.addView(cardView2);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}