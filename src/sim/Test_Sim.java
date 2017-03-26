package sim;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class Test_Sim {

	public static Document preParse(String html){
		Whitelist user_content_filter = Whitelist.relaxed();
		Document doc = Jsoup.parse(html, "http://www.cqzb.gov.cn/");
		Element e = doc.select("div.ztb_con_exp").first();
		e = e.select("div#ztb_zbxx1").first();
		e = e.select("table").first();
		html = Jsoup.clean(e.toString(), user_content_filter);
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "").replaceAll("<blockquote>", "").replace("<//blockquote>", "");
		doc = Jsoup.parseBodyFragment(html, "http://www.cqzb.gov.cn/");
		doc.select("a").remove();
		Elements trs = doc.select("tr");
		List<Integer> index = new ArrayList<Integer>();
		for(int i = 0; i < trs.size(); i++){
			if(trs.get(i).text().replaceAll("\\s*", "").replaceAll("　", "").length() == 0){
				index.add(i);
			}else{
				break;
			}
		}
		for(int i = index.size()-1; i >= 0; i--){
			trs.get(index.get(i)).remove();
		}
		
		index = new ArrayList<Integer>();
		for(int i = trs.size()-1; i >= 0; i--){
			if(trs.get(i).text().replaceAll("\\s*", "").replaceAll("　", "").length() == 0){
				index.add(i);
			}else{
				break;
			}
		}
		for(int i = 0; i < index.size(); i++){
			trs.get(index.get(i)).remove();
		}
		
		return doc;
	}
	
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
	
	public void writeFile(String path, String data) {
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
	
	
	public void test1(){
		Document d1 = preParse(getHTML("http://www.cqzb.gov.cn/zbgg-5-71615-2.aspx"));
		Document d2 = preParse(getHTML("http://www.cqzb.gov.cn/zbgg-5-72181-2.aspx"));
		System.out.println(Similarity.DOM_Similarity(d1, d2));
		writeFile("a.html", d1.toString());
		writeFile("b.html", d2.toString());
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test_Sim().test1();
	}

}
