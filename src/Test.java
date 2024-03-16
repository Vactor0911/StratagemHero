import org.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		Stratagem s = new Stratagem();
		JSONObject[] ary = s.getRandStratagem(20, new String[] {"Defensive", "Eagle"} );
		
		for (JSONObject k : ary) {
			System.out.println(k.getString("name"));
		}
	}

}
