package ch.portmann.wolframalpha;

import java.util.Map;

import org.mapdb.Serializer;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

import ch.portmann.cache.MapDBStore;

public class Wolframalpha {

	private String apiKey = "Hi, please enter the API key here!! ;)";
	
	public Wolframalpha() {


	}

	public boolean isEqual(String expression1, String expression2, boolean alsoAlternative) {
		
		boolean returnValue = false;

		WAEngine engine = new WAEngine();

		// These properties will be set in all the WAQuery objects created from
		// this WAEngine.
		engine.setAppID(this.apiKey);
		engine.addFormat("plaintext");

		// Create the query.
		WAQuery query = engine.createQuery();

		// Set properties of the query.
		String queryString = "(" + expression1 + ") == (" + expression2 + ")";
		
		//check DB for similar querys
		MapDBStore mDB = MapDBStore.getInstance();
		Map<String, Boolean> wolframalphaMap = mDB.getDb().hashMap("wolframalpha", Serializer.STRING, Serializer.BOOLEAN)
				.createOrOpen();
		
		if (wolframalphaMap.get(queryString) != null){
			return wolframalphaMap.get(queryString);
		}
		else
		{
		
		query.setInput(queryString);


		try {

			// This sends the URL to the Wolfram|Alpha server, gets the XML
			// result
			// and parses it into an object hierarchy held by the WAQueryResult
			// object.
			WAQueryResult queryResult = engine.performQuery(query);

			if (!queryResult.isError() && queryResult.isSuccess()) {
				// Got a result.
				for (WAPod pod : queryResult.getPods()) {
					if (!pod.isError()) {
						if (pod.getTitle().equals("Result")
								|| (pod.getTitle().equals("Alternate form") && alsoAlternative)) {
							for (WASubpod subpod : pod.getSubpods()) {
								for (Object element : subpod.getContents()) {
									if (element instanceof WAPlainText) {
										if (((WAPlainText) element).getText().equals("True"))
										{
											returnValue = true;
										}

									}
								}
							}
						}
					}
				}
				// We ignored many other types of Wolfram|Alpha output, such as
				// warnings, assumptions, etc.
				// These can be obtained by methods of WAQueryResult or objects
				// deeper in the hierarchy.
			}
		} catch (WAException e) {
			e.printStackTrace();
		}
		
		wolframalphaMap.put(queryString, returnValue);
		return returnValue;
	}
	}
}
