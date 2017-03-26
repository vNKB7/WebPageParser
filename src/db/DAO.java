package db;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.CQZB_ZHAOBIAO_Model;
import sim.ModelSet;
import util.util;
import webparser.ZHAOBIAO;
import webparser.ZHONGBIAO;

public class DAO { 
	public static Connection conn = null;

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 加载数据库驱动
			System.out.println("数据库驱动加载成功！"); // 输出的信息
			String url = "jdbc:oracle:thin:@192.168.1.101:1521:orcl"; // 获取连接URL
			String user = "scott"; // 连接用户名
			String password = "1234"; // 连接密码
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

	public static List<ZHAOBIAO> load_ZHAOBIAO(String sql) throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		// String sql =
		// "select * from (select rownum no, HNII_ALARM_SERVICE_20161031.* from HNII_ALARM_SERVICE_20161031 where rownum <= 2) where no >= 1";
	//!!!	//String sql = "select * from PROJECT_DATA where SOURCE='http://www.cqzb.gov.cn/class-5-1.aspx' and rownum <= 500";
//		String sql = "select * from PROJECT_DATA where CONTENT like '%项目编号%' and SOURCE = 'http://www.cqzb.gov.cn/class-5-1.aspx'";
		List<ZHAOBIAO> list = new ArrayList<ZHAOBIAO>(1000);
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		try {
			connection = getConn();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			Reader inStream = null;
			String HTML = null;
			int count = 0;
			while (rs.next()) {
				ZHAOBIAO zb = new ZHAOBIAO();
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

				// CONTENT
				clob = rs.getClob("CONTENT");
				inStream = clob.getCharacterStream();
				c = new char[(int) clob.length()];
				inStream.read(c);
				// data是读出并需要返回的数据，类型是String
				String CONTENT = new String(c);
				inStream.close();

				// System.out.print(id + " ");
				// System.out.print(TITLE);
				// System.out.print(SOURCE);
				// System.out.print(URL);
				// System.out.println();
				// System.out.println(HTML);

				zb.xmmc = TITLE;
				zb.html = HTML;
				zb.content = CONTENT;
				zb.title = TITLE;
				zb.url = URL;
				list.add(zb);
				count += 1;

				// if (URL.startsWith("http://www.cqzb.gov.cn/")) {
				// zb.html = HTML
				// list.add(URL+";"+HTML);
				// count += 1;
				// }
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
	
	public static List<ZHONGBIAO> load_ZHONGBIAO(String sql) throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		// String sql =
		// "select * from (select rownum no, HNII_ALARM_SERVICE_20161031.* from HNII_ALARM_SERVICE_20161031 where rownum <= 2) where no >= 1";
	//!!!	//String sql = "select * from PROJECT_DATA where SOURCE='http://www.cqzb.gov.cn/class-5-1.aspx' and rownum <= 500";
//		String sql = "select * from PROJECT_DATA where CONTENT like '%项目编号%' and SOURCE = 'http://www.cqzb.gov.cn/class-5-1.aspx'";
		List<ZHONGBIAO> list = new ArrayList<ZHONGBIAO>(1000);
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		try {
			connection = getConn();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			Reader inStream = null;
			String HTML = null;
			int count = 0;
			while (rs.next()) {
				ZHONGBIAO zb = new ZHONGBIAO();
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

				// CONTENT
				clob = rs.getClob("CONTENT");
				inStream = clob.getCharacterStream();
				c = new char[(int) clob.length()];
				inStream.read(c);
				// data是读出并需要返回的数据，类型是String
				String CONTENT = new String(c);
				inStream.close();

				// System.out.print(id + " ");
				// System.out.print(TITLE);
				// System.out.print(SOURCE);
				// System.out.print(URL);
				// System.out.println();
				// System.out.println(HTML);

				zb.xmmc = TITLE;
				zb.html = HTML;
				zb.content = CONTENT;
				zb.title = TITLE;
				zb.url = URL;
				list.add(zb);
				count += 1;

				// if (URL.startsWith("http://www.cqzb.gov.cn/")) {
				// zb.html = HTML
				// list.add(URL+";"+HTML);
				// count += 1;
				// }
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

	public void test() {
		ArrayList<String> zjPrefix = new ArrayList<String>(Arrays.asList(
				"资金来源为", "资金来自", "资金来源：", "资金来自为", "资金为：", "资金由", "资金：", "资金为",
				"资金：", "资金为"));
		ArrayList<String> zjList = new ArrayList<String>(Arrays.asList("自筹资金",
				"企业自筹", "中央预算内资金", "中央预算投资", "重庆市配套资金", "银行贷款", "业主自筹",
				"上级补助资金", "地方自筹资金", "开行软贷", "中央资金", "专项建设基金", "金融机构贷款", "政府投资",
				"国有资金", "区财政配套", "上级资金", "区财政资金", "县级财政资金", "上级专项补助",
				"中央及市级财政奖励资金", "国有投资", "区县级农村土地整治专项资金", "业主多渠道解决", "市级专项资金",
				"上级拨付", "对口支援", "政府自筹", "交通专项资金", "县教委统筹资金", "财政性资金",
				"全国建制镇示范示点补助资金", "国家棚户区改造专项资金", "水利专项资金", "债券资金", "交委补助",
				"中央政法基础设施建设专项", "市预算内统筹资金", "市级交通资金", "县财政资金", "扶贫资金",
				"中央财政转移支付", "薄弱学校改造资金", "财政涉农资金", "市商委专项资金", "县地质灾害防治基金",
				"市级财政资金"));
		ArrayList<String> zblbList = new ArrayList<String>(Arrays.asList("施工",
				"监理", "勘察设计"));

		List<ZHAOBIAO> list = new ArrayList<ZHAOBIAO>();
		try {
			list = load_ZHAOBIAO("select * from PROJECT_DATA where SOURCE='http://www.cqzb.gov.cn/class-5-1.aspx' and rownum <= 500");
			close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ZHAOBIAO zb : list) {
			// 项目名称
			if (zb.xmmc.endsWith("招标公告")) {
				zb.xmmc = zb.xmmc.substring(0, zb.xmmc.length() - 4);
			} else {
				int index1 = zb.xmmc.lastIndexOf("工程");
				int index2 = zb.xmmc.lastIndexOf("项目");
				index1 = index1 > index2 ? index1 : index2;
				if (index1 > 0) {
					index2 = zb.xmmc.indexOf("）");
					if (index2 > 0) {
						zb.xmmc = zb.xmmc.substring(0, index2 + 1);
					} else {
						zb.xmmc = zb.xmmc.substring(0, index1 + 2);
					}
				}
			}

			// 资金来源
			Set<String> zijin = new HashSet<String>();
			for (String s : zjList) {
				if (zb.content.indexOf(s) != -1) {
					zijin.add(s);
					// System.out.println(s);
				}
			}
			if (zijin.size() == 0) {
				for (String s : zjPrefix) {
					if (zb.content.indexOf(s) != -1) {
						int index = zb.content.indexOf(s) + s.length();
						int offset = 1;
						while (offset < 20) {
							char ch = zb.content.charAt(index + offset);
							if (ch == '，' || ch == '。' || ch == ','
									|| ch == '.' || ch == '、') {
								break;
							}
							offset++;
						}
						if (offset < 20) {
							zijin.add(zb.content.substring(index, index
									+ offset));
							// System.out.println(zb.content.substring(index,
							// index+offset));
						}
					}
				}
			}
			for (String zj : zijin) {
				zb.zjly += zj + " ";
			}

			// 招标类别

			for (String zblb : zblbList) {
				if (zb.title.contains(zblb)) {
					zb.zblb = zblb;
					break;
				}
			}
			if (zb.zblb.length() == 0) {
				for (String zblb : zblbList) {
					if (zb.content.contains(zblb)) {
						zb.zblb = zblb;
						break;
					}
				}
			}

			// 合同段
			String biaoduan = "[一二三四五六七八九十123456789]标段";
			Pattern p = Pattern.compile(biaoduan);
			Matcher m = p.matcher(zb.title);
			while (m.find()) {
				String t = m.group().trim();
				if (t.length() > 0) {
					zb.htd += t;
				}
			}

			if (zb.htd.length() == 0) {
				p = Pattern.compile(biaoduan);
				m = p.matcher(zb.title);
				while (m.find()) {
					String t = m.group().trim();
					if (t.length() > 0) {
						zb.htd += t;
					}
				}
			}
			
			//招标公告编号
			Document doc = Jsoup.parse(zb.html);
			Element zbggbh = doc.select("h4#_xmbh1").first();
			zb.zbggbh = zbggbh.text().trim().substring(7);
			
			
			zb.display();
			
		}
	}

	// public void test2(){
	// String xmmc = "合川区人民医院二期建设项目（第二次）";
	// if (xmmc.endsWith("招标公告")) {
	// xmmc = xmmc.substring(0, xmmc.length() - 4);
	// } else {
	// int index1 = xmmc.lastIndexOf("工程");
	// int index2 = xmmc.lastIndexOf("项目");
	// index1 = index1 > index2 ? index1 : index2;
	// if (index1 > 0) {
	// index2 = xmmc.indexOf("）");
	// if (index2 > 0) {
	// xmmc = xmmc.substring(0, index2 + 1);
	// }else{
	// xmmc = xmmc.substring(0, index1 + 2);
	// }
	// }
	// }
	// System.out.println(xmmc);
	// }
	//
	// public void test3(){
	// ArrayList<String> zjPrefix = new ArrayList<String>(Arrays.asList("资金来源为",
	// "资金来自", "资金来源：", "资金来自为", "资金为：", "资金由", "资金：", "资金为", "资金：", "资金为"));
	// ArrayList<String> zjList = new ArrayList<String>(Arrays.asList("企业自筹",
	// "中央预算内资金", "中央预算投资", "重庆市配套资金", "银行贷款", "业主自筹", "上级补助资金", "地方自筹资金",
	// "开行软贷", "中央资金", "专项建设基金", "金融机构贷款", "政府投资", "国有资金", "区财政配套", "上级资金",
	// "区财政资金", "县级财政资金", "上级专项补助", "中央及市级财政奖励资金", "国有投资", "区县级农村土地整治专项资金",
	// "业主多渠道解决", "市级专项资金", "上级拨付", "对口支援", "政府自筹", "交通专项资金", "县教委统筹资金",
	// "财政性资金", "全国建制镇示范示点补助资金", "国家棚户区改造专项资金", "水利专项资金", "债券资金", "交委补助",
	// "中央政法基础设施建设专项", "市预算内统筹资金", "市级交通资金", "县财政资金", "扶贫资金", "中央财政转移支付",
	// "薄弱学校改造资金", "财政涉农资金", "市商委专项资金", "县地质灾害防治基金", "市级财政资金"));
	// String content = "我们的，资金来自自己种田，别人给的";
	// Set<String> zijin = new HashSet<String>();
	// for(String s : zjList){
	// if(content.indexOf(s) != -1){
	// zijin.add(s);
	// // System.out.println(s);
	// }
	// }
	// if(zijin.size() == 0){
	// for(String s : zjPrefix){
	// if(content.indexOf(s) != -1){
	// int index = content.indexOf(s) + s.length();
	// int offset = 1;
	// while(offset < 20){
	// char ch = content.charAt(index+offset);
	// if(ch == '，' || ch == '。' || ch == ',' || ch == '.' || ch == '、'){
	// break;
	// }
	// offset++;
	// }
	// if(offset < 20){
	// zijin.add(content.substring(index, index+offset));
	// // System.out.println(content.substring(index, index+offset));
	// }
	// }
	// }
	// }
	// }

	public void test5() throws SQLException {
		List<ZHAOBIAO> list = load_ZHAOBIAO("select * from PROJECT_DATA where SOURCE='http://www.cqzb.gov.cn/class-5-1.aspx' and rownum <= 500");

		for (int i = 0; i < list.size(); i++) {
			String text = list.get(i).content;
			int start = 0;
			while (start < text.length()) {
				int index = text.indexOf("项目编号", start);
				if (index < 0) {
					break;
				}
				System.out.println(list.get(i).url + " "
						+ text.substring(index - 2, index + 50) + "  ");
				start = index + 1;
			}
		}
	}
	
	public void test6(){
		int index = 0;
		List<ZHAOBIAO> list = new ArrayList<ZHAOBIAO>();
		try {
			list = DAO
					.load_ZHAOBIAO("select * from PROJECT_DATA where INFOTYPE = 0 and UNITNAME = '重庆市招标投标综合网' and rownum <= 1000");
			DAO.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(ZHAOBIAO zb : list){
			Elements tables = CQZB_ZHAOBIAO_Model.preParse(zb.html).select("table");
			for(Element table : tables){
				String html = table.toString();
				if(html.contains("标段")||html.contains("项目")||html.contains("合同")){
					util.writeFile("out/"+(index++)+".html", zb.url + "\n" + html);
				}
			}
		}
	}
	

	public static void main(String[] args) throws SQLException {
		DAO dao = new DAO();
		dao.test6();

		// ModelSet ms = new ModelSet();
		//
		// for(int i = 0; i < list.size(); i++){
		// String html = list.get(i);
		// int index = ms.get_sim_model(html);
		// }
		//
		// ms.write_model_data();
		
		
	}

}
