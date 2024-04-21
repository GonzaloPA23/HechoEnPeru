package com.upc.hechoenperu.dtos.response;

import java.io.Serializable;

//clase 5
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		super(); // super() llama al constructor de la clase padre
		this.jwttoken = jwttoken;
	}
	public String getJwttoken() {
		return jwttoken;
	}
}