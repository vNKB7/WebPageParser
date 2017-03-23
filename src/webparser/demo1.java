package webparser;

import java.util.ArrayList;
import java.util.List;

public class demo1 {
	//heng
	public static ArrayList<List> main(String[] args) {
		ArrayList<Item> input = new ArrayList<Item>();
		ArrayList<List> l2 = new ArrayList<List>();
		for (Item it: input){
			if (it.value.equals("")) continue;
			ArrayList<String> temp = new ArrayList<String>();
			for(int i = 0; i < it.prefix.size(); i++){
				temp.add(it.prefix.get(i));
			}
			temp.add(it.value);
			l2.add(temp);
		}
		return l2;
	}

}
