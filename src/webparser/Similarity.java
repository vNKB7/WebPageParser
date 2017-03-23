package webparser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Similarity {

	public static double DOM_Similarity(Element Tm, Element Ts){
		double similarity = DOM_T_Similarity(Tm, Ts);
		//similarity = similarity / (Tm.getAllElements().size() + Ts.getAllElements().size());
		//System.out.println(Tm.getAllElements().size()+":"+Ts.getAllElements().size());
		//System.out.println(Tm.getAllElements().size() + Ts.getAllElements().size());
		//System.out.println(similarity / (Tm.getAllElements().size() + Ts.getAllElements().size()));
		return similarity / (Tm.getAllElements().size() + Ts.getAllElements().size()); //similarity;
	}
	
	public static double DOM_T_Similarity(Element Tm, Element Ts) {
		int m = Tm.children().size();
		int n = Ts.children().size();

		double[][] M = new double[m+1][n+1];

		for (int i = 1; i <= m; i++) {
			M[i][0] = M[i-1][0] + Ins_Cost(Tm.child(i-1));
		}

		for (int i = 1; i <= n; i++) {
			M[0][i] = M[0][i-1] + Ins_Cost(Ts.child(i-1));
		}

		double Del_cost, Ins_Cost, Rep_Cost, Sum_Cost;

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				double a = Del_Cost(Ts.child(j-1));
				a = Ins_Cost(Tm.child(i-1));
				Del_cost = M[i][j-1] + Del_Cost(Ts.child(j-1));
				Ins_Cost = M[i-1][j] + Ins_Cost(Tm.child(i-1));
				if (Ts.child(j-1).children().size() == 0) {
					Rep_Cost = replace_cost(Tm.child(i-1), Ts.child(j-1));
					Sum_Cost = M[i-1][j-1] + Rep_Cost + Ins_Cost(Tm.child(i-1).children());
				} else if (Tm.child(i-1).children().size() == 0) {
					Rep_Cost = replace_cost(Tm.child(i-1), Ts.child(j-1));
					Sum_Cost = M[i-1][j-1] + Rep_Cost + Del_Cost(Ts.child(j-1).children());
				} else {
					Rep_Cost = replace_cost(Tm.child(i-1), Ts.child(j-1));
					Sum_Cost = M[i-1][j-1] + Rep_Cost + DOM_T_Similarity(Tm.child(i-1), Ts.child(j-1));
				}

				M[i][j] = Math.min(Del_cost, Math.min(Ins_Cost, Sum_Cost));
			}
		}
		
		double similarity = M[m][n];// * (m + n) / (2 * m * n);
//		System.out.println(M[m][n]);
//		System.out.println(similarity);
		return similarity;
	}

	private static double Del_Cost(Element e) {
		return e.getAllElements().size();
	}

	private static double Del_Cost(Elements es) {
		double sum = 0;
		for (Element e : es) {
			sum += e.getAllElements().size();
		}
		return sum;
	}

	private static double Ins_Cost(Element e) {
		return e.getAllElements().size();
	}

	private static double Ins_Cost(Elements es) {
		double sum = 0;
		for (Element e : es) {
			sum += e.getAllElements().size();
		}
		return sum;
	}

	private static double replace_cost(Element e1, Element e2) {
		if(e1.tagName().equals(e2.tagName())){
			return 0.0;
		}else{
			return 1.0;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
