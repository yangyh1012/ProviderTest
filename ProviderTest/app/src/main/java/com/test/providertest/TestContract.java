package com.test.providertest;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Yangyihui
 * @create 2018/8/12
 * @Describe
 */
public class TestContract {

    protected static final String CONTENT_AUTHORITY = "com.alipay.uniqueid";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    protected static final String PATH_TEST = "test";
    protected static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + PATH_TEST);

    public static final class ProviderEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEST).build();
        protected static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        protected static final String TABLE_NAME = "test";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VALUE = "value";
    }
}
