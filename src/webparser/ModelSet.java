package webparser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import parser.Parser;

public class ModelSet {
	double threshold = 0.4;

	List<CQZB_ZHAOBIAO_Model> model_set = new ArrayList<CQZB_ZHAOBIAO_Model>();
	List<Parser> parser_set = new ArrayList<Parser>();
	List<String> file_set = new ArrayList<String>();
	String base_path = "out/";

	public ModelSet() {
		// read from file ...
	}

	public int get_sim_model(String html) {
		Document doc = CQZB_ZHAOBIAO_Model.preParse(html);
		CQZB_ZHAOBIAO_Model sim_model = null;
		double min_dis = Double.MAX_VALUE;
		for (CQZB_ZHAOBIAO_Model m : model_set) {
			double sim = Similarity.DOM_Similarity(m.doc.body(), doc.body());
			if (sim < min_dis) {
				min_dis = sim;
				sim_model = m;
			}
		}
		if (min_dis > threshold) {
			add_model(html);
			write_html(doc.toString(), model_set.size()-1);
			return -1;
		} else {
			int index = model_set.indexOf(sim_model);
			write_html(doc.toString(), index);
			return index;
		}
	}

	public void write_model_data() {
		for(int i = 0; i < model_set.size(); i++){
			writeFile(file_set.get(i)+"/model", model_set.get(i).doc.toString());
		}
	}
	
	public void write_html(String html, int index){
		writeFile(file_set.get(index)+"/"+model_set.get(index).count+".html", html);
		model_set.get(index).count ++;
	}

	public void writeFile(String path, String data) {
		java.io.File file = new java.io.File(path);
		java.io.PrintWriter output = null;
		
			try {
				output = new java.io.PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
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

	public boolean add_model(String html) {
		Document doc = CQZB_ZHAOBIAO_Model.preParse(html);
		CQZB_ZHAOBIAO_Model sim_model = null;
		double min_dis = Double.MAX_VALUE;
		for (CQZB_ZHAOBIAO_Model m : model_set) {
			double sim = Similarity.DOM_Similarity(m.doc.body(), doc.body());
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
		// TODO Auto-generated method stub

	}

}
