import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CompanyManager {
	private static CompanyManager instance = null;

	private HashMap<String, Company> list;

	private CompanyManager() {
		list = new HashMap<String, Company>();
	}

	public static CompanyManager getInstance() {
		if (instance == null)
			instance = new CompanyManager();
		return instance;
	}

	public void addCompany(Company c) {
		list.put(c.getName(), c);
	}

	public Company getCompany(String c) {
		return list.get(c);
	}

	public void setCompany(Company c) {
		list.put(c.getName(), c);
	}

	public ArrayList<String> getCompanyList() {
		ArrayList<String> returnArray = new ArrayList<String>();
		Iterator<String> iterator = list.keySet().iterator();
		while (iterator.hasNext()) {
			returnArray.add(iterator.next());
		}
		return returnArray;
	}
}
