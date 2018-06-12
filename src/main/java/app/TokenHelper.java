package app;

import java.util.ArrayList;
import java.util.List;

public class TokenHelper {
	public static boolean isTokenBasedAuthentication(String authorizationHeader, String authenticationScheme) {
		return (authorizationHeader != null)
				&&
				(authorizationHeader.toLowerCase()
						.startsWith(authenticationScheme.toLowerCase() + " "));
	}
	
	public static String[] getScopeArray(String scope) {
		System.out.println(scope);
		List<String> scopeArray = new ArrayList<String>();
		String[] slicer = scope.split(" ");
		for(String slice : slicer) {
			System.out.println(slice);
			if (slice.toLowerCase().matches("[a-z]+:.*")) {
				scopeArray.add(slice);
			}
		}
		return scopeArray.toArray(new String[scopeArray.size()]);
	}
}
