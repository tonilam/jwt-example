package app;

public class TokenHelper {
	public static boolean isTokenBasedAuthentication(String authorizationHeader, String authenticationScheme) {
		return (authorizationHeader != null)
				&&
				(authorizationHeader.toLowerCase()
						.startsWith(authenticationScheme.toLowerCase() + " "));
	}
}
