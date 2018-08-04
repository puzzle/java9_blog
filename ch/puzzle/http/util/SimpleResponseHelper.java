package ch.puzzle.http.util;

import java.util.stream.Collectors;

import jdk.incubator.http.HttpHeaders;
import jdk.incubator.http.HttpResponse;

public class SimpleResponseHelper {
	
	public static String toString(final HttpResponse<String> response) {
		StringBuilder sb = new StringBuilder();
		sb.append("Url    : " + response.uri()).append("\n");
		sb.append("Status : " + response.statusCode()).append("\n");
		sb.append("Version: " + response.version()).append("\n");
		sb.append("Body   : " + response.body()).append("\n");
		sb.append("Headers: " + toString(response.headers())).append("\n");
		return sb.toString();
	}

	private static String toString(HttpHeaders headers) {
		return headers.map() //
				.entrySet() //
				.stream() //
				.map(e -> String.format("\"%s\":\"%s\"", e.getKey(), e.getValue())) //
				.collect(Collectors.joining("\n"));
	}
}
