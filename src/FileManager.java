import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileManager {
	private ArrayList<String> company_list;

	public FileManager() {

	}

	public void ReadTrainingData(String data_folder_path) {
		company_list = ReadCompanyTable(data_folder_path);
		for (String company : company_list) {
			Company temp = ReadCompanyAllData(data_folder_path
					+ "\\Training_Data_Sample\\RawData_Sample", company);
			CompanyManager.getInstance().addCompany(temp);
		}
	}

	public ArrayList<String> ReadCompanyTable(String data_folder_path) {
		String fullPath = data_folder_path + "\\CODE_LIST_Train.txt";
		String line;
		ArrayList<String> returnArray = new ArrayList<String>();

		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(fullPath));
			while ((line = buffer.readLine()) != null) {
				returnArray.add(line);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnArray;

	}

	public ArrayList<Tick> ReadDayCompanyData(String data_folder_path,
			String company_title, int day) {
		String textFile = company_title + "_" + String.format("%05d", day);
		String fullPath = data_folder_path + "\\" + company_title + "\\"
				+ textFile + ".txt";
		ArrayList<Tick> returnTick = new ArrayList<Tick>();

		String line;
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(fullPath));
			while ((line = buffer.readLine()) != null) {
				returnTick.add(new Tick(line));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnTick;
	}

	public Company ReadCompanyAllData(String data_folder_path,
			String company_title) {
		String fullDirectoryPath = data_folder_path + "\\" + company_title;
		File directory = new File(fullDirectoryPath);
		File[] directoryFiles = directory.listFiles();

		int filesLength = directoryFiles.length;

		Company tempCom = new Company();
		tempCom.setName(company_title);
		ArrayList<DayTick> tempDayTick = new ArrayList<DayTick>();

		for (int i = 0; i < filesLength; i++) {
			ArrayList<Tick> t = ReadDayCompanyData(data_folder_path,
					company_title, i);
			DayTick temp = new DayTick();
			temp.makeDayTick(t);
			if (!tempDayTick.isEmpty()) {
				temp.calBType(tempDayTick.get(i - 1));
			}
			tempDayTick.add(temp);
		}
		tempCom.setCandle(tempDayTick);
		return tempCom;
	}

	public BasicInformation readBasicInformation(String Filepath) {
		BasicInformation basic = new BasicInformation();
		ArrayList<String> temp = new ArrayList<String>();
		String line;
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(Filepath));
			while ((line = buffer.readLine()) != null) {
				temp.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		basic.setIPaddress(temp.get(0));
		basic.setPort(Integer.parseInt(temp.get(1)));
		if (temp.size() < 4 || temp.get(3).equals("0"))
			basic.setFeq(1);
		else
			basic.setFeq((int) (1000 / Integer.parseInt(temp.get(2))));
		basic.setCompany(Integer.parseInt(temp.get(3)));
		return basic;

	}
}
