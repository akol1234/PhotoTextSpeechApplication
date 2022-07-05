package foodsqlitedemo.quocnguyen.com.foodsqlitedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String desc, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO PHOTO VALUES (NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, desc);
        statement.bindBlob(2, image);

        statement.executeInsert();
    }

    public void updateData(String desc, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE PHOTO SET desc = ?, image = ? WHERE id = ?"; //rename desc since it has the same name in db commands
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, desc);
        statement.bindBlob(2, image);
        statement.bindDouble(3, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM PHOTO WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
