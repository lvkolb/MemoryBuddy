package de.thnuernberg.bme.memorybuddy.ui.card;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

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

    public void onCardSaved(String name, String frontText, String backText, String deck, String tag) {
        Card newCard = new Card(name,frontText,backText,deck,tag);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        onCardSaved("dummy card","How much fun is android?","10/10","AndroidFunfacts","fun");

        ExtendedFloatingActionButton buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> showCardEditDialog());

        return view;
    }

    // Adapter for RecyclerView
    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

        private List<Card> cardList;
        private int cardCount = 0;

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
            holder.textViewDeck.setText("Deck: "+card.getDeck());
            holder.textViewTag.setText("Tag: #"+card.getTag());
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
        public int getCardCount() {
            return cardCount;
        }

        public void addCard(Card card) {
            cardCount++;
            Resources res = getResources();
            String numberOfCards = String.format(res.getString(R.string.cards_update), cardCount);
            Toast.makeText(getContext(),numberOfCards, Toast.LENGTH_LONG).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
