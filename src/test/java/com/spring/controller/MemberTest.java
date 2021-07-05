package com.spring.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j2;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j2
public class MemberTest {
	
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private DataSource ds;
	
//	@Test
//	public void test() {
//		log.info("테스트 메소드 호출");
//		
//		String sql="insert into spring_member(userid,userpw,username) values(?,?,?)";
//		
//		for(int i=0;i<100;i++) {
//			try(Connection con = ds.getConnection();
//					PreparedStatement pstmt = con.prepareStatement(sql)) {
//				
//				pstmt.setString(2, encoder.encode("pw"+i)); //비밀번호(userpw)
//				
//				if(i < 80) {
//					pstmt.setString(1, "user"+i);  //아이디(userid)
//					pstmt.setString(3, "일반회원"+i); //이름(username)
//				}else if(i < 90) {
//					pstmt.setString(1, "manager"+i);
//					pstmt.setString(3, "운영자"+i);
//				}else {
//					pstmt.setString(1, "admin"+i);
//					pstmt.setString(3, "관리자"+i);
//				}
//				pstmt.executeUpdate();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	
	@Test
	public void testAuth() {
		log.info("테스트 메소드 호출");
		
		String sql="insert into spring_member_auth(userid,auth) values(?,?)";
		
		for(int i=0;i<100;i++) {
			try(Connection con = ds.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql)) {			
				
				
				if(i < 80) {
					pstmt.setString(1, "user"+i);  //아이디(userid)
					pstmt.setString(2, "ROLE_USER"); //이름(username)
				}else if(i < 90) {
					pstmt.setString(1, "manager"+i);
					pstmt.setString(2, "ROLE_MANAGER");
				}else {
					pstmt.setString(1, "admin"+i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}









