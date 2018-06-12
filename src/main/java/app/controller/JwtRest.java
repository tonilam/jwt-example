package app.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.web.bind.annotation.RequestParam;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import app.TokenHelper;
import app.model.JwtToken;

@Path("/")
public class JwtRest {

	@GET
	@Path("token")
	public String getToken() {
		String token = "";
		
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			String[] scopes = {"read:messages", "write:messages"};
			
			token = JWT.create()
					.withClaim("scope", String.join(" ", scopes))
					.withIssuer("auth0")
					.sign(algorithm);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "IllegalArgumentException";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "UnsupportedEncodingException";
		}
		
		return token;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("decode")
	public Response decodeToken(@QueryParam("token") String token) {
		JwtToken tokenDetail = new JwtToken();
		try {
			DecodedJWT jwt = JWT.decode(token);
			tokenDetail.withAlgorithm(jwt.getAlgorithm())
				.withType(jwt.getType())
				.withContentType(jwt.getContentType())
				.withKeyId(jwt.getKeyId())
				.withSubject(jwt.getSubject());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.noContent().status(Response.Status.BAD_REQUEST).build();
		} catch (JWTDecodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.noContent().status(Response.Status.BAD_REQUEST).build();
		}
				
		return Response.ok(tokenDetail).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bear")
	public Response decodeAuthorizedBearToken(ContainerRequestContext requestContext) {
		final String AUTHENTICATION_SCHEME = "Bearer";
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		JwtToken tokenDetail = new JwtToken();

		if (!TokenHelper.isTokenBasedAuthentication(authorizationHeader, AUTHENTICATION_SCHEME)) {
			return Response.accepted().status(Response.Status.BAD_REQUEST).build();
		}
		
		try {
			String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length());
			DecodedJWT jwt = JWT.decode(token);
			tokenDetail = tokenDetail.withAlgorithm(jwt.getAlgorithm())
				.withType(jwt.getType())
				.withContentType(jwt.getContentType())
				.withKeyId(jwt.getKeyId())
				.withSubject(jwt.getSubject())
				.withScope(TokenHelper.getScopeArray(jwt.getClaim("scope").asString()));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.noContent().status(Response.Status.BAD_REQUEST).build();
		} catch (JWTDecodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.noContent().status(Response.Status.BAD_REQUEST).build();
		}

		return Response.ok(tokenDetail).build();
	}
	
}
