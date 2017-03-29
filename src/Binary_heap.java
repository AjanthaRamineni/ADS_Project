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
class Heap_Tree
{
	int parent, left, right;
	void build_min_heap(ArrayList<Data_Node> node_list)
	{
		int heap_size;
		heap_size = node_list.size();
		for (int i = ((heap_size/2) -1) ; i>=0; i--)
			min_heapify(node_list,i,heap_size);
		for(int j=0;j<node_list.size();j++)
			System.out.println(node_list.get(j).key_val+"--->"+node_list.get(j).count);
	}
	void min_heapify(ArrayList<Data_Node> node_list, int i,int heap_size)
	{
		int min=i;
		int left = 2*i + 1;
		int right= 2*i + 2;
		if(left<heap_size && node_list.get(left).count < node_list.get(min).count )
			min = left;
		if(right<heap_size && node_list.get(right).count < node_list.get(min).count)
			min = right;
		if(min!=i)
		{
			Collections.swap(node_list, i, min);
			min_heapify(node_list,min,heap_size);
		}
	}
}
public class Binary_heap {
	public static void main(String[] args) {
		File file = new File("C:/Users/Ajantha/Desktop/Internship/myexample.txt");
		FileInputStream fis = null;
		HashMap<Integer,Long> hash = new HashMap<Integer,Long>();
		Heap_Tree heap = new Heap_Tree();
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
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		ArrayList<Data_Node> node_list = new ArrayList<Data_Node>();
		for(Map.Entry<Integer,Long> entry: hash.entrySet())
		{
			Data_Node dt_node = new Data_Node();
			dt_node.key_val = entry.getKey();
			dt_node.count = entry.getValue();
			node_list.add(dt_node);
		}
		System.out.println("Before Heapifying ");
		for(int j=0;j<node_list.size();j++)
			System.out.println(node_list.get(j).key_val+"--->"+node_list.get(j).count);
		System.out.println("After Heapifying");
		heap.build_min_heap(node_list);
	}
}
