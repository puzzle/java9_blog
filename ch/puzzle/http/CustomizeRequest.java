package ch.puzzle.http;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;

// import org.apache.http.client.utils.URIBuilder;

import ch.puzzle.http.util.ResponseHelper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

/**
 * Advanced usage of the httpClient API.
 *
 * Customize HttpRequest using various methods:
 * <li>GET(), POST()</li>
 * <li>headers(), version()</li>
 *
 * To run the examples, you must have a running proxy at port 8080. You can install and run
 * the proxy from https://mitmproxy.org
 *
 * To run getWithParameters() you must install the Apache HttpComponenents library
 * from https://hc.apache.org/downloads.cgi
 *
 * @author jean-claude.brantschen
 */
public class CustomizeRequest {

	public static void getWithHeaders(String pageUrl) throws Exception {
		HttpRequest request = HttpRequest.newBuilder() //
				.uri(new URI(pageUrl)) //
				.headers("key1", "value1", "key2", "value2") //
				.GET() //
				.build();

		processRequestUsingProxy(request);
	}

	/**
	 * Use the apache URIBuilder class to build an URI with parameters and make a
	 * GET request. In addition use a local proxy to view the generated request.
	 *
	 * (You must install the Apache HttpComponenents library from https://hc.apache.org/downloads.cgi)
	 */
	public static void getWithParameters(String pageUrl) throws Exception {
		/*
		URIBuilder uriWithParameters = new URIBuilder(pageUrl);
		uriWithParameters.addParameter("t", "search");
		uriWithParameters.addParameter("q", "apples");

		HttpRequest request = HttpRequest.newBuilder() //
				.uri(uriWithParameters.build()) //
				.GET() //
				.build();

		processRequestUsingProxy(request);
		*/
	}

	public static void getWithHttp2(String pageUrl) throws Exception {
		HttpRequest request = HttpRequest.newBuilder() //
				.uri(new URI(pageUrl)) //
				.version(HttpClient.Version.HTTP_2) //
				.GET() //
				.build();

		processRequestUsingProxy(request);
	}

	public static void post(String pageUrl) throws Exception {
		String data = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
		HttpRequest request = HttpRequest.newBuilder() //
				.uri(new URI(pageUrl)) //
				.POST(HttpRequest.BodyProcessor.fromString(data)) //
				.build();

		processRequestUsingProxy(request);
	}

	private static void processRequestUsingProxy(HttpRequest request) throws Exception {
		HttpResponse<String> response = HttpClient.newBuilder() //
				.proxy(ProxySelector.of(new InetSocketAddress("localhost", 8080))) //
				.build() //
				.send(request, HttpResponse.BodyHandler.asString());

		System.out.println(ResponseHelper.toString(response));
	}

	public static void main(final String[] args) throws Exception {
		System.out.println("\n--- GET with headers ---------------------------");
		getWithHeaders("http://urlecho.appspot.com/echo");

		System.out.println("\n--- GET with parameters ------------------------");
		getWithParameters("http://urlecho.appspot.com/echo");

		System.out.println("\n--- GET with http2 -----------------------------");
		getWithHttp2("http://urlecho.appspot.com/echo");

		System.out.println("\n--- POST ---------------------------------------");
		post("http://urlecho.appspot.com/echo");
	}

}
