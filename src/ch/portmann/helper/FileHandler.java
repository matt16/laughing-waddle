package ch.portmann.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {

	public FileHandler() {

	}

	public String loadFileToString(String path) throws IOException {

		Charset encoding = StandardCharsets.UTF_8;
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		// return encoding.decode(ByteBuffer.wrap(encoded)).toString();
		return new String(encoded, encoding);
	}

	public void saveStringAsFile(String url, String content) throws IOException {

		File file = new File(url);
		file.getParentFile().mkdirs();

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

	}

	public void saveSListAsFile(String url, List<String> content, String stopper) throws IOException {

		String toWrite = "";

		for (String s : content)
			toWrite = toWrite + s + stopper;

		File file = new File(url);
		file.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(toWrite);
		bw.close();
	}

	public void saveMapAsFile(String url, Map<String, Integer> content, String stopper) throws IOException {

		String toWrite = "";

		for (Map.Entry<String, Integer> entry : content.entrySet())
			toWrite = toWrite + entry.getKey() + stopper + entry.getValue() + stopper + "\r";

		File file = new File(url);
		file.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(toWrite);
		bw.close();

	}

	public void saveIListAsFile(String url, List<Integer> content, String stopper) throws IOException {

		List<String> newContent = new ArrayList<String>();

		for (Integer s : content)
			newContent.add(s.toString());

		saveSListAsFile(url, newContent, stopper);
	}

	// load file
	// dog
	// cat
	// house
	// into hashmap like
	// <dog,1>
	// <cat,2>
	// <house,3>
	public Map<String, Integer> loadFileToMapIndex(String path, boolean lowerCase) throws Exception {

		Map<String, Integer> returnMap = new HashMap<String, Integer>();

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new File(path));

		int index = 1;
		while (scanner.hasNext()) {

			if (lowerCase)
				returnMap.put(scanner.next().toLowerCase(), index);

			else
				returnMap.put(scanner.next(), index);

			index++;
		}

		return returnMap;

	}

	public Map<String, Integer> loadFileToMap(String path, boolean lowerCase) throws Exception {

		Map<String, Integer> returnMap = new HashMap<String, Integer>();

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new File(path));

		int index = 1;
		while (scanner.hasNext()) {

			if (lowerCase) {
				String[] values = scanner.next().toLowerCase().split(";");
				returnMap.put(values[0], Integer.parseInt(values[1]));
			} else {
				String[] values = scanner.next().split(";");
				returnMap.put(values[0], Integer.parseInt(values[1]));
			}

			index++;
		}

		return returnMap;

	}

	public Map<String, Integer> loadFileToMapZERO(String path, boolean lowerCase) throws Exception {

		Map<String, Integer> returnMap = new HashMap<String, Integer>();

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new File(path));

		int index = 0;
		while (scanner.hasNext()) {

			if (lowerCase)
				returnMap.put(scanner.next().toLowerCase(), index);

			else
				returnMap.put(scanner.next(), index);

		}

		return returnMap;

	}

}
