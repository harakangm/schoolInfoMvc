package ch09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
	//Dao : DB와 연동되어서 데이터 값을 받아옴 그걸 DO에 넘겨줌
	
	//Connection클래스? : 데이터 베이스랑 연결을 담당해줌.
	Connection conn = null;
	//쿼리문의 실행을 담당한다.
	PreparedStatement pstmt;
	
	//연결하려면 오라클의 주소랑 계정 정보가 필요함
	 
	//JDBC : 자바하고 DB를 연결해주는 api -> 오라클은 ojbcd6.jar
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver"; //오라클의 주소
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe"; //호스트에 정보
	
	//DB연결 메소드
	public void open() {
		try {
			//리플렉션 : Class.forName() : 클래스의 정보를 리턴해줌
			Class.forName(JDBC_DRIVER); //드라이버 로드
			//실직적으로 DB연결을 하는 곳
			conn = DriverManager.getConnection(JDBC_URL, "test" , "test1234");
			// Connection conn = DtiverManager.getConnection("호스트정보", "호스트아이디", "호스트비밀번호");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	//DB닫는 메소드
	public void close() {
		//리소스는 항상 사용하면 닫아야한다
		try {
			// 리소스(데이터를 읽고 쓰는 객체)
			// pstmt, conn은 리소스 이므로 사용 후 반드시 닫아줘야 한다.
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//학생 정보를 다 불러온다.
	public List<Student> getAll() {
		
		open(); // db오픈 
		
		//student객체를 담을 리스트 준비.
		ArrayList<Student> students = new ArrayList<>();
		
		try {
			//쿼리문입력
			//prepareStatement("쿼리문")
			pstmt = conn.prepareStatement("select * from student");
			//ResultSet : 쿼리문을 실행해서 받은 데이터를 받는 역활
			ResultSet rs = pstmt.executeQuery(); // 쿼리문 실행 executeQuery() : (select문을 사용할때 사용)
			
			//next() : 테이블에서 한행의 값이 존재하면 true 없으면 false
			while(rs.next()) {
				Student s = new Student();
				//get타입() : 테이블에서 받아온 데이터를 자바의 타입으로 바꿔줌
				s.setId(rs.getInt("id"));
				s.setUsername(rs.getString("username")); //이름이 들어옴
				s.setUniv(rs.getString("univ"));
				s.setBirth(rs.getDate("birth"));
				s.setEmail(rs.getString("email"));
				
				students.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 무족건 실행
			close();		
		}
		
		return students;
	}
	
	//학생 정보를 데이터베이스에 입력.
	public void insert(Student s) {
		open();
		//물음표: 어떤 데이터가 들어 올지 모른다.
		String sql = "insert into student values(id_seq.nextval,?,?,?,?)";
				
			try {
				// 실행할 쿼리문 입력
				pstmt = conn.prepareStatement(sql);
				// ????에들어갈 데이터 입력
				//setString(값을 넣어줄 위치, 넣어줄 데이터)
				//오라클의 데이터 타입으로 변환 해준다.
				pstmt.setString(1, s.getUsername());
				pstmt.setString(2, s.getUniv());
				pstmt.setDate(3, s.getBirth());
				pstmt.setString(4, s.getEmail());
				
				pstmt.executeUpdate(); // insert,delete,update 실행시 사용한다
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close();
			}
	
	}
}
