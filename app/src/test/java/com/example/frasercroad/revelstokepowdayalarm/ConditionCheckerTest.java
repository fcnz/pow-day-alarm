package com.example.frasercroad.revelstokepowdayalarm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.BeforeClass;
import org.junit.Test;

import timber.log.Timber;

import static org.junit.Assert.*;

/**
 * Tests the ConditionChecker class which houses web scraping logic
 */
public class ConditionCheckerTest {
    @BeforeClass
    public static void setup_logging() {
        Timber.plant(new Timber.Tree() {
            @Override
            protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
                System.out.println(message);
                if (t != null) {
                    System.out.println("ERROR: " + t.getMessage());
                    t.printStackTrace();
                }
            }
        });
    }

    @Test
    public void can_check_24_hour_snow() {
        ConditionChecker checker = new ConditionChecker(ConditionChecker.NEW_SNOW_24_HOUR);
        int qty = checker.getQuantity();
        assertTrue("Quantity found is greater than 0 (i.e. it worked)", qty >= 0);
    }

    @Test
    public void can_check_snow_since_close() {
        ConditionChecker checker = new ConditionChecker(ConditionChecker.NEW_SNOW_SINCE_CLOSE);
        int qty = checker.getQuantity();
        assertTrue("Quantity found is greater than 0 (i.e. it worked)", qty >= 0);
    }
}
