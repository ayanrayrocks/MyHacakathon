package utility;

public class GetXpath {
	public static String getXpath(int trCount, int tdCount) {
		String xpath = "/html[1]/body[1]/div[1]/div[1]/main[1]/article[1]/div[3]/div[1]/div[3]/div[2]/table[1]/tbody[1]/tr["+ trCount +"]/td["+tdCount+"]";
		return xpath;
	}
}
