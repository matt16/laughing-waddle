package ch.portmann.synonym;

import java.util.List;
import java.util.Set;

import ch.portmann.input.TokenLight;

public interface Synonym {
	
	public Set<List<TokenLight>> getSynos(TokenLight token);

}
