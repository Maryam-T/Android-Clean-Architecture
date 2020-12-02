package com.pooyeshgaran.tarootfaal.Cache;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.pooyeshgaran.tarootfaal.BuildConfig;
import com.pooyeshgaran.tarootfaal.Entities.Tarot;
import com.pooyeshgaran.tarootfaal.Entities.TarotCard;
import com.pooyeshgaran.tarootfaal.SharedKeys;
import com.pooyeshgaran.tarootfaal.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {
    private static final String TAG = DBAdapter.class.getSimpleName();
    private Context mContext;
    private static String DATABASE_NAME = "TarootFaal.db";
    private static int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private static DBAdapter mInstance;
    private static final String DB_LOCATION = "/data/data/"+ BuildConfig.APPLICATION_ID+"/databases/";

    //Tarot Table
    private String TABLE_TAROT = "tbl_tarot";
    private String KEY_TAROT_ID = "id";
    private String KEY_TAROT_ICON = "icon";
    private String KEY_TAROT_DESCRIPTION = "description";
    private String KEY_TAROT_TITLE = "title";

    //TarotCard Table
    private String TABLE_TAROT_CARD = "tbl_TarotCard";
    private String KEY_TAROT_CARD_ID = "id";
    private String KEY_TAROT_CARD_NAME = "name";
    private String KEY_TAROT_CARD_ICON = "icon";
    private String KEY_TAROT_CARD_COUNT = "card_count";

    public static DBAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBAdapter(context.getApplicationContext());
        }
        return mInstance;
    }
    private DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        Utils mUtils = new Utils();
        int OldVersion = mUtils.loadPreferencesInt(mContext, SharedKeys.DB_VERSION);
        if (OldVersion < DATABASE_VERSION || !databaseExists()) {
            checkDataBase();
            mUtils.savePreferencesInt(mContext, SharedKeys.DB_VERSION, DATABASE_VERSION);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private void openDatabase() {
        if (db != null && db.isOpen()) {
            return;
        }
        String dbPath = DBAdapter.DB_LOCATION + DBAdapter.DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    private void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    private void copyDataBase(InputStream inputStream, OutputStream outputStream) {
        try {
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() +"");
        }
    }
    private void checkDataBase() {
        try {
            File mFile = new File(DBAdapter.DB_LOCATION);
            if (!mFile.exists()){
                mFile.mkdir();
            }
            String mDestPath = DBAdapter.DB_LOCATION + DBAdapter.DATABASE_NAME;
            File mDbFile = new File(mDestPath);
            if (mDbFile.exists()) {
                mDbFile.delete();
            }
            copyDataBase(mContext.getAssets().open(DBAdapter.DATABASE_NAME), new FileOutputStream(mDestPath));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() +"");
        }
    }
    private boolean databaseExists() {
        try {
            String mDestPath = DBAdapter.DB_LOCATION + DBAdapter.DATABASE_NAME;
            File mDbFile = new File(mDestPath);
            return (mDbFile.exists());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() +"");
            return false;
        }
    }

// -------------------------------------------------------------------------------------------------

    public ArrayList<Tarot> getTarotFal(int limit) {
        ArrayList<Tarot> mTarots = new ArrayList<>();
        try {
            mTarots.clear();
            openDatabase();
            String rawQuery = "SELECT * FROM  " + TABLE_TAROT + " ORDER BY " + "RANDOM()" +" LIMIT " +limit;
            Cursor mCursor = db.rawQuery(rawQuery, null);
            while (mCursor.moveToNext()) {
                int id = mCursor.getInt(mCursor.getColumnIndex(KEY_TAROT_ID));
                String icon = mCursor.getString(mCursor.getColumnIndex(KEY_TAROT_ICON));
                String description = mCursor.getString(mCursor.getColumnIndex(KEY_TAROT_DESCRIPTION));
                String title = mCursor.getString(mCursor.getColumnIndex(KEY_TAROT_TITLE));

                Tarot mTarotObj = new Tarot();
                mTarotObj.setId(id);
                mTarotObj.setIcon(icon);
                mTarotObj.setDescription(description);
                mTarotObj.setTitle(title);
                mTarots.add(mTarotObj);
            }
            mCursor.close();
            closeDatabase();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() +"");
        }
        return mTarots;
    }

    public ArrayList<TarotCard> getTarotCards() {
        ArrayList<TarotCard> mTarotCards = new ArrayList<>();
        try {
            mTarotCards.clear();
            openDatabase();
            String rawQuery = "SELECT * FROM  " + TABLE_TAROT_CARD;
            Cursor mCursor = db.rawQuery(rawQuery, null);
            while (mCursor.moveToNext()) {
                int id = mCursor.getInt(mCursor.getColumnIndex(KEY_TAROT_CARD_ID));
                String name = mCursor.getString(mCursor.getColumnIndex(KEY_TAROT_CARD_NAME));
                String icon = mCursor.getString(mCursor.getColumnIndex(KEY_TAROT_CARD_ICON));
                int cardCount = mCursor.getInt(mCursor.getColumnIndex(KEY_TAROT_CARD_COUNT));

                TarotCard mTarotCardObj = new TarotCard();
                mTarotCardObj.setId(id);
                mTarotCardObj.setName(name);
                mTarotCardObj.setIcon(icon);
                mTarotCardObj.setCardCount(cardCount);
                mTarotCards.add(mTarotCardObj);
            }
            mCursor.close();
            closeDatabase();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
        }
        return mTarotCards;
    }


}
