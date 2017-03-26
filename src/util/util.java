package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class util {
	
	public static String getHTML(String url) {
		String html = null;
		try{
			Document doc = Jsoup.connect(url)
					.userAgent("Mozilla")
					.timeout(15000)
					.get();
			html = doc.html();
		}catch(Exception e) {
			
		}
		return html;
	}
	
	public static void writeFile(String path, String data) {
		java.io.File file = new java.io.File(path);
		java.io.PrintWriter output = null;
		
			try {
				output = new java.io.PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		output.println(data);
		output.close();
	}
	
	public static List<String> col_tab(Element table){
		List<String> list = new ArrayList<String>();
		int m = table.select("tr").size();
		int n = table.select("tr").select("td").size();
		
		for(int i = 0; i < n; i++){
			list.add("");
		}
		
		Elements trs = table.select("tr");
		for(int i = 0; i < m; i++){
			Elements tds = trs.get(i).select("td");
			for(int j = 0; j < tds.size(); j++){
				list.set(j, list.get(j)+tds.get(j).text()+";");
			}
		}
		
		return list;
	}
}
