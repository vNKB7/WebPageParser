package sim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import db.DAO;
import parser.Parser;
import webparser.ZHAOBIAO;

public class ModelSet1 {
	double threshold = 0.4;

	List<CQZB_ZHAOBIAO_Model> model_set = new ArrayList<CQZB_ZHAOBIAO_Model>();
	List<Parser> parser_set = new ArrayList<Parser>();
	List<String> file_set = new ArrayList<String>();
	String base_path = "out/";
	int table_count = 0;
	List<String> url_list = new ArrayList<String>(); 
	List<Integer> count = new ArrayList<Integer>();

	public ModelSet1() {
		// read from file ...
	}

	public void get_sim_model(String html, String url) {
		Document doc = CQZB_ZHAOBIAO_Model.preParse(html);
		Elements tables = doc.select("table");
		for (Element table : tables) {
			table_count++;
			CQZB_ZHAOBIAO_Model sim_model = null;
			double min_dis = Double.MAX_VALUE;
			for (CQZB_ZHAOBIAO_Model m : model_set) {

				double sim = Similarity.DOM_Similarity(m.doc, table);
				if (sim < min_dis) {
					min_dis = sim;
					sim_model = m;
				}
			}
			if (min_dis > threshold) {
				add_model(table);
				url_list.add(url);
				count.add(1);
//				write_html(url + "\n" + table.toString(), model_set.size() - 1);
			} else {
				int index = model_set.indexOf(sim_model);
				count.set(index, count.get(index)+1);
				// write_html(url + "\n" + doc.toString(), index);
			}
		}
	}

	public void write_model_data() {
		for (int i = 0; i < model_set.size(); i++) {
			writeFile(base_path + i + ".html", url_list.get(i)+"\n"+model_set.get(i).doc.toString());
		}
	}

	public void write_html(String html, int index) {
		writeFile(base_path + index + ".html", html);
		model_set.get(index).count++;
	}

	public void writeFile(String path, String data) {
		java.io.File file = new java.io.File(path);
		java.io.PrintWriter output = null;

		try {
			output = new java.io.PrintWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
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

	public boolean add_model(Element e) {
		CQZB_ZHAOBIAO_Model sim_model = null;
		double min_dis = Double.MAX_VALUE;
		for (CQZB_ZHAOBIAO_Model m : model_set) {
			double sim = Similarity.DOM_Similarity(m.doc, e);
			if (sim < min_dis) {
				min_dis = sim;
				sim_model = m;
			}
		}
		if (min_dis == 0) {
			return false;
		} else {
			File new_file = new File(base_path + model_set.size());
			if (!new_file.exists()) {
				new_file.mkdirs();
			}
			file_set.add(base_path + model_set.size());
			model_set.add(new CQZB_ZHAOBIAO_Model(e));
			return true;
		}
	}

	public boolean add_model(String html) {
		Document doc = CQZB_ZHAOBIAO_Model.preParse(html);
		CQZB_ZHAOBIAO_Model sim_model = null;
		double min_dis = Double.MAX_VALUE;
		for (CQZB_ZHAOBIAO_Model m : model_set) {
			double sim = Similarity.DOM_Similarity(m.doc, doc.body());
			if (sim < min_dis) {
				min_dis = sim;
				sim_model = m;
			}
		}
		if (min_dis == 0) {
			return false;
		} else {
			File new_file = new File(base_path + model_set.size());
			if (!new_file.exists()) {
				new_file.mkdirs();
			}
			file_set.add(base_path + model_set.size());
			model_set.add(new CQZB_ZHAOBIAO_Model(doc));
			return true;
		}
	}

	public int get_size() {
		return model_set.size();
	}

	public static void main(String[] args) {
		List<ZHAOBIAO> list = new ArrayList<ZHAOBIAO>();
		try {
			list = DAO
					.load_ZHAOBIAO("select * from PROJECT_DATA where INFOTYPE = 0 and UNITNAME = '重庆市招标投标综合网' and rownum <= 1000");
			DAO.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelSet1 ms = new ModelSet1();

		for (int i = 0; i < list.size(); i++) {
			String html = list.get(i).html;
			ms.get_sim_model(html, list.get(i).url);
		}

		ms.write_model_data();
		System.out.println(ms.table_count);
		for(int i = 0 ; i < ms.count.size(); i++){
			System.out.println(i + " " + ms.count.get(i));
		}
	}

}
