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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import de.thnuernberg.bme.memorybuddy.R;
import de.thnuernberg.bme.memorybuddy.databinding.FragmentCardBinding;

public class CardFragment extends Fragment {

    private FragmentCardBinding binding;
    private CardViewModel cardViewModel;
    private CardAdapter adapter;
    private List<Card> cards;


    private void showCardEditDialog() {
        CardEditDialog dialog = new CardEditDialog(adapter, cards);
        dialog.show(getChildFragmentManager(), "CardEditDialog");
    }

    public void onCardSaved(String tag, String name, String deck, String frontText, String backText) {
        Card newCard = new Card(tag, name, deck, frontText, backText);
        adapter.addCard(newCard);
    }

    private void addCardToList(Card card) {
        cards.add(card);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        cards = new ArrayList<>();
        adapter = new CardAdapter(cards);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ExtendedFloatingActionButton buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> showCardEditDialog());

        return view;
    }

    // Adapter for RecyclerView
    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

        private List<Card> cardList;

        public CardAdapter(List<Card> cardList) {
            this.cardList = cardList;
        }

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new CardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            Card card = cardList.get(position);
            holder.textViewName.setText(card.getName());
            holder.textViewFront.setText(card.getFront());
            holder.textViewBack.setText(card.getBack());
            holder.textViewDeck.setText(card.getDeck());
            holder.textViewTag.setText(card.getTag());
            holder.btnDelete.setOnClickListener(view -> {
                int deletedPosition = holder.getAdapterPosition();
                removeCard(deletedPosition);
                // Perform additional logic to delete the card from the database or storage
            });
            // Bind other card data to views if needed
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        public void addCard(Card card) {
            cardList.add(card);
            notifyItemInserted(cardList.size() - 1);
        }
        public void removeCard(int position) {
            cardList.remove(position);
            notifyItemRemoved(position);
        }

        class CardViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName;
            TextView textViewFront;
            TextView textViewBack;
            TextView textViewTag;
            TextView textViewDeck;
            Button btnDelete;
            // Other views if needed

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewFront = itemView.findViewById(R.id.textViewFront);
                textViewBack = itemView.findViewById(R.id.textViewBack);
                textViewDeck = itemView.findViewById(R.id.textViewDeck);
                textViewTag = itemView.findViewById(R.id.textViewTag);
                btnDelete = itemView.findViewById(R.id.btnDelete);
                // Initialize other views if needed
            }
        }
    }
}
