package db;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webparser.ModelSet;

public class DAO {
	public static Connection conn = null;

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 加载数据库驱动
			System.out.println("数据库驱动加载成功！"); // 输出的信息
			String url = "jdbc:oracle:thin:@localhost:1522:orcl"; // 获取连接URL
			String user = "zwz"; // 连接用户名
			String password = "Abc12345"; // 连接密码
			conn = DriverManager.getConnection(url, user, password); // 获取数据库连接
			if (conn != null) {
				System.out.println("成功的与Oracle数据库建立连接！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		return conn;
	}

	public static List<String> load_content() throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		// String sql =
		// "select * from (select rownum no, HNII_ALARM_SERVICE_20161031.* from HNII_ALARM_SERVICE_20161031 where rownum <= 2) where no >= 1";
		String sql = "select * from PROJECT_DATA where rownum <= 100";
		List<String> list = new ArrayList<String>(1000);
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		try {
			connection = getConn();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			Reader inStream = null;
			String HTML = null;
			int count = 0;
			while (rs.next()) {
				String id = rs.getString("ID").trim();
				String TITLE = rs.getString("TITLE").trim();
				String SOURCE = rs.getString("SOURCE").trim();
				String URL = rs.getString("URL").trim();
				String UNITNAME = rs.getString("UNITNAME").trim();

				java.sql.Clob clob = rs.getClob("HTML");
				inStream = clob.getCharacterStream();
				char[] c = new char[(int) clob.length()];
				inStream.read(c);
				// data是读出并需要返回的数据，类型是String
				HTML = new String(c);
				inStream.close();

				// System.out.print(id + " ");
				// System.out.print(TITLE);
				// System.out.print(SOURCE);
				// System.out.print(URL);
				// System.out.println();
				// System.out.println(HTML);
				if (URL.startsWith("http://www.cqzb.gov.cn/")) {
					list.add(HTML);
					count += 1;
				}
			}
			System.out.println(count);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;

	}

	// public void write_class() throws SQLException {
	// Classification cla = new Classification();
	// java.io.File czfile = new java.io.File("db_data/chuzu_zixun.txt");
	// java.io.PrintWriter czoutput = null;
	// try {
	// czoutput = new java.io.PrintWriter(czfile);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// java.io.File ctfile = new java.io.File("db_data/chuzu_tousu.txt");
	// java.io.PrintWriter ctoutput = null;
	// try {
	// ctoutput = new java.io.PrintWriter(ctfile);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// java.io.File wtfile = new java.io.File("db_data/wangyue_tousu.txt");
	// java.io.PrintWriter wtoutput = null;
	// try {
	// wtoutput = new java.io.PrintWriter(wtfile);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// java.io.File jpfile = new java.io.File("db_data/jp.txt");
	// java.io.PrintWriter jpoutput = null;
	// try {
	// jpoutput = new java.io.PrintWriter(jpfile);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// Connection connection = null;
	// Statement stmt = null;
	// ResultSet rs = null;
	// // String sql =
	// //
	// "select * from (select rownum no, HNII_ALARM_SERVICE_20161031.* from HNII_ALARM_SERVICE_20161031 where rownum <= 100000) where no >= 1";
	// // String sql = "select CONTENT from HNII_ALARM_SERVICE_20161031";
	// // String sql =
	// //
	// "select CONTENT from (select rownum no, HNII_ALARM_SERVICE_20161031.* from HNII_ALARM_SERVICE_20161031 where rownum <= 100000) where no >= 1";
	// int chuzu = 0, wangyue = 0, zixun = 0, tousu = 0, czzx = 0, czts = 0,
	// wyzx = 0, wyts = 0, jp = 0;
	// Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	// try {
	// connection = getConn();
	// stmt = connection.createStatement();
	// int offset = 1000;
	// int count = 0;
	// for (int i = 1; i < 307472 + offset; i += offset) {
	// List<String> list = new ArrayList<String>(1000);
	// String sql = String
	// .format("select CONTENT from (select rownum no, HNII_ALARM_SERVICE_20161031.* from HNII_ALARM_SERVICE_20161031 where rownum <= %d) where no >= %d",
	// i + offset, i);
	// System.out.println(sql);
	// rs = stmt.executeQuery(sql);
	//
	// while (rs.next()) {
	// String content = rs.getString("CONTENT");
	// Matcher m = p.matcher(content);
	// content = m.replaceAll("");
	// list.add(content);
	// count += 1;
	// }
	// System.out.println(count);
	//
	// List<String[]> label_list = cla.predict(list);
	// if (label_list.size() != list.size())
	// System.out.println(label_list.size() + " " + list.size()
	// + "!!!!!!!!!!!!!!!!!!!");
	// for (int k = 0; k < label_list.size(); k++) {
	// String[] labels = label_list.get(k);
	// if (labels[1].equals("出租车行业")) {
	// // System.out.println(labels[2]);
	// chuzu++;
	// if (labels[2].equals("咨询")) {
	// czzx++;
	// zixun++;
	// czoutput.write(list.get(k) + "\n");
	// } else if (labels[2].equals("投诉")) {
	// tousu++;
	// czts++;
	// ctoutput.write(list.get(k) + "\n");
	// }
	// } else if (labels[1].equals("网约车行业")) {
	// // System.out.println(labels[1] + " " + labels[2]);
	// wangyue++;
	// if (labels[2].equals("投诉")) {
	// wtoutput.write(list.get(k) + "\n");
	// tousu++;
	// wyts++;
	// } else if (labels[2].equals("咨询")) {
	// zixun++;
	// wyzx++;
	// }
	// } else if (labels[1].equals("驾培行业")) {
	// jp++;
	// jpoutput.write(list.get(k) + "\n");
	// }
	// }
	// System.out.println("cz=" + chuzu + " wy=" + wangyue + " ts="
	// + tousu + " zx=" + zixun + " czts=" + czts + " czzx="
	// + czzx + " wyts=" + wyts + " wyzx=" + wyzx + " jp="
	// + jp);
	//
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (rs != null) {
	// rs.close();
	// }
	// if (stmt != null) {
	// stmt.close();
	// }
	// }
	// czoutput.close();
	// ctoutput.close();
	// wtoutput.close();
	// jpoutput.close();
	//
	// }

	public static void writeFile(String path, List<String> list) {
		java.io.File file = new java.io.File(path);
		java.io.PrintWriter output = null;
		try {
			output = new java.io.PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String line : list) {
			output.println(line);
		}
		output.close();

	}
	
	public static void writeFile(String path, String str) {
		java.io.File file = new java.io.File(path);
		java.io.PrintWriter output = null;
		try {
			output = new java.io.PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output.println(str);
		output.close();
	}

	public static void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	public static void main(String[] args) {
		DAO dao = new DAO();
		List<String> list = new ArrayList<String>();
		try {
			list = dao.load_content();
			dao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelSet ms = new ModelSet();

		for(int i = 0; i < list.size(); i++){
			String html = list.get(i);
			int index = ms.get_sim_model(html);
		}
		
		ms.write_model_data();

	}

}
