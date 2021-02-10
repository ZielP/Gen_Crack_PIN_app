package com.example.crackthepin;

import android.provider.BaseColumns;

public class PinContract {

    private PinContract() {
    }

    public static final class PinEntry implements BaseColumns {
        public static final String TABLE_NAME = "pinList";
        public static final String COLUMN_INPUT = "input";
        public static final String COLUMN_OUTPUT = "output";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
