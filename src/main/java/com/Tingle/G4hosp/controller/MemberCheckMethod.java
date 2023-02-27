package com.Tingle.G4hosp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class MemberCheckMethod {
	
	public static String redirectAfterAlert (String alertMsg, String location, HttpServletResponse resp) {
		try {
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = resp.getWriter();
			out.println("<script>alert('" + alertMsg + "'); location.href = '" + location + "'</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
