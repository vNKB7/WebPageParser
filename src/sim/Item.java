package sim;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import util.util;
public class Item {
	public String value;
	public ArrayList<String> prefix = new ArrayList<String>();
	
	public Item(){
		
	}
	
	public Item(String v, ArrayList<String> p){
		value = v;
		prefix = p;
	}

	
	public static ArrayList<String> parse_table(Element table){
//		Document doc = Jsoup.parse(util.getHTML("http://www.cqzb.gov.cn/zbgg-5-72268-2.aspx"));
//		Element body = doc.body().select("table").first();
		Element body = table;
		ArrayList<Item> l = new ArrayList<Item>();
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Element> stack = new ArrayList<Element>();
		ArrayList<Integer> container1 = new ArrayList<Integer>();
		ArrayList<String> container2 = new ArrayList<String>();
		for (int k = 0; k < 20; k ++){
			container1.add(0);
			container2.add("");
		}
		Element root = body;
		stack.add(root);
		boolean a = true;
		while(stack.size() > 0){
			Element node = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);
			if (node.children().size() != 0 && ((!node.child(0).attr("width").equals("") && !node.child(0).attr("width").equals("100%")) | !node.child(0).attr("colspan").equals("") | !node.child(0).attr("rowspan").equals(""))){
				a = false;
			} else{
				if (node.children().size() > 0 && node.child(0).children().size() > 0 && node.child(0).child(0).children().size() == 0){
					a = false;
				} else{
					a = true;
				}
			}
			if (a){
				for (int i = node.children().size() - 1; i >= 0; i--){
					stack.add(node.child(i));
				}
			 } else {
				 String value = node.child(node.children().size() - 1).text();
				 ArrayList<String> prefix = new ArrayList<String>();
				 int N = node.children().size() - 1;
				 int count = 0;
				 int i = 0;
				 for (; i < N; i++){
					 while(container1.get(i) != 0){
						 prefix.add(container2.get(i));
						 container1.set(i, container1.get(i) - 1);
						 if (container1.get(i) == 0){
							 container2.set(i,"");
						 }
						 i += 1;
						 N += 1;
						 count += 1;
					 }
					 prefix.add(node.child(i - count).text());
					 if (!node.child(i - count).attr("rowspan").equals("")){
						 container1.set(i, Integer.valueOf(node.child(i - count).attr("rowspan")) - 1);
						 container2.set(i, node.child(i - count).text());
					 }
				 }
				 if (container1.get(N) != 0){
					 prefix.add(container2.get(N));
					 container1.set(i, container1.get(N) - 1);
					 if (container1.get(N) == 0){
						 container2.set(N,"");
					 }
				 }
				 Item item = new Item(value, prefix);
				 l.add(item);
			 }
		}
		for(Item it : l){
			String str = "";
//			if (it.value.equals("")) continue;
//			System.out.print(it.value+"	");
			for(int i = 0; i < it.prefix.size(); i++){
				str += it.prefix.get(i)+";";
			}
			str += it.value;
			result.add(str);
		}
		return result;
	}
	
	
	public static ArrayList<String> parse_table2(Element table){
		Element body = table;
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Item> l = new ArrayList<Item>();
		ArrayList<Integer> container1 = new ArrayList<Integer>();
		ArrayList<String> container2 = new ArrayList<String>();
		for (int k = 0; k < 20; k ++){
			container1.add(0);
			container2.add("");
		}
		int m = body.select("tr").size();
		for (int i = 0; i < m; i++){
			Element node = body.select("tr").get(i);
			int n = body.select("tr").get(i).children().size();
			String value = node.child(n - 1).text();
			ArrayList<String> prefix = new ArrayList<String>();
			int N = n - 1;
			int count = 0;
			int j = 0;
			for (; j < N; j++){
				while(container1.get(j) != 0){
					prefix.add(container2.get(j));
					container1.set(j, container1.get(j) - 1);
					if (container1.get(j) == 0){
						container2.set(j,"");
						}
					j += 1;
					N += 1;
					count += 1;
					}
				prefix.add(node.child(j - count).text());
				if (!node.child(j - count).attr("rowspan").equals("")){
					container1.set(j, Integer.valueOf(node.child(j - count).attr("rowspan")) - 1);
					container2.set(j, node.child(j - count).text());
					}
				}
			if (container1.get(N) != 0){
				prefix.add(container2.get(N));
				container1.set(N, container1.get(N) - 1);
				if (container1.get(N) == 0){
					container2.set(N,"");
					}
				}
			Item item = new Item(value, prefix);
			l.add(item);
		}
		
		for(Item it : l){
			String str = "";
//			if (it.value.equals("")) continue;
//			System.out.print(it.value+"	");
			for(int i = 0; i < it.prefix.size(); i++){
				str += it.prefix.get(i)+";";
			}
			str += it.value;
			result.add(str);
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(util.getHTML("http://www.cqjt.gov.cn/openCatalog/74BC638467494C9CB7798C381C3CCACF.html"));
//		System.out.println(doc);
		Element table = doc.body().select("table").first();
		ArrayList<String> list = parse_table2(table);
		for(String str : list){
			System.out.println(str);
		}
	}

}
