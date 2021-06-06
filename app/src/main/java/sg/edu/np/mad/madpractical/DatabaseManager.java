package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


public class DatabaseManager extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOLLOWED = "followed";

    public DatabaseManager(Context context, String name,
                       SQLiteDatabase.CursorFactory factory,
                       int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS + "(" + COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," + COLUMN_ID + " TEXT," +
                COLUMN_FOLLOWED + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);

        for(int i=0; i<20; i++)
        {
            ContentValues c = new ContentValues();
            c.put("name", "Name" + new Random().nextInt());
            c.put("description", "Description" + new Random().nextInt());
            c.put("followed", new Random().nextInt()%2 == 0);
            //Coz sqllite got no boolean. Only 1 and 0
            db.insert("user",null,c);

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVerson, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> uList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            User user = new User();
            user.setName(cursor.getString(0));
            user.setDescription(cursor.getString(1));
            user.setId(cursor.getInt(2));
            if (cursor.getInt(3) == 0)
            {
                user.setFollowed(false);
            }
            else
            {
                user.setFollowed(true);
            }
            uList.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return uList;

    }

    public void updateUser(User user){
        String query = "SELECT * FROM " + TABLE_USERS
                + " WHERE " + COLUMN_ID + " = \"" + user.id
                + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_FOLLOWED,user.isFollowed());
        values.put(COLUMN_ID,user.getId());
        values.put(COLUMN_NAME,user.getName());

        db.update(TABLE_USERS, values,"id = ?", new String[] {String.valueOf(user.getId())});
        Log.v("Update","hii");
    }

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_FOLLOWED,user.isFollowed());
        values.put(COLUMN_ID,user.getId());
        values.put(COLUMN_NAME,user.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }
}
