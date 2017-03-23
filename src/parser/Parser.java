package parser;
import org.jsoup.nodes.Document;

import webparser.ZHAOBIAO;


public interface Parser {

	public ZHAOBIAO parse(Document doc);

}
