package com.offerzen.common.helpers.time_formatter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DefaultTimeFormatter implements TimeFormatter {
    @Override
    public @NotNull String format(@Nullable String dateTimeValue, @NotNull String inputPattern, @NotNull String outputPattern) {
        if (dateTimeValue == null || dateTimeValue.isEmpty()) return "";
        try {
            SimpleDateFormat inputFormat = buildFormat(inputPattern, "UTC");
            SimpleDateFormat outputFormat = buildFormat(outputPattern, null);
            Date date = inputFormat.parse(dateTimeValue);
            return date != null ? outputFormat.format(date) : "";
        } catch (ParseException e) {
            return "";
        }
    }

    @Override
    public long parseToEpoch(@Nullable String dateTimeValue, @NotNull String inputPattern) {
        if (dateTimeValue == null || dateTimeValue.isEmpty()) return 0L;
        try {
            SimpleDateFormat inputFormat = buildFormat(inputPattern, "UTC");
            Date date = inputFormat.parse(dateTimeValue);
            return date != null ? date.getTime() : 0L;
        } catch (ParseException e) {
            return 0L;
        }
    }

    private SimpleDateFormat buildFormat(@NotNull String pattern, @Nullable String timeZone) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        if (timeZone != null) {
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        return format;
    }
}
