package webparser;

import java.util.ArrayList;
import java.util.List;

public class demo2 {
//shu
	public static ArrayList<List> main(String[] args) {
		ArrayList<List> input = new ArrayList<List>();
		ArrayList<List> l2 = new ArrayList<List>();
		int len = l2.size();
		int i = 0;
		boolean flag = true;
		while (flag){
			flag = false;
			ArrayList<String> temp = new ArrayList<String>();
			for (List it: input){
				if (i > it.size()) continue;
				else flag = true;
				temp.add((String) it.get(i));
				l2.add(temp);
			}
			l2.add(temp);
		}
		return l2;
	}
}
