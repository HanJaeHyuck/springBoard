package com.yydh.www.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;

import com.yydh.www.board.MemberDAO;
import com.yydh.www.vo.UserVO;

public class BoardController {
	
	@RequestMapping("/login")
	public String login(MemberDAO dao, UserVO vo,HttpServletRequest req) {
		dao.instance.login(vo.getId(), vo.getPassword());
		if (vo != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", vo);
			req.getSession().setAttribute("msg", "로그인 성공");
			return "redirect::/board";
		} else {
			HttpSession session = req.getSession();
			req.getSession().setAttribute("msg", "로그인 실패");
			return "redirect::/";
		}
		
	}
}
