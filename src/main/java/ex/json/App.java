
package ex.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * 
 * https://github.com/json-path/JsonPath
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Example of jsonpath using java");
		String json = getJson();
		ex1(json);
		ex2(json);
	}

	private static void ex1(String json) {

		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		String author0 = JsonPath.read(document, "$.store.book[0].author");
		System.out.println(author0);

		//preferred approach 
		DocumentContext docContext = JsonPath.using(Configuration.defaultConfiguration()).parse(json);
		String author1 = docContext.read("$.store.book[1].author");
		System.out.println(author1);

	}

	private static void ex2(String json) {
		List<String> authors = JsonPath.read(json, "$.store.book[*].author");
		System.out.println("authors: " + authors); // print ["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R.
													// Tolkien"]
		DocumentContext docContext = JsonPath.using(Configuration.defaultConfiguration()).parse(json);
		List<Map<String, Object>> expensiveBooks = docContext.read("$.store.book[?(@.price > 22)].title", List.class);
		System.out.println(expensiveBooks); // print ["Hello, Middle-earth! "]
		System.out.println();

		String jsonHiWorld = "{\"message\":\"Hi\",\"place\":{\"name\":\"World!\"}}\"";
		String message = JsonPath.read(jsonHiWorld, "$.message");
		String place = JsonPath.read(jsonHiWorld, "$.place.name");
		System.out.println(message + " " + place); // print "Hi World!"
	}

	private static String getJson() {
		String json = "";
		try {
			json = new String(Files.readAllBytes(Paths.get("./data/sample.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

}
