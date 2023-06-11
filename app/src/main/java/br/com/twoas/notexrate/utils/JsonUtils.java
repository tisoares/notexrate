package br.com.twoas.notexrate.utils;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import br.com.twoas.notexrate.network.adapter.BigDecimalAdapter;

/**
 * Created by tiSoares on 11/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class JsonUtils {

    public static final Moshi mMoshi = new Moshi.Builder()
            .add(new BigDecimalAdapter())
            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
            .build();

    private JsonUtils() {/* Noting */}

    public static <T> T fromJson(Class<T> type, String json) throws IOException {
        return mMoshi.adapter(type).fromJson(json);
    }

    public static <T> String toJson(Class<T> type, T instance) {
        return mMoshi.adapter(type).toJson(instance);
    }

    public static <T> List<T> fromList(Class<T> type, String json) throws IOException {
        return (List<T>) mMoshi.adapter(Types.newParameterizedType(List.class, type)).fromJson(json);
    }
    public static <T> String toJsonList(Class<T> type, List<T> instance) {
        return mMoshi.adapter(Types.newParameterizedType(List.class, type)).toJson(instance);
    }
}
