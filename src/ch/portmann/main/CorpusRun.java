package ch.portmann.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.util.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.portmann.helper.FileHandler;
import ch.portmann.input.Sentence;
import ch.portmann.input.Sentence2;
import ch.portmann.pipe.RunFeaturePipe;
import ch.portmann.stanford.StanfordCore;

public class CorpusRun {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		StanfordCore.init();

		FileHandler fileHandler = new FileHandler();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// all docs from directory
		String path = "corpus/Test";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			List<Sentence2> tojSentence = new RunFeaturePipe(
					fileHandler.loadFileToString(path + "/" + listOfFiles[i].getName())).process();
			List<JsonOutputData> output = new ArrayList<JsonOutputData>();

			for (Sentence2 s : tojSentence) {
				output.add(new JsonOutputData(s));
			}
			
		
			// store return as json
			
			fileHandler.saveStringAsFile(path + "/out/" + listOfFiles[i].getName() + ".json",
					gson.toJson(output));
		

			System.out.println("Document: " + i + " done.");
		}
	}
}