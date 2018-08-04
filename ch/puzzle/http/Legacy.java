package ch.puzzle.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

/**
 * Reading data form URLs before Java 9.
 *
 */
public class Legacy {

	public static String readWithUrl(String pageUrl) throws IOException {
		URL url = new URL(pageUrl);
		return readContent(url.openStream());
	}

	public static String readWithUrlConnection(String pageUrl) throws IOException {
		URL url = new URL(pageUrl);
		URLConnection connection = url.openConnection();
		return readContent(connection.getInputStream());
	}

	private static String readContent(final InputStream is) throws IOException {
		try (InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr)) {
			return br.lines().collect(Collectors.joining("\n"));
		}
	}

	private static String firstNChars(String s, int n) {
		if (s == null || s.length() <= n) {
			return s;
		}
		return s.trim().substring(0, n) + "... \n\nTotal " + s.length() + " bytes";
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("\n--- get data from URL --------------------------");
		String s = readWithUrl("https://www.puzzle.ch/de/");
		System.out.println(firstNChars(s, 100));

		System.out.println("\n--- get data from URLConnection ----------------");
		String s2 = readWithUrlConnection("https://www.puzzle.ch/de/");
		System.out.println(firstNChars(s2, 100));
	}
}
