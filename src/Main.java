import java.util.Stack;

public class Main {
	public static void main(String[] args) throws Exception {
		String xml =
    	"""
		XML сюда
				""";

		XMLParser parser = new XMLParser();
		parser.parseXml(xml);

	}

}