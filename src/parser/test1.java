package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import sim.Item;

public class test1 {

	public static Document preParse(String html) {
		Whitelist user_content_filter = Whitelist.relaxed();
		Document doc = Jsoup.parse(html, "http://www.cqzb.gov.cn/");
		Element e = doc.select("div.ztb_con_exp").first();
		e = e.select("div#ztb_zbxx1").first();
		html = Jsoup.clean(e.toString(), user_content_filter);
		html = html.replaceAll("<//span>", "").replaceAll("<span>", "")
				.replaceAll("<blockquote>", "").replace("<//blockquote>", "");
		doc = Jsoup.parseBodyFragment(html, "http://www.cqzb.gov.cn/");
		doc.select("a").remove();

		return doc;
	}

	public void t1() throws IOException {
		File input = new File("out/1/1.html");
		Element doc = Jsoup.parse(input, "UTF-8", "http://www.cqzb.gov.cn/")
				.body();
		// doc = preParse(doc.toString());
		while (doc.children().size() == 1) {
			doc = doc.child(0);
		}
		// System.out.println(doc);
		Elements tables = doc.select("table");
		// System.out.println(tables.get(0));
		if (tables.size() > 0) {
			int table_count = 0;
			boolean flag = false;
			Elements children = doc.children();
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).tagName().equals("table")) {
					table_count++;
					if (table_count == tables.size()) {
						if (i > children.size() - 5) {
							flag = true;
							break;
						}
					}
				}
			}

			if (flag) {
				Element table = tables.last();
				System.out.println(table);
				Item.parse_table(table);
			}
		}
	}

	public void test1() throws IOException {
		File f = new File("t.html");
		Document doc = Jsoup.parse(f, "utf-8");
		System.out.println(doc);
		Element table = doc.select("table").first();
		ArrayList<String> list = Item.parse_table2(table);
		for(String s : list){
			System.out.println(s);
		}
		
	}

	public static void main(String[] args) throws IOException {
		new test1().test1();
	}

}
