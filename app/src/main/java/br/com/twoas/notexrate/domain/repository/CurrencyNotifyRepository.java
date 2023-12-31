package br.com.twoas.notexrate.domain.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.twoas.notexrate.domain.model.CurrencyNotify;

/**
 * Created by tiSoares on 09/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
@Dao
public interface CurrencyNotifyRepository {

    @Query("SELECT * FROM currency_notify")
    List<CurrencyNotify> getAll();

    @Query("SELECT * FROM currency_notify WHERE wdg_id NOT IN (:wdgIds) AND wdg_id IS NOT NULL")
    List<CurrencyNotify> findAllByDeletedWdgIds(int[] wdgIds);

    @Query("SELECT * FROM currency_notify WHERE code = :code")
    List<CurrencyNotify> findByCode(String code);

    @Query("SELECT * FROM currency_notify WHERE wdg_id = :wdgId LIMIT 1")
    CurrencyNotify findByWdgId(int wdgId);

    @Query("SELECT * FROM currency_notify WHERE uid = :uid LIMIT 1")
    CurrencyNotify findById(int uid);

    @Query("SELECT code FROM currency_notify")
    List<String> getAllCodes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CurrencyNotify... currencyNotifies);

    @Delete
    void delete(CurrencyNotify currencyNotify);
}
