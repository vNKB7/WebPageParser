package sim;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

public class CQJT_ZHAOBIAO_Model{
	Document doc = null;
	String folder = "";
	File file = null;
	int count = 0;

	public CQJT_ZHAOBIAO_Model() {
	}

	public CQJT_ZHAOBIAO_Model(String html) {
		doc = preParse(html);
	}

	public CQJT_ZHAOBIAO_Model(Document d) {
		doc = d;
	}

	// public double similarity(Document s){
	// return Similarity.DOM_Similarity(doc, s);
	// }

	public static Document preParse(String html) {
		Whitelist user_content_filter = Whitelist.relaxed();
		Document doc = Jsoup.parse(html, "http://www.cqjt.gov.cn/");
		Element e = doc.select("div.newsDetail").first();
		html = Jsoup.clean(e.toString(), user_content_filter);
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "")
				.replaceAll("<blockquote>", "").replace("<//blockquote>", "");
		doc = Jsoup.parseBodyFragment(html, "http://www.cqjt.gov.cn/");
		doc.select("a").remove();
		// doc.select("p").remove();
		return doc;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
