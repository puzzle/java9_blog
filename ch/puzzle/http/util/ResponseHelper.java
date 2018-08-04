package ch.puzzle.http.util;

import java.util.stream.Collectors;

import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpResponse;

public class ResponseHelper {

	public static String toShortString(final HttpResponse<?> response) {
		return toString(response, 100, 3, "|");
	}

	public static String toString(final HttpResponse<String> response) {
		return toString(response, 1000, 1000, "\n");
	}

	private static String toString(final HttpResponse<?> response, int maxChar, int maxHeader, String separator) {
		StringBuilder sb = new StringBuilder();
		sb.append("Url    : " + response.uri()).append("\n");
		sb.append("Status : " + response.statusCode()).append("\n");
		sb.append("Version: " + response.version()).append("\n");
		sb.append("Body   : " + firstNChars(response.body().toString(), maxChar)).append("\n");
		sb.append("Headers: " + firstNHeaders(response.headers(), maxHeader, separator)).append("\n");
		return sb.toString();
	}

	private static String firstNChars(String s, int n) {
		if (s == null || s.length() <= n) {
			return s;
		}
		return s.trim().substring(0, n) + "... \n\nTotal " + s.length() + " bytes";
	}
	
	private static String firstNHeaders(HttpHeaders headers, int n, String separator) {
		return headers.map() //
				.entrySet() //
				.stream() //
				.limit(n) //
				.map(e -> String.format("\"%s\":\"%s\"", e.getKey(), e.getValue())) //
				.collect(Collectors.joining(" " + separator + " "));
	}

}
