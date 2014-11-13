package hm.edu.hmcheckin.util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_HOTSPOT = "hotspot";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_ROOM = "room";

	private static final String DATABASE_NAME = "hmcheckin.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_HOTSPOT + "(" + COLUMN_ID + " integer primary key, " + COLUMN_ADDRESS
			+ " text not null, " + COLUMN_ROOM + " text not null);";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTSPOT);
		onCreate(db);
	}

	public void addHotSpot(HotSpot hotspot) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, hotspot.getId());
		values.put(COLUMN_ADDRESS, hotspot.getAddress());
		values.put(COLUMN_ROOM, hotspot.getRoom());

		db.insert(TABLE_HOTSPOT, null, values);
		db.close();
	}

	public void addHotSpots(ArrayList<HotSpot> hotspots) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();

		for (int i = 0; i < hotspots.size(); i++) {
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, hotspots.get(i).getId());
			values.put(COLUMN_ADDRESS, hotspots.get(i).getAddress());
			values.put(COLUMN_ROOM, hotspots.get(i).getRoom());

			db.insert(TABLE_HOTSPOT, null, values);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	public HotSpot getHotSpot(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_HOTSPOT, new String[] { COLUMN_ID, COLUMN_ADDRESS, COLUMN_ROOM }, COLUMN_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		} else {
			return null;
		}
		HotSpot hotspot = new HotSpot(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
		return hotspot;
	}

	public void updateHotSpot(HotSpot hotspot) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, hotspot.getId());
		values.put(COLUMN_ADDRESS, hotspot.getAddress());
		values.put(COLUMN_ROOM, hotspot.getRoom());

		db.update(TABLE_HOTSPOT, values, COLUMN_ID + " = ?", new String[] { String.valueOf(hotspot.getId()) });
		db.close();
	}

	public void deleteHotSpot(HotSpot hotspot) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_HOTSPOT, COLUMN_ID + " = ?", new String[] { String.valueOf(hotspot.getId()) });
		db.close();
	}

}
