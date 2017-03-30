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
//		for(int j=0;j<node_list.size();j++)
//			System.out.println(node_list.get(j).key_val+"--->"+node_list.get(j).count);
	}
	void min_heapify(ArrayList<Data_Node> node_list, int i,int heap_size)
	{
		int min=i;
		int left = 2*i + 1;//Finding left child in heap
		int right= 2*i + 2;//Finding right child in heap
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
	Data_Node Extract_Min(ArrayList<Data_Node> node_list)
	{
		Data_Node min_node = new Data_Node();
		min_node = node_list.get(0);
		node_list.set(0 , node_list.get(node_list.size()-1));
		node_list.remove(node_list.size()-1);
		min_heapify(node_list,0,node_list.size());
		return min_node;
	}
	void build_tree_using_binary_heap(ArrayList<Data_Node> freq_table)
	{
		ArrayList<Data_Node> temp_freq_table = new ArrayList<Data_Node>(freq_table);
		build_min_heap(temp_freq_table);
		while(temp_freq_table.size()>1)
		{
			Data_Node data_node1,data_node2,data_node3;
//			for(int k=0;k<temp_freq_table.size();k++)
//				System.out.print(temp_freq_table.get(k).count+" ");
//			System.out.println(" ");
			data_node1 = new Data_Node();
			data_node1 = Extract_Min(temp_freq_table);
			data_node2 = new Data_Node();
			data_node2 = Extract_Min(temp_freq_table);
			data_node3 = new Data_Node();
			data_node3.count = data_node1.count + data_node2.count;
			data_node3.key_val =  0;
			temp_freq_table.add(data_node3);
			int parent = (temp_freq_table.size()/2)-1;
			int i = temp_freq_table.size()-1;
			while(i>1 && temp_freq_table.get(parent).count > temp_freq_table.get(i).count)
			{
				Collections.swap(temp_freq_table, i, parent);
				i = parent;
				//Index Vs Size
				parent = ((i+1)/2)-1;
			}
		}
	}
}
public class Binary_heap {
	public static void main(String[] args) {
		File file = new File("C:/Users/Ajantha/Desktop/Internship/sample_input_large.txt");
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
//		System.out.println("Before Heapifying ");
//		for(int j=0;j<node_list.size();j++)
//			System.out.println(node_list.get(j).key_val+"--->"+node_list.get(j).count);
//		System.out.println("After Heapifying");
//		heap.build_min_heap(node_list);
//		System.out.println("Extracting Minimum");
		long start_Time = System.currentTimeMillis();
		for(int i=0;i<10;i++)
		{
			heap.build_tree_using_binary_heap(node_list);
		}
		long stop_Time = System.currentTimeMillis();
		System.out.println(stop_Time - start_Time+"Time Calculation   ");
	}
}
