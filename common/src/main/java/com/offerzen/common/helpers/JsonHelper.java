package com.offerzen.common.helpers;

import android.content.Context;

import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonHelper {
    public static String read(Context context, @RawRes int rawResId) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(rawResId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }
}
