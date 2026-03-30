package com.offerzen.common.helpers.json;

import android.content.Context;

import androidx.annotation.RawRes;

import java.io.IOException;

public interface JsonHelper {
    String read(Context context, @RawRes int rawResId) throws IOException;
}
