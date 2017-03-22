package webparser;
import java.util.ArrayList;


public class Item {
	public String value;
	public ArrayList<String> prefix = new ArrayList<String>();
	
	public Item(){
		
	}
	
	public Item(String v, ArrayList<String> p){
		value = v;
		prefix = p;
	}
	
	public static void main(String[] args) {
		ArrayList<Item> array = new ArrayList<Item>();
		
		String value = "重庆开疆建筑工程有限公司";
		ArrayList<String> prefix = new ArrayList<String>();
		prefix.add("綦江区2016年横山高标准农田建设项目");
		prefix.add("一标段");
		prefix.add("第一中标候选人");
		
		
		Item item = new Item(value, prefix);
		
		array.add(item);
		
		for(Item it : array){
			System.out.print(it.value+"	");
			for(int i = 0; i < it.prefix.size(); i++){
				System.out.print(it.prefix.get(i)+";");
			}
		}

	}

}
