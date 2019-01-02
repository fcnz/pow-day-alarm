package com.example.frasercroad.revelstokepowdayalarm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import timber.log.Timber;

public class ConditionChecker {
    public static final int NEW_SNOW_24_HOUR = 0;
    public static final int NEW_SNOW_SINCE_CLOSE = 1;

    private final int _type;

    ConditionChecker(int type) {
        _type = type;
    }

    int getQuantity() {
        Timber.v("Checking snowfall with type: %s", _type);

        int result = -1;
        try {
            Document doc = Jsoup.connect("https://www.revelstokemountainresort.com/conditions/snow-report").get();

            if (_type == NEW_SNOW_24_HOUR) {
                Element newSnowSource = doc.selectFirst("#content > div.twelve.columns.content-container > section > div.four.columns.alpha.center.border-box.border-right > div:nth-child(2)");
                String newSnowText = newSnowSource.ownText();
                result = Integer.parseInt(newSnowText.substring(0, newSnowText.length() - 3));
                Timber.v("24_HOUR snowfall found");

            } else if (_type == NEW_SNOW_SINCE_CLOSE) {
                Element newSnowSource = doc.selectFirst("#content > div.twelve.columns.content-container > section > div.four.columns.alpha.center.border-box.new-snow > strong");
                String newSnowText = newSnowSource.ownText();
                result = Integer.parseInt(newSnowText);
                Timber.v("SINCE_CLOSE snowfall found");
            }
        } catch (IOException ioe) {
            Timber.e(ioe, "Exception thrown during scraping");
        }

        Timber.v("Snowfall done: %d", result);
        return result;
    }
}
