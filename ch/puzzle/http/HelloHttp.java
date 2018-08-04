package ch.puzzle.http;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import ch.puzzle.http.util.ResponseHelper;
import ch.puzzle.http.util.SimpleResponseHelper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

/**
 * Basic usage of the Java 9 httpClient API.
 *
 * <li>make an http GET request</li>
 * <li>download an html page</li>
 */
public class HelloHttp {

	public static void helloHttp(String pageUrl) throws Exception {
		HttpRequest request = HttpRequest.newBuilder(new URI(pageUrl)) //
				.GET() //
				.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send( //
				request, //
				HttpResponse.BodyHandler.asString());

		System.out.println(SimpleResponseHelper.toString(response));
	}

	public static void readPageSynchronousHttp1(String pageUrl) throws Exception {
		HttpResponse<String> response = HttpClient.newHttpClient().send( //
				HttpRequest.newBuilder(new URI(pageUrl)).GET().build(), //
				HttpResponse.BodyHandler.asString());

		System.out.println(ResponseHelper.toShortString(response));
	}

	public static void downloadPageSynchronous(String pageUrl) throws Exception {
		HttpResponse<Path> response = HttpClient.newHttpClient().send( //
				HttpRequest.newBuilder(new URI(pageUrl)).GET().build(), //
				HttpResponse.BodyHandler.asFile(Paths.get("my-test.html")));

		System.out.println(ResponseHelper.toShortString(response));
	}

	public static void main(final String[] args) throws Exception {
		System.out.println("\n--- hello world  ------------------------");
		helloHttp("https://www.puzzle.ch/de/");

		System.out.println("\n--- read page by url http 1.1 -----------");
		readPageSynchronousHttp1("https://www.puzzle.ch/de/");

		System.out.println("\n--- download file by url ----------------");
		downloadPageSynchronous("https://www.puzzle.ch/de/");
	}
}
