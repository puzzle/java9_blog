package ch.puzzle.http.util;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;

public class CookieHelper {

	public static void showCookies(String urlInfo, CookieManager cookieManager) {
		System.out.println("cookies for " + urlInfo + ":");
		CookieStore cookieStore = cookieManager.getCookieStore();
		
		if (cookieStore.getCookies().size() == 0) {
			System.out.println("  no cookies found");
			return;
		}
		
		for (HttpCookie cookie : cookieStore.getCookies()) {
			System.out.println(toString(cookie));
		}
	}

	public static String toString(HttpCookie cookie) {
		StringBuilder sb = new StringBuilder();
		sb.append("domain          : " + cookie.getDomain()).append("\n");
		sb.append("max age         : " + cookie.getMaxAge()).append("\n");
		sb.append("name            : " + cookie.getName()).append("\n");
		sb.append("server path     : " + cookie.getPath()).append("\n");
		sb.append("is secure       : " + cookie.getSecure()).append("\n");
		sb.append("value           : " + cookie.getValue()).append("\n");
		sb.append("protocol version: " + cookie.getVersion()).append("\n");
		return sb.toString();
	}
}
