package app.controller;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.web.bind.annotation.RequestParam;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import app.model.JwtToken;

@Path("/")
public class JwtRest {

	@GET
	@Path("token")
	public String getToken() {
		String token = "";
		
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			token = JWT.create()
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
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("auth0")
					.build();
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
		} catch (UnsupportedEncodingException e) {
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
