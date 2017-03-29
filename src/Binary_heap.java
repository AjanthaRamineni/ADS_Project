import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
class Data_Node
{
	int key_val;
	long count;
}
public class Binary_heap {
	public static void main(String[] args) {
		File file = new File("C:/Users/Ajantha/Desktop/Internship/sample_input_large.txt");
		FileInputStream fis = null;
		HashMap<Integer,Long> hash = new HashMap<Integer,Long>();
		try 
		{
			fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String str_content;
			int content;
			while ((str_content = br.readLine()) != null) 
			{
				str_content = str_content.replaceAll("\\s+","");
				content = Integer.parseInt(str_content);
				if(!hash.containsKey(content))
					hash.put(content, (long) 1);
				else
				{
					long count = hash.get(content);
					count++;
					hash.put(content,count);
				}
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		ArrayList<Data_Node> node_list = new ArrayList<Data_Node>();
		for(Map.Entry<Integer,Long> entry: hash.entrySet())
		{
			Data_Node dt_node = new Data_Node();
			dt_node.key_val = entry.getKey();
			dt_node.count = entry.getValue();
			node_list.add(dt_node);
//			System.out.println(entry.getKey()+"--->"+entry.getValue());
		}
	}
}
