package garrag.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper{
	
	private static final int VERSION = 1;
	public MyDataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	public MyDataBase(Context context,String name){
		this(context,name,VERSION);
	}
	public MyDataBase(Context context,String name,int version){
		this(context, name,null,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("create a Database");
		db.execSQL("create table user(sid int ,sname varchar(20),smac varchar(20))");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("update a Database");
	}
	
}
