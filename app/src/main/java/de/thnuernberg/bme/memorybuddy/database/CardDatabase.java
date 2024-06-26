package de.thnuernberg.bme.memorybuddy.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import de.thnuernberg.bme.memorybuddy.DAO.CardDAO;
import de.thnuernberg.bme.memorybuddy.ui.card.Card;

@Database(entities = {Card.class}, version = 2)
public abstract class CardDatabase extends RoomDatabase {

    public abstract CardDAO getCardDAO();


}


