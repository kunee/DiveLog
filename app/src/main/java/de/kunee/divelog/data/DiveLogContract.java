package de.kunee.divelog.data;

import android.provider.BaseColumns;

public interface DiveLogContract {

    public static interface Dives extends BaseColumns {

        // Database
        public static final String TABLE_NAME = "dive";
        public static final String DIVE_NO = "dive_no";
        public static final String DIVE_DATE = "dive_date";
        public static final String LOCATION = "location";
        public static final String TIME_IN = "time_in";
        public static final String DURATION_BOTTOM_TIME = "duration_bottom_time";
        public static final String DURATION_SAFETY_STOP = "duration_safety_stop";
        public static final String TANK_PRESSURE_BEFORE = "tank_pressure_before";
        public static final String TANK_PRESSURE_AFTER = "tank_pressure_after";
        public static final String DEPTH_MAX = "depth_max";
        public static final String TEMPERATURE_AIR = "temperature_air";
        public static final String TEMPERATURE_SURFACE = "temperature_surface";
        public static final String TEMPERATURE_BOTTOM = "temperature_bottom";
        public static final String VISIBILITY = "visibility";
        public static final String WEIGHT = "weight";
        public static final String DIVE_SKIN = "dive_skin";
        public static final String WET_SUIT = "wet_suit";
        public static final String DRY_SUIT = "dry_suit";
        public static final String HOODED_VEST = "hooded_vest";
        public static final String GLOVES = "gloves";
        public static final String BOOTS = "boots";
        public static final String COMMENTS = "comments";
    }
}
