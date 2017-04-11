package ch.portmann.synonym;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.portmann.input.TokenLight;

public class HandMade implements Synonym {

	private HashMap<String, List<String>> synoMap = new HashMap<String, List<String>>();

	public HandMade(String path) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

		for (String line : lines) {
			String str[] = line.split(";");

			List<String> synoStringList = new ArrayList<String>();

			//row 0 is key
			if (this.synoMap.get(str[0].toLowerCase()) != null)
				synoStringList = this.synoMap.get(str[0].toLowerCase());
			
			synoStringList.add(str[1].toLowerCase());
			synoMap.put(str[0].toLowerCase(), synoStringList);
			
			//row 1 is key
			if (this.synoMap.get(str[1].toLowerCase()) != null)
				synoStringList = this.synoMap.get(str[1].toLowerCase());
			
			synoStringList.add(str[0].toLowerCase());
			synoMap.put(str[1].toLowerCase(), synoStringList);
			
		}
	}

	@Override
	public Set<List<TokenLight>> getSynos(TokenLight token) {

		Set<List<TokenLight>> allSynos = new HashSet<List<TokenLight>>();
		Set<String> synoStrings = new HashSet<String>();
		synoStrings = searchSynoTree(synoStrings, token.getStemmed());

		// convert synoStrings to allSynos (not nice implemented)
		for (String synos : synoStrings) {

			List<TokenLight> tokens = new ArrayList<TokenLight>();
			for (String s : synos.split(" ")) {

				TokenLight t = new TokenLight();
				t.setStemmed(s);
				t.setPos(token.getPos());
				tokens.add(t);

			}

			allSynos.add(tokens);
		}

		return allSynos;
	}

	public Set<String> searchSynoTree(Set<String> allSynos, String toLookUpSyno) {

		// add lookup to allSynos
		allSynos.add(toLookUpSyno);

		if (this.synoMap.get(toLookUpSyno) != null)
			for (String s : this.synoMap.get(toLookUpSyno)) {
				if (!allSynos.contains(s)) {
					allSynos.addAll(searchSynoTree(allSynos, s));
				}
			}

		return allSynos;
	}
}