package com.iti.itiinhands.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iti.itiinhands.beans.Announcement;
import com.iti.itiinhands.beans.Notification;

import java.util.ArrayList;

/**
 * Created by HP on 28/05/2017.
 */

public class DataBase extends SQLiteOpenHelper {


    private static DataBase instance;

    private static final String DATABASE_NAME = "iti.db";
    private static final int DATABASE_Version = 2;


    //anouncement Table
    private static final String AnnouncementTable = "announcement_table";
    private static final String AnnouncementType = "Announcement_type";
    private static final String AnnouncementTitle = "Announcement_title";
    private static final String AnnouncementBody = "Announcement_body";
    private static final String AnnouncementDate = "Announcement_date";




    public static synchronized DataBase getInstance(Context context) {
        if (instance == null) {
            instance = new DataBase(context.getApplicationContext());
        }
        return instance;
    }

    private DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        //create announcement table
        sqLiteDatabase.execSQL("create table IF NOT EXISTS " + AnnouncementTable + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + AnnouncementType + " Integer, " +
                AnnouncementTitle + " varchar(255), " +
                AnnouncementBody + " varchar(255) ,  " +
                AnnouncementDate + " Long)");

    }


/////////////////////////////////////////////// Announcement Table ////////////////////////////////////////
    //insert announcement
    public Long insertAnnouncement(Announcement announcement) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues row = new ContentValues();

        //put row's table
        row.put(AnnouncementType, announcement.getType());
        row.put(AnnouncementTitle, announcement.getTitle());
        row.put(AnnouncementBody,announcement.getBody());
        row.put(AnnouncementDate,announcement.getDate());

        //insertQuery
        Long result = DB.insert(AnnouncementTable, null, row);
        DB.close();

        return result;

    }




    //get all trips
    public ArrayList getAnnoucements() {
        ArrayList<Announcement> AnnoucementList = new ArrayList<Announcement>();
        SQLiteDatabase DB = this.getReadableDatabase();

        int i = 0;
        Cursor c = DB.rawQuery("SELECT * FROM " + AnnouncementTable, null);

        while (c.moveToNext()) {
            Announcement announcement = new Announcement();
            announcement.setType(c.getType(1));
            announcement.setTitle(c.getString(2));
            announcement.setBody(c.getString(3));
            announcement.setDate(c.getLong(4));
            AnnoucementList.add(announcement);
        }

        c.close();
        DB.close();

        return AnnoucementList;

    }



//    public int deleteAnnouncement()
//    {
//        SQLiteDatabase DB=this.getWritableDatabase();
//        int delete =DB.delete(AnnouncementTable,AnnouncementTitle+" =?",new String[]{""});
//        return delete;
//    }




    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("rwsult","upgrade");
    }
}
