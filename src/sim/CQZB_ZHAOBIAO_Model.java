package sim;
import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;


public class CQZB_ZHAOBIAO_Model{
	
	Element doc = null;
	String folder = "";
	File file = null;
	int count = 0;
	
	public CQZB_ZHAOBIAO_Model(){
	}
	
	
	public CQZB_ZHAOBIAO_Model(String html){
		doc = preParse(html);
	}
	
	public CQZB_ZHAOBIAO_Model(Element d){
		doc = d;
	}
	
	
	
//	public double similarity(Document s){
//		return Similarity.DOM_Similarity(doc, s);
//	}
	
	public static Document preParse(String html){
		Whitelist user_content_filter = Whitelist.relaxed();
		Document doc = Jsoup.parse(html, "http://www.cqzb.gov.cn/");
		Element e = doc.select("div.ztb_con_exp").first();
		e = e.select("div#ztb_zbxx1").first();
		html = Jsoup.clean(e.toString(), user_content_filter);
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "").replaceAll("<blockquote>", "").replace("<//blockquote>", "");
		doc = Jsoup.parseBodyFragment(html, "http://www.cqzb.gov.cn/");
		doc.select("a").remove();
		
		return doc;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
