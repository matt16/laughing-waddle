package ch.portmann.cache;

import java.io.IOException;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class RecoverDBFuckUp {
	
	public static void main(String[] args) throws IOException {
		
		DB db = DBMaker.fileDB("mapdb/cache.db").checksumHeaderBypass().make();
		db.close();
	}
}
