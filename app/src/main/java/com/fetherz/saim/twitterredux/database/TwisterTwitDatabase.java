package com.fetherz.saim.twitterredux.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by sm032858 on 3/25/17.
 */
@Database(name = TwisterTwitDatabase.NAME, version = TwisterTwitDatabase.VERSION)
public class TwisterTwitDatabase {

    public static final String NAME = "TwisterTwitDatabase"; // we will add the .db extension

    public static final int VERSION = 1;
}
