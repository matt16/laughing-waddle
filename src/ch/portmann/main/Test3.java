package ch.portmann.main;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.portmann.feature.FAnxiety;
import ch.portmann.feature.FConditionality1;
import ch.portmann.feature.LGTFeature;
import ch.portmann.feature.RegexFeature;

public class Test3 {
	public static void main(String[] args){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Car car = new Car();
		car.brand = "Rover";
		car.doors = 5;

		boolean[] temp = {true,true,false,false,true};
		car.isHit.put("Conditionality", temp);
		
		
		String json = gson.toJson(car);
		System.out.print(json);
		
		RegexFeature cond1 = new FAnxiety();
		m(cond1);
		
		
	}
	
	public static void m(Object x){
		System.out.println(x.toString());
	}
}

class Car{
	public String brand = null;
	public int doors = 0;
	public Map<String, boolean[]> isHit = new HashMap<>();
}
