package de.thnuernberg.bme.memorybuddy.DAO;

import de.thnuernberg.bme.memorybuddy.ui.card.Card;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CardDAO {

    @Insert
    public void addCard(Card card);
    @Update
    public void updateCard(Card card);
    @Delete
    public void deleteCard(Card card);
    @Query("select * from cards")
    public LiveData<List<Card>> getAllCard();
    @Query("select * from cards where card_id==:card_id")
    public Card getCard(int card_id);
    @Query("select * from cards where card_recommendation=1")
    public LiveData<List<Card>> getCardRecommended();
    @Query("select * from cards where card_category==:card_category")
    public Card getCardCategory(String card_category);

}
