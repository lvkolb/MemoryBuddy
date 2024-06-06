package de.thnuernberg.bme.memorybuddy.ui.card;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import de.thnuernberg.bme.memorybuddy.ui.card.CardFragment.CardAdapter.CardViewHolder;

import de.thnuernberg.bme.memorybuddy.R;
import de.thnuernberg.bme.memorybuddy.database.CardDatabase;
import de.thnuernberg.bme.memorybuddy.ui.SharedViewModel;

public class CardFragment extends Fragment {

    private CardAdapter adapter;
    private List<Card> cards;
    private CardDatabase cardDatabase;
    private RecyclerView recyclerView;

    private SharedViewModel sharedViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCards);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        cardDatabase = sharedViewModel.getCardDatabase();
        //sharedViewModel.getCards().observe(getViewLifecycleOwner(), cards -> adapter.updateCards(cards));

        adapter = new CardAdapter(this);
        recyclerView.setAdapter(adapter);
        ExtendedFloatingActionButton buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> showCardEditDialog());

        sharedViewModel.getCards().observe(getViewLifecycleOwner(), cardDatabase -> {
            if (cardDatabase != null) {
                // Retrieve the list of cards from the database
                LiveData<List<Card>> cardsLiveData = sharedViewModel.getCards();
                cardsLiveData.observe(getViewLifecycleOwner(), cards -> {
                    // Update the RecyclerView adapter with the new list of cards
                    adapter.setData(cards);
                    // Notify the adapter that the data set has changed
                    adapter.notifyDataSetChanged();
                });
            }
        });

        return view;
    }

    private void showCardEditDialog() {
        CardEditDialog dialog = new CardEditDialog(adapter, cards);
        dialog.show(getChildFragmentManager(), "CardEditDialog");
    }

    public void onCardSaved(String name, String frontText, String backText, String deck, String tag) {
        Card newCard = new Card(name, frontText, backText, deck, tag);
        adapter.addCard(newCard);
    }

    public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

        private final LayoutInflater mInflater;
        private List<Card> data;

        CardAdapter(CardFragment context) {
            mInflater = LayoutInflater.from(context.getActivity());
        }

        class CardViewHolder extends RecyclerView.ViewHolder{
            TextView textViewName;
            TextView textViewFront;
            TextView textViewBack;
            TextView textViewTag;
            TextView textViewDeck;
            Button btnDelete;

            private CardViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewFront = itemView.findViewById(R.id.textViewFront);
                textViewBack = itemView.findViewById(R.id.textViewBack);
                textViewDeck = itemView.findViewById(R.id.textViewDeck);
                textViewTag = itemView.findViewById(R.id.textViewTag);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }
        }
        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.item_card, parent, false);
            return new CardViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {

            if (data != null) {

                Card card = data.get(position);
                holder.textViewName.setText(card.getName());
                holder.textViewFront.setText(card.getFront());
                holder.textViewBack.setText(card.getBack());
                holder.textViewDeck.setText(String.format("%s%s", getString(R.string.deck), card.getDeck()));
                holder.textViewTag.setText(String.format("%s%s", getString(R.string.tag), card.getTag()));
                holder.btnDelete.setOnClickListener(view -> {
                    int deletedPosition = holder.getAdapterPosition();
                    removeCard(deletedPosition);
                });
            }
            else {
                holder.textViewName.setText("Add Cards");
            }
        }
        void setData(List<Card> data) {

            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (data != null)
                return data.size();
            else return 0;
        }


        public void addCard(Card card) {
            addCardInBackground(cardDatabase, card);
            data.add(card);
            //notifyItemInserted(cardList.size() - 1);
            adapter.updateCards(data);

        }

        public void removeCard(int position) {
            Card card = data.get(position);
            removeCardInBackground(cardDatabase, card);
            data.remove(position);
            notifyItemRemoved(position);
        }

        public void updateCards(List<Card> newCards) {
            data.clear();
            data.addAll(newCards);
            notifyDataSetChanged();
        }
    }

    public void addCardInBackground(CardDatabase db, Card card) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            db.getCardDAO().addCard(card);

            //on finishing task
            handler.post(() -> Toast.makeText(getContext(), "Added to Database", Toast.LENGTH_SHORT).show());
        });
    }

    public void removeCardInBackground(CardDatabase db, Card card) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            db.getCardDAO().deleteCard(card);

            //on finishing task
            handler.post(() -> Toast.makeText(getContext(), "Deleted from Database", Toast.LENGTH_SHORT).show());
        });
    }
}
