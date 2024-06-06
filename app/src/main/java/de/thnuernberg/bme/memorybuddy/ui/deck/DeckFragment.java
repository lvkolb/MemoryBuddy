package de.thnuernberg.bme.memorybuddy.ui.deck;

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
        import de.thnuernberg.bme.memorybuddy.databinding.FragmentDeckBinding;
        import de.thnuernberg.bme.memorybuddy.ui.card.Card;

public class DeckFragment extends Fragment {

    private FragmentDeckBinding binding;
    private DeckViewModel deckViewModel;
    private DeckAdapter adapter;
    private List<Deck> decks;
    private List<Card> cards;
    private int tempCount = 0;


    private void showDeckEditDialog() {
        DeckEditDialog dialog = new DeckEditDialog(adapter, decks);
        dialog.show(getChildFragmentManager(), "DeckEditDialog");
    }

    public void onDeckSaved(String name, List<Card> cards, int cardCount) {
        Deck newDeck = new Deck(name, cards, cardCount);
        adapter.addDeck(newDeck);
    }

    private void addDeckToList(Deck deck) {
        decks.add(deck);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck, container, false);

        decks = new ArrayList<>();
        adapter = new DeckAdapter(decks);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDecks);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
       // cards = new ArrayList<>();
       // cards.add(new Card("Card 1", "Front 1", "Back 1", "Deck 1", "Tag 1"));
       // cards.add(new Card("Card 2", "Front 2", "Back 2", "Deck 2", "Tag 2"));
      //  cards.add(new Card("Card 3", "Front 3", "Back 3", "Deck 3", "Tag 3"));
      //  for (int i = 0; i < cards.size(); i++) {
      //      tempCount++;
      //  }
       // onDeckSaved("dummydeck",cards,tempCount);

        ExtendedFloatingActionButton buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> showDeckEditDialog());

        return view;
    }

    // Adapter for RecyclerView
    public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckViewHolder> {

        private List<Deck> deckList;
        private int deckCount = 0;

        public DeckAdapter(List<Deck> deckList) {
            this.deckList = deckList;
        }

        @NonNull
        @Override
        public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);
            return new DeckViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
            Deck deck = deckList.get(position);
            holder.textViewName.setText(deck.getName());
            holder.textViewCardCount.setText(String.valueOf("Amount of Cards: "+deck.getCardCount()));
            holder.btnDelete.setOnClickListener(view -> {
                int deletedPosition = holder.getAdapterPosition();
                removeDeck(deletedPosition);
                // Perform additional logic to delete the card from the database or storage
            });
            // Bind other card data to views if needed
        }

        @Override
        public int getItemCount() {
            return deckList.size();
        }
        public int getDeckCount() {
            return deckCount;
        }

        public void addDeck(Deck deck) {
            deckList.add(deck);
            notifyItemInserted(deckList.size() - 1);
        }
        public void removeDeck(int position) {
            deckList.remove(position);
            notifyItemRemoved(position);
        }

        class DeckViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName;
            TextView textViewCardCount;
            Button btnDelete;
            // Other views if needed

            public DeckViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewCardCount = itemView.findViewById(R.id.textViewCardCount);
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