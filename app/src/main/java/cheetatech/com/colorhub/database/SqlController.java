package cheetatech.com.colorhub.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.ParcelUuid;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.RESTRICTIONS_SERVICE;

public class SqlController {

    private SQLiteDatabase database = null;
    private Context context = null;
    private String tableName = null;

    private static SqlController instance = null;


    public static SqlController getInstance()
    {
        if(instance == null)
            instance = new SqlController();
        return instance;
    }
    private SqlController(){}
    public SqlController(Context context)
    {
        this.context = context;
    }
    public void setContext(Context context)
    {
        this.context = context;
    }
    public Context getContext()
    {
        return this.context;
    }

    public void setDatabase(SQLiteDatabase database)
    {
        this.database = database;
    }
    public SQLiteDatabase getDatabase()
    {
        return this.database;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    public String getTableName()
    {
        return this.tableName;
    }


    public void openOrCreate(String name,int mode)
    {
        this.database = context.openOrCreateDatabase(name,mode,null);
    }

    public void createTable()
    {
        if(database == null)
            return;
        String sqlQuery = "CREATE TABLE IF NOT EXISTS "+ tableName + " ID INTEGER PRIMARY KEY, NAME VARCHAR, COLOR1 VARCHAR,COLOR2 VARCHAR, COLOR3 VARCHAR, COLOR4 VARCHAR,COLOR5 VARCHAR)";
        try{
            database.execSQL(sqlQuery);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void createTable(String tableName)
    {
        if(database == null)
            return;
        String sqlQuery = "CREATE TABLE IF NOT EXISTS "+ tableName + " ID INTEGER PRIMARY KEY, NAME VARCHAR, COLOR1 VARCHAR,COLOR2 VARCHAR, COLOR3 VARCHAR, COLOR4 VARCHAR,COLOR5 VARCHAR)";
        try{
            database.execSQL(sqlQuery);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void save(SqlType type)
    {
        String sqlEkle = "INSERT INTO " + tableName + " (NAME ,COLOR1,COLOR2,COLOR3,COLOR4,COLOR5) VALUES('"
                + type.getName()
                + "','" + type.getColor1()
                + "','" + type.getColor2()
                + "','" + type.getColor3()
                + "','" + type.getColor4()
                + "','" + type.getColor5()
                + "');";
        Log.i("DB", "Save query:" + sqlEkle);
        try{
            database.execSQL(sqlEkle);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(String name)
    {
        String sqlDeleteRow = "DELETE FROM " + tableName + " WHERE NAME="+name+" ;";
        Log.i("DB", "Delete row query:" + sqlDeleteRow);
        try {
            database.execSQL(sqlDeleteRow);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }





}
