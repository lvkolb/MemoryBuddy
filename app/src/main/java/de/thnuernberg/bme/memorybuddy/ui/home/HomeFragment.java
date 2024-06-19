package de.thnuernberg.bme.memorybuddy.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.thnuernberg.bme.memorybuddy.R;
import de.thnuernberg.bme.memorybuddy.database.CardDatabase;
import de.thnuernberg.bme.memorybuddy.ui.FragmentContainer;
import de.thnuernberg.bme.memorybuddy.ui.SharedViewModel;
import de.thnuernberg.bme.memorybuddy.ui.card.Card;
import de.thnuernberg.bme.memorybuddy.ui.card.CardDetailDialog;
import de.thnuernberg.bme.memorybuddy.ui.card.CardEditDialog;
import de.thnuernberg.bme.memorybuddy.ui.card.CardFragment;
import de.thnuernberg.bme.memorybuddy.ui.card.CardPlayDialog;


public class HomeFragment extends Fragment implements FragmentContainer {

    private HomeFragment.CardAdapter adapter;
    private List<Card> cards;
    private CardDatabase cardDatabase;
    private RecyclerView recyclerView;
    private SharedViewModel sharedViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCards);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        cardDatabase = sharedViewModel.getCardDatabase();

        adapter = new HomeFragment.CardAdapter(this);
        recyclerView.setAdapter(adapter);

        sharedViewModel.getRecommendedCards().observe(getViewLifecycleOwner(), cardDatabase -> {
            if (cardDatabase != null) {
                // Retrieve the list of cards from the database
                LiveData<List<Card>> cardsLiveData = sharedViewModel.getRecommendedCards();
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
        CardEditDialog dialog = new CardEditDialog(cards);
        dialog.show(getChildFragmentManager(), "CardEditDialog");
    }

    public void onCardSaved(String category, String frontText, String backText, String deck) {
        Card newCard = new Card(category, frontText, backText, deck, 1,1,0);
        adapter.addCard(newCard);
    }
    @Override
    public void onCardUpdated(int id,String category, String frontText, String backText, String deck, int rating, int recommendation , int reviewCount) {
        Card updatedCard = new Card(category, frontText, backText, deck, rating,recommendation,reviewCount);
        updatedCard.setID(id);
        adapter.updateCard(updatedCard);
    }
    @Override
    public void onCardPlayed(int id,String category, String frontText, String backText, String deck, int rating, int recommendation , int reviewCount) {
        int newReviewCount =reviewCount+1;
        int newRecommendation =recommendation+1;
        Card updatedCard = new Card(category, frontText, backText, deck, rating,newRecommendation,newReviewCount);
        updatedCard.setID(id);
        adapter.updateCard(updatedCard);
    }

    public class CardAdapter extends RecyclerView.Adapter<HomeFragment.CardAdapter.CardViewHolder> {

        private final LayoutInflater mInflater;
        private List<Card> data;

        CardAdapter(HomeFragment context) {
            mInflater = LayoutInflater.from(context.getActivity());
        }

        class CardViewHolder extends RecyclerView.ViewHolder{

            LinearLayout cardLayout;
            TextView textViewName;
            TextView textViewFront;
            Button btnDelete;
            Button btnDetail;

            private CardViewHolder(View itemView) {
                super(itemView);
                cardLayout = itemView.findViewById(R.id.cardLayout);
                textViewFront = itemView.findViewById(R.id.textViewFront);
                btnDetail = itemView.findViewById(R.id.btnEdit);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }
        }
        @Override
        public HomeFragment.CardAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.item_card, parent, false);
            return new HomeFragment.CardAdapter.CardViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(HomeFragment.CardAdapter.CardViewHolder holder, int position) {

            if (data != null && data.get(position).getRecommendation()==1) {

                Card card = data.get(position);
                holder.textViewFront.setText(card.getFront());
                holder.btnDelete.setOnClickListener(view -> {
                    int deletedPosition = holder.getAdapterPosition();
                    removeCard(deletedPosition);
                });
                holder.btnDetail.setOnClickListener(view -> {
                    CardDetailDialog dialog = new CardDetailDialog(card);
                    dialog.show(getChildFragmentManager(), "CardDetailDialog");
                });
                holder.cardLayout.setOnClickListener(view -> {
                    CardPlayDialog dialog = new CardPlayDialog(card);
                    dialog.show(getChildFragmentManager(), "CardPlayDialog");
                });
            }
            else {
                holder.textViewFront.setText("No recommened cards");
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
            adapter.updateCards(data);

        }

        public void removeCard(int position) {
            Card card = data.get(position);
            removeCardInBackground(cardDatabase, card);
            data.remove(position);
            notifyItemRemoved(position);
        }
        public void updateCard(Card card) {
            int position = -1;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getID() == card.getID()) {
                    position = i;
                    break;
                }
            }

            if (position != -1) {
                data.set(position, card);
                notifyItemChanged(position);
            }
            updateCardInBackground(cardDatabase, card);
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

    public void updateCardInBackground(CardDatabase db, Card card) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            db.getCardDAO().updateCard(card);

            //on finishing task
            handler.post(() -> Toast.makeText(getContext(), "Updated in Database", Toast.LENGTH_SHORT).show());
        });
    }
}

