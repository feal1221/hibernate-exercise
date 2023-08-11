package web.member.controller;

import static core.util.CommonUtil.writePojo2Json;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.util.CommonUtil;
import web.member.service.MemberService;

@WebServlet("/member/getInfoAll")
public class GetInfoAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService service;
	
	public void init() throws ServletException{
		service = CommonUtil.getBean(getServletContext(),MemberService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		writePojo2Json(response, service.findAll());
	}
}
