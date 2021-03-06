package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// CRUD 중심으로 기능을 정의
// 데이터와 관련된 작업(Data Access Object: DAO)
public class MemberDAO {
	// 기능을 정의할 떄는 메서드(함수)를 사용
	// create메서드 호출시 입력값을 받아주는 중간 매개체 역할의 변수
	// -> 매개변수(parameter, 파라메터)
	String url = "jdbc:mysql://localhost:3366/shop1";
	String user = "root";
	String password = "1234";
	Connection con;

	public MemberDAO() throws Exception {
		// 1. connector설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. connector 연결 성공..!");

		// 2. db연결
//		String url = "연걸하는방법://ip//port/db명"
		con = DriverManager.getConnection(url, user, password);
		System.out.println("2. db연결 성공..!");

	}

	public boolean create(MemberVO vo) throws Exception {
		// 3. sql문을 만든다(create)
		String sql = "insert into member values (?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, vo.getId());
		ps.setString(2, vo.getPw());
		ps.setString(3, vo.getName());
		ps.setString(4, vo.getTel());
		System.out.println("3. SQL생성 성공..!");

		// 4. sql문 전송
		int row = ps.executeUpdate();
		System.out.println("4. SQL문 전송 성공..!");
		boolean result = false;
		if (row == 1) {
			result = true;
		}
		ps.close();
		con.close();
		return result;
	}

	public boolean create(String id, String pw, String name, String tel) throws Exception {
		// 3. sql문을 만든다.(create)
		String sql = "insert into member values (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		ps.setString(3, name);
		ps.setString(4, tel);
		System.out.println("3. SQL생성 성공.!!");

		// 4. sql문은 전송
		int row = ps.executeUpdate();
		boolean result = false;
		if (row == 1) {
			result = true;
		}
		System.out.println("4. SQL문 전송 성공.!!");
		ps.close();
		con.close();
		return result;
	}

	// id중복체크
	public int read(String id) throws Exception {
		// 3. sql문을 만든다(create)
		String sql = "select * from member where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id);
		System.out.println("3. SQL생성 성공..!");

		// 4. sql문 전송
		// select의 결과는 검색겨로가가 담긴 테이블(항목 + 내용)
		// 내용에는 없을 수도 있고, 많을 수도 있음
		ResultSet rs = ps.executeQuery();
		System.out.println("4. SQL문 전송 성공..!");
		int result = 0; // 데이터가 없을때
		if (rs.next()) {
			// 결과가 있는지 없는지 체크해주는 메서드
			System.out.println("검색결과가 있어요.");
			result = 1; // 데이터가 있을때
			String id2 = rs.getString("id");
			String pw = rs.getString("pw");
			String name = rs.getString("name");
			String tel = rs.getString("tel");

			System.out.println("id: " + id2);
			System.out.println("pw: " + pw);
			System.out.println("name: " + name);
			System.out.println("tel: " + tel);
		} else {
			System.out.println("검색결과가 없어요.");
		}
		rs.close();
		ps.close();
		con.close();
		return result;
		// 0이 넘어가면 검색결과 없음
		// 1이 넘어가면 검색결과 있음
	}

	// id중복체크
	public MemberVO one(String id) throws Exception {
		// 3. sql문을 만든다(create)
		String sql = "select * from member where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id);
		System.out.println("3. SQL생성 성공..!");

		// 4. sql문 전송
		// select의 결과는 검색겨로가가 담긴 테이블(항목 + 내용)
		// 내용에는 없을 수도 있고, 많을 수도 있음
		ResultSet rs = ps.executeQuery();
		System.out.println("4. SQL문 전송 성공..!");
		MemberVO bag = new MemberVO(); // 가방만들어서
		if (rs.next()) {
			// 결과가 있는지 없는지 체크해주는 메서드
			System.out.println("검색결과가 있어요.");
			String id2 = rs.getString("id");
			String pw = rs.getString("pw");
			String name = rs.getString("name");
			String tel = rs.getString("tel");
			bag.setId(id2);
			bag.setPw(pw);
			bag.setName(name);
			bag.setTel(tel);
		} else {
			System.out.println("검색결과가 없어요.");
		}
		rs.close();
		ps.close();
		con.close();
		return bag;
		// bag은 참조형 변수, 주소를 전달
	}

	// id, pw 맞는지 로그인 처리
	public boolean read(String id, String pw) throws Exception {
		// 3. sql문을 만든다(create)
		String sql = "select * from member where id = ? and pw = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		System.out.println("3. SQL생성 성공..!");

		// 4. sql문 전송
		// select의 결과는 검색겨로가가 담긴 테이블(항목 + 내용)
		// 내용에는 없을 수도 있고, 많을 수도 있음
		ResultSet rs = ps.executeQuery();
		System.out.println("4. SQL문 전송 성공..!");
		boolean result = false; // 로그인이 not인 상태
		if (rs.next()) {
			// 결과가 있는지 없는지 체크해주는 메서드
			System.out.println("로그인 ok");
			result = true; // 데이터가 있을때
		} else {
			System.out.println("로그인 not");
		}
		rs.close();
		ps.close();
		con.close();
		return result;
		// false면 로그인 not
		// true면 로그인 ok
	}

	public boolean update(MemberVO vo) throws Exception {
		// 3. sql문을 만든다(create)
		String sql = "update member set pw = ?, name = ?, tel = ? where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, vo.getPw());
		ps.setString(2, vo.getName());
		ps.setString(3, vo.getTel());
		ps.setString(4, vo.getId());
		System.out.println("3. SQL생성 성공..!");

		// 4. sql문 전송
		int row = ps.executeUpdate();
		System.out.println("4. SQL문 전송 성공..!");
		ps.close();
		con.close();
		boolean result = false;
		if (row == 1) {
			result = true;
		}
		return result;
	}

	public boolean delete(String id) throws Exception {
		// 3. sql문을 만든다(create)
		String sql = "delete from member where id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id);
		System.out.println("3. SQL생성 성공..!");

		// 4. sql문 전송
		int row = ps.executeUpdate();
		ps.close();
		con.close();
		System.out.println("4. SQL문 전송 성공..!");
		boolean result = false;
		if (row == 1) {
			result = true;
		}
		return result;
	}
}
