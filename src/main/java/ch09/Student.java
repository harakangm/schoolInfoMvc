package ch09;

import java.sql.Date;

public class Student {
	//DB에 있는 컬럼명이랑 변수명을 맞춘다
	//DO or 엔티티클래스: 데이터베이스와 대응된다, 컬럼명 = 속성
	private int id;
	private String username;
	private String univ;
	//데이터베이스에서 Date를 받아올때는 java.sql을 임포트 해야함
	private Date birth;
	private String email;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUniv() {
		return univ;
	}
	public void setUniv(String univ) {
		this.univ = univ;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
