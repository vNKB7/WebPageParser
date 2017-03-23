import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class Item {
	public String value;
	public ArrayList<String> prefix = new ArrayList<String>();
	
	public Item(){
		
	}
	
	public Item(String v, ArrayList<String> p){
		value = v;
		prefix = p;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File input = new File(".//t.html");
		Document doc = Jsoup.parse(input, "Utf-8");
		Element body = doc.body();
//		Element e = body.select("table").first();
		ArrayList<Item> l = new ArrayList<Item>();
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
			if (node.children().size() != 0 && !node.child(0).attr("width").equals("") && !node.child(0).attr("width").equals("100%")){
				a = false;
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
				 for (int i = 0; i < N; i++){
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
					 if (i + 1 == N && container1.get(i + 1) != 0){
						 prefix.add(container2.get(i + 1));
						 container1.set(i, container1.get(i + 1) - 1);
						 if (container1.get(i + 1) == 0){
							 container2.set(i + 1,"");
						 }
					 }
					 if (!node.child(i - count).attr("rowspan").equals("")){
						 container1.set(i, Integer.valueOf(node.child(i - count).attr("rowspan")) - 1);
						 container2.set(i, node.child(i - count).text());
					 }
				 }
				 Item item = new Item(value, prefix);
				 l.add(item);
				 a = true;
			 }
		}
		for(Item it : l){
			if (it.value.equals("")) continue;
			System.out.print(it.value+"	");
			for(int i = 0; i < it.prefix.size(); i++){
				System.out.print(it.prefix.get(i)+";");
			}
			System.out.println();
		}
		
	}

}
