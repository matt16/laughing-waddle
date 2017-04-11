package ch.portmann.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class MapDBStore {

	private static MapDBStore instance = null;
	private DB db;

	protected MapDBStore() {

		this.setDb(DBMaker.fileDB("mapdb/cache.db").checksumHeaderBypass().make());
	}

	public static MapDBStore getInstance() {
		if (instance == null) {
			instance = new MapDBStore();
		}
		return instance;
	}

	public DB getDb() {
		return this.db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public void close() {
		getDb().commit();
		getDb().close();
	}

	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		if (data == null) {
			return null;
		}

		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}
}
