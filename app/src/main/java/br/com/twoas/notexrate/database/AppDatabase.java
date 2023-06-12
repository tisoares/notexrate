package br.com.twoas.notexrate.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.database.converter.Converters;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;

/**
 * Created by TIAGO SOARES on 16/07/2019.
 */
@TypeConverters(Converters.class)
@Database(entities = {CurrencyNotify.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CurrencyNotifyRepository currencyNotifyRepository();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME)
//                            .addMigrations(MIGRATION_1_2)
//                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE currency_notify"
//                    + "  COLUMN uid INTEGER ");
//            database.execSQL("ALTER TABLE currency_notify"
//                    + " ADD COLUMN wdg_id INTEGER ");
//        }
//    };
}