package de.thnuernberg.bme.memorybuddy.ui.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import de.thnuernberg.bme.memorybuddy.R;
import de.thnuernberg.bme.memorybuddy.databinding.FragmentCardBinding;

public class CardFragment extends Fragment {

    private FragmentCardBinding binding;
    private CardViewModel cardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText editTextFront = binding.editTextFront;
        final EditText editTextBack = binding.editTextBack;
        Button buttonAdd = binding.buttonAdd;

        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);

        buttonAdd.setOnClickListener(view -> {
            String frontText = editTextFront.getText().toString().trim();
            String backText = editTextBack.getText().toString().trim();

            if (!frontText.isEmpty() && !backText.isEmpty()) {
                Card newCard = new Card(frontText, backText);
                cardViewModel.addCard(newCard);
                editTextFront.setText("");
                editTextBack.setText("");
                Toast.makeText(getContext(), "Card added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = binding.recyclerViewCards;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CardAdapter adapter = new CardAdapter();
        recyclerView.setAdapter(adapter);

        cardViewModel.getCards().observe(getViewLifecycleOwner(), adapter::setCards);

        return root;
    }

    // Adapter for RecyclerView
    private static class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

        private List<Card> cards = new ArrayList<>();

        public void setCards(List<Card> cards) {
            this.cards = cards;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new CardViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            Card card = cards.get(position);
            holder.textViewFront.setText(card.getFront());
            holder.textViewBack.setText(card.getBack());
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

        static class CardViewHolder extends RecyclerView.ViewHolder {
            TextView textViewFront;
            TextView textViewBack;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewFront = itemView.findViewById(R.id.textViewFront);
                textViewBack = itemView.findViewById(R.id.textViewBack);
            }
        }
    }
}
