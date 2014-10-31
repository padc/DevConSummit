package ph.devcon.android.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph.devcon.android.news.db.News;
import ph.devcon.android.news.db.NewsDao;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.db.ProgramDao;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.db.SpeakerDao;
import ph.devcon.android.sponsor.db.Sponsor;
import ph.devcon.android.sponsor.db.SponsorDao;

/**
 * Created by lope on 9/16/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "DevConSummit.sqlite";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper mInstance = null;

    // the DAO object we use to access the SimpleData table
    private ProgramDao programDao = null;
    private SpeakerDao speakerDao = null;
    private SponsorDao sponsorDao = null;
    private NewsDao newsDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Program.class);
            TableUtils.createTable(connectionSource, Speaker.class);
            TableUtils.createTable(connectionSource, Sponsor.class);
//            database.execSQL(FTSSearch.buildTable());
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try {
        List<String> allSql = new ArrayList<String>();
        switch (oldVersion) {
//                case 1:
//                    allSql.add("alter table Test add column `isProcessed` VARCHAR");
        }
        for (String sql : allSql) {
            database.execSQL(sql);
        }
//        } catch (SQLException e) {
//            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
//            throw new RuntimeException(e);
//        }
    }

    public ProgramDao getProgramDao() {
        if (null == programDao) {
            try {
                programDao = getDao(Program.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return programDao;
    }

    public SpeakerDao getSpeakerDao() {
        if (null == speakerDao) {
            try {
                speakerDao = getDao(Speaker.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return speakerDao;
    }

    public SponsorDao getSponsorDao() {
        if (null == sponsorDao) {
            try {
                sponsorDao = getDao(Sponsor.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return sponsorDao;
    }

    public NewsDao getNewsDao() {
        if (null == newsDao) {
            try {
                newsDao = getDao(News.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return newsDao;
    }
}
