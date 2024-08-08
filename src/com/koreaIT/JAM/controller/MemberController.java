package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.service.MemberService;

public class MemberController {

	private Scanner sc;
	private MemberService memberService;
//	private MemberDao memberDao;
	
	public MemberController(Connection conn, Scanner sc) {
		this.sc = sc;
		this.memberService = new MemberService(conn);
//		this.memberDao = new MemberDao(conn);
	}
	
	public void doJoin() {
		String loginId = null;
		String loginPw = null;
		String name = null;
		
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디는 필수 입력 정보입니다");
				continue;
			}
			
	        boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
	        
	        if (isLoginIdDup) {
	        	System.out.printf("[ %s ]은(는) 이미 사용중인 아이디입니다\n", loginId);
            	continue;
	        }
	        System.out.printf("[ %s ]은(는) 사용가능한 아이디입니다\n", loginId);
			
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호는 필수 입력 정보입니다");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine().trim();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호가 다릅니다.");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.length() == 0) {
				System.out.println("이름은 필수 입력 정보입니다");
				continue;
			}
			break;
		}
		
		memberService.joinMember(loginId, loginPw, name);
		
		System.out.printf("[ %s ]님의 가입이 완료되었습니다\n", name);
	}

}