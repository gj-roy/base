package vn.loitp.app.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.model.Chap;
import vn.loitp.core.utilities.LLog;

/**
 * Created by Loitp on 5/2/2017.
 */

public class DataManager extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();
    private final static String DB_PATH = "/data/data/loitp.zsamgiangphatgiaohoahao/databases/";
    private final static String DB_NAME = "samgiang.sqlite";
    private final static int DATABASE_VERSION = 1;

    public final static String TABLE_NAME = "t_truyen";

    public final static String KEY_ID = "_id";
    public final static String KEY_TITLE = "tieude";
    public final static String KEY_CONTENT = "noidung";
    public final static String KEY_LINK_MP3 = "diachi";

    private SQLiteDatabase sqLiteDatabase;
    private final Context context;

    public DataManager(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DataManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //do nothing
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //do nothing
    }

    public void openDatabase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        super.close();
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            LLog.d(TAG, "checkDatabase " + e.toString());
        }
        if (checkDB != null) checkDB.close();
        return checkDB != null;
    }

    private void copyDatabase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (dbExist) {
            //khong lam gi ca, database da co roi
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase(); //chep du lieu
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public List<Chap> getAllChap(String tableName) {
        List<Chap> chapList = new ArrayList<Chap>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName, null);
        cursor.moveToFirst();
        do {
            Chap chap = new Chap();
            chap.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
            chap.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            chap.setContent(cursor.getString(cursor.getColumnIndex(KEY_CONTENT)));
            chap.setLinkMp3(cursor.getString(cursor.getColumnIndex(KEY_LINK_MP3)));
            chapList.add(chap);
        } while (cursor.moveToNext());
        return chapList;
    }

    /*public List<Msg> getAllMsg(int categoryId) {
        List<Msg> msgList = new ArrayList<Msg>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_MSG + " where " + KEY_CATEGORY + "=" + categoryId + " order by " + KEY_IS_FAV + " desc", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                Msg msg = new Msg();
                msg.setIsFav(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_IS_FAV))));
                msg.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                msg.setContent(cursor.getString(cursor.getColumnIndex(KEY_CONTENT)));
                msg.setBackup(cursor.getString(cursor.getColumnIndex(KEY_BACKUP)));
                msg.setCategory(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY))));
                msgList.add(msg);
            } while (cursor.moveToNext());
        }
        return msgList;
    }*/

    /*private Vocabulary getVocabularyInListByID(int id, List<Vocabulary> vocabularyList) {
        for (Vocabulary vocabulary : vocabularyList) {
            if (vocabulary.getId() == id) {
                return vocabulary;
            }
        }
        return null;
    }*/

    /*public List<Vocabulary> getFavVocabulary() {
        List<Vocabulary> vocabularyList = getAllVocabulary();
        List<Vocabulary> favVocabularyList = new ArrayList<>();
        String jsonFavVocabulary = LPref.getJsonFavVocabulary(context);
        if (jsonFavVocabulary != null && !jsonFavVocabulary.isEmpty()) {
            List<Integer> idList = LApplication.getGson().fromJson(jsonFavVocabulary, new TypeToken<List<Integer>>() {
            }.getType());
            LLog.d(TAG, "getFavVocabulary " + idList.size());
            if (idList != null) {
                for (Integer integer : idList) {
                    Vocabulary v = getVocabularyInListByID(integer, vocabularyList);
                    if (v != null) {
                        favVocabularyList.add(v);
                    }
                }
            }
        }
        LLog.d(TAG, ">>> favVocabularyList " + favVocabularyList.size());
        return favVocabularyList;
    }*/

    /*public List<Vocabulary> getNRandomVocabulary(int first, int last) {
        List<Vocabulary> vocabularyList = new ArrayList<Vocabulary>();
        String limit = first + "," + last;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, //ten bang
                null,//danh sach cot can lay
                null, //dieu kien where
                null, //doi so dieu kien where
                null, //bieu thuc groupby
                null, //bieu thuc having
                null,//"random()", //bieu thuc orderby
                limit//"0,3" //bieuthuc limit
        );
        cursor.moveToFirst();
        do {
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setId(Integer.parseInt(cursor.getString(0)));
            vocabulary.setSword(cursor.getString(1));
            vocabulary.setSphonetic(cursor.getString(2));
            vocabulary.setSmeanings(cursor.getString(3));
            vocabulary.setSsummary(cursor.getString(4));
            vocabulary.setSisoxfordlist(Integer.parseInt(cursor.getString(5)));
            vocabulary.setClose(true);
            vocabularyList.add(vocabulary);
        } while (cursor.moveToNext());
        return vocabularyList;
    }*/
    /*public int updateMsg(Msg msg) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IS_FAV, msg.getIsFav());
        values.put(KEY_ID, msg.getId());
        values.put(KEY_CONTENT, msg.getContent());
        values.put(KEY_BACKUP, msg.getBackup());
        values.put(KEY_CATEGORY, msg.getCategory());

        return db.update(TABLE_NAME_MSG, values, KEY_ID + " = ?", new String[]{String.valueOf(msg.getId())});
    }*/
}