package ch09;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class StudenContoroller
 */
@WebServlet("/studentControll")
public class StudenContoroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDAO dao;
	
	//init (서블릿의 초기화 메서드) 실행 동시와 인스턴스를 호출한다
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config); //서블릿 초기화
		dao = new StudentDAO(); // init이 한번만 실행되니까 객체가 한번만 생성됨 -> 공유해서 쓸 수 있다.
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글데이터 깨짐 방지
		request.setCharacterEncoding("utf-8");
		//request : 뷰에서 넘어온 데이터, 정보가 들어 있다
		String action = request.getParameter("action"); //insert
		String view = " ";
		
		view = insert(request,response); //request와 response 객체를 매개변수로 넘겨 준다.
		
		//getRequestDispatcher(이동할 페이지의 경로를 지정)
		//forward : 페이지를 이동시키지만 내부에서 이동되기 때문에 주소가 변하지 않는다
		getServletContext().getRequestDispatcher("/ch09/" + view).forward(request, response);
		
	}

     
	//request 데이터 받아옴 -> DAO에 있는 insert실행(DB에 INSERT가 됨)
	public String insert (HttpServletRequest request, HttpServletResponse response) {
		Student s = new Student();
		
		
//		s.setUsername(request.getParameter("username"));
//		s.setEmail(request.getParameter("email"));
//		s.setUniv(request.getParameter("univ"));
//		s.setBirth(request.getParameter("birth"));
		
		
		try {
			//위에 과정을 대신해주는 라이브러리 BeanUtils
			// (객체이름 , 파라메터)
			BeanUtils.populate(s, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		dao.insert(s); //컨트롤러는 DAO한테 있는 메소드를 사용한다. DAO한테 데이터 베이스 관련 요청을 해야한다.
		
		return "StudentInfo.jsp";
	}



}
