package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {

	/**
	 * Reads the content of a file, returning a list with each line.
	 * 
	 * @param inFileName
	 * @return a list with each line.
	 * @throws FileNotFoundException
	 */
	List<String> loadFile(String inFileName) throws FileNotFoundException {
		List<String> res = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader((inFileName)));
		try {
			in.lines().parallel().forEach((line) -> res.add(line));
		} finally {
			try {
				in.close();
			} catch (IOException pepe) {
			}
		}
		return res;
	}

	void saveToFile(String outFileName, List<String> lines) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter((outFileName)));
			try {
				for (String line : lines) {
					out.write(line);
					out.newLine();
				}

			} finally {
				out.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}