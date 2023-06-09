package br.com.twoas.notexrate.network.adapter;

import androidx.annotation.NonNull;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by tiSoares on 09/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class BigDecimalAdapter extends JsonAdapter<BigDecimal> {
    @Override
    @FromJson
    public BigDecimal fromJson(JsonReader reader) throws IOException {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull();
            return null;
        }

        String value = reader.nextString();
        return new BigDecimal(value);
    }

    @Override
    @ToJson
    public void toJson(@NonNull JsonWriter writer, BigDecimal value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }

        writer.value(value.toString());
    }
}
