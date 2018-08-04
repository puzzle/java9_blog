package ch.puzzle.http;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;

import ch.puzzle.http.util.CookieHelper;
import ch.puzzle.http.util.ResponseHelper;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

/**
 * Advanced usage of the httpClient API.
 *
 * Customize the HttpClient using various methods:
 * <li>proxy() to use a proxy</li>
 * <li>followRedirects() to work correct with pages that are moved</li>
 * <li>cookieManager() to work with cookies</li>
 *
 * To run the examples, you must have a running proxy at port 8080. You can install and run
 * the proxy from https://mitmproxy.org
 *
 * @author jean-claude.brantschen
 */
public class CustomizeClient {

	/**
	 * Working with a (local) proxy. You can install a proxy from https://mitmproxy.org.
	 */
	public static void usingProxy(String pageUrl) throws Exception {
		HttpRequest request = HttpRequest.newBuilder() //
				.uri(new URI(pageUrl)) //
				.version(HttpClient.Version.HTTP_2) //
				.GET() //
				.build();

		HttpResponse<String> response = HttpClient.newBuilder() //
				.proxy(ProxySelector.of(new InetSocketAddress("localhost", 8080))) //
				.build() //
				.send(request, HttpResponse.BodyHandler.asString());

		System.out.println(ResponseHelper.toString(response));
	}

	/**
	 * Working with pages that are moved (and prevent an 301 error)
	 * 
	 * e.g. forwards: http://www.sun.com to: https://www.oracle.com/sun/index.html
	 * 
	 * <li>without the forwarding we receive a status code of 301 Moved Permanently
	 * <li>this and all future requests should be directed to the given URI
	 * 
	 * <pre>
	 * Status:  301
	 * Body:    
	 * Headers: "connection":"[close]" 
	 *   "content-length":"[0]" 
	 *   "location":"[http://www.oracle.com/us/sun/index.htm]"
	 * </pre>
	 */
	public static void urlRedirection(String pageUrl) throws Exception {
		HttpRequest request = HttpRequest.newBuilder() //
				.uri(new URI(pageUrl)) //
				.GET() //
				.build();

		HttpResponse<String> response = HttpClient.newBuilder() //
				.followRedirects(HttpClient.Redirect.SAME_PROTOCOL) //
				.build() //
				.send(request, HttpResponse.BodyHandler.asString());

		System.out.println(ResponseHelper.toString(response));
	}

	/**
	 * Working with cookies
	 * <li>create a CookieManager with a policy and use it in all requests
	 * <li>allow cookies: cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
	 * <li>not allow cookies:
	 * cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);
	 */
	public static void useCookies(String pageUrl) throws Exception {
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);

		// accept-cookie policy
		// step 1: server sends back a cookie as part of the response
		// step 2: in the next request, the cookie as append to request
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

		// accept-none policy
		// step 1: server sends back a cookie as part of the response
		// step 2: in the next request, the cookie will not be appended to the request

		// cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);

		CookieHelper.showCookies("", cookieManager);

		HttpRequest request = HttpRequest.newBuilder() //
				.uri(new URI(pageUrl)) //
				.GET() //
				.build();

		HttpResponse<String> firstResponse = HttpClient.newBuilder()
				.proxy(ProxySelector.of(new InetSocketAddress("localhost", 8080))) //
				.cookieManager(cookieManager) //
				.build().send(request, HttpResponse.BodyHandler.asString());

		// System.out.println(ResponseHelper.toString(firstResponse));
		CookieHelper.showCookies(pageUrl, cookieManager);

		HttpResponse<String> SecondResponse = HttpClient.newBuilder()
				.proxy(ProxySelector.of(new InetSocketAddress("localhost", 8080))) //
				.cookieManager(cookieManager) //
				.build().send(request, HttpResponse.BodyHandler.asString());

		// System.out.println(ResponseHelper.toString(SecondResponse));
		CookieHelper.showCookies(pageUrl, cookieManager);
	}

	public static void main(final String[] args) throws Exception {
		System.out.println("\n--- GET with a proxy ---------------------------");
		usingProxy("http://www.google.com");

		System.out.println("\n--- redirect ULR --------------------------------");
		urlRedirection("http://www.sun.com");

		System.out.println("\n--- using cookies -------------------------------");
		useCookies("http://www.google.com");
	}

}
