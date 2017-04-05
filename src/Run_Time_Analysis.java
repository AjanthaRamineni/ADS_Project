import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Heap_Tree
{
	int parent, left, right;
	void build_min_heap(ArrayList<Data_Node> node_list)
	{
		int heap_size;
		heap_size = node_list.size();
		for (int i = ((heap_size/2) -1) ; i>=0; i--)
			min_heapify(node_list,i,heap_size);
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
		Data_Node root;
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
			//Constructing Tree
			data_node3.left = data_node1;
			data_node3.right= data_node2;
			//Tree  Construction Completed
			temp_freq_table.add(data_node3);
			int parent = (temp_freq_table.size()/2)-1;
			int i = temp_freq_table.size()-1;
			while(i>=1 && temp_freq_table.get(parent).count > temp_freq_table.get(i).count)
			{
				Collections.swap(temp_freq_table, i, parent);
				i = parent;
				//Index Vs Size
				parent = ((i+1)/2)-1;
			}
			if(temp_freq_table.size()==1)
				root = data_node3;

		}

	}
}
class pairing_Heap_Node{
	Data_Node pair_Node;
	ArrayList<pairing_Heap_Node> pair_children;
	pairing_Heap_Node(){
		pair_Node=new Data_Node();
		pair_children=new ArrayList<pairing_Heap_Node>();
	}

}
class pair_Heap{
	pairing_Heap_Node pair_root;
	int pair_size;
   public pair_Heap() {
	// TODO- Auto-generated constructor stub
	   pair_root=null;
	   pair_size=0;
   }
   void insert_Pair_Element(pairing_Heap_Node Addval){
	   if(pair_root==null) pair_root=Addval;
	   else if(pair_root.pair_Node.count < Addval.pair_Node.count){
		   pair_root.pair_children.add(Addval);
	   }
	   else{
		   	Addval.pair_children.add(pair_root);
		   pair_root = Addval;
	   }
	   pair_size++;
   }
   Data_Node extract_Min(){
	   Data_Node minN=pair_root.pair_Node;
	   ArrayList<pairing_Heap_Node> temp=new ArrayList<pairing_Heap_Node>();
	   for(int i=pair_root.pair_children.size()-1;i>0;i=i-2){
		   if(pair_root.pair_children.get(i).pair_Node.count > pair_root.pair_children.get(i-1).pair_Node.count){
			   temp.add(pair_root.pair_children.get(i-1));
			   pair_root.pair_children.get(i-1).pair_children.add(pair_root.pair_children.get(i));
		   }
		   else{
			   temp.add(pair_root.pair_children.get(i));
			   pair_root.pair_children.get(i).pair_children.add(pair_root.pair_children.get(i-1));
		   }
		   pair_root.pair_children.remove(pair_root.pair_children.size()-1);
		   pair_root.pair_children.remove(pair_root.pair_children.size()-1);
	   }
	   if(pair_root.pair_children.size()>0){
		   temp.add(pair_root.pair_children.get(pair_root.pair_children.size()-1));
		   pair_root.pair_children.remove(pair_root.pair_children.size()-1);
	   }
	   pairing_Heap_Node temp_root = null;
	   if(temp.size() > 0){
		   temp_root=temp.get(temp.size()-1);
		   for(int i=temp.size()-2;i>=0;i--){
			   if(temp.get(i).pair_Node.count > temp_root.pair_Node.count)
				   temp_root.pair_children.add(temp.get(i));
			   else{
				   temp.get(i).pair_children.add(temp_root);
				   temp_root=temp.get(i);
			   }
		   }
	   }
	   this.pair_root=temp_root;
	   pair_size--;
	   return minN;
   }
   void build_tree_using_pairing_heap(ArrayList<Data_Node> pairing_list){

	   Data_Node data1=new Data_Node();
	   Data_Node data2=new Data_Node();


	   for(int i=0;i<pairing_list.size();i++){
		   Data_Node temp=new Data_Node();
		   pairing_Heap_Node phNode_Temp=new pairing_Heap_Node();
		   temp.key_val = pairing_list.get(i).key_val;
		   temp.count=pairing_list.get(i).count;
		   phNode_Temp.pair_Node=temp;
		   insert_Pair_Element(phNode_Temp);

	   }
	   while(pair_size>1){
		   pairing_Heap_Node pheap=new pairing_Heap_Node();
		   Data_Node data3=new Data_Node();
		   data1 = extract_Min();
		   data2 = extract_Min();
		   long count=data1.count+data2.count;
		   data3.count=count;
		   data3.key_val=-1;
		   pheap.pair_Node=data3;
		   insert_Pair_Element(pheap);
	   }


   }

}

//Calculating Time
public class Run_Time_Analysis {
	public static void main(String[] args) throws IOException {
		File file = new File(args[0]);
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
		ArrayList<Data_Node> binary_node_list = new ArrayList<Data_Node>();
		ArrayList<Data_Node> Pairing_node_list = new ArrayList<Data_Node>();
		for(Map.Entry<Integer,Long> entry: hash.entrySet())
		{
			Data_Node dt_node = new Data_Node();
			dt_node.key_val = entry.getKey();
			dt_node.count = entry.getValue();
			binary_node_list.add(dt_node);
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
			heap.build_tree_using_binary_heap(binary_node_list);
		}
		long stop_Time = System.currentTimeMillis();
		System.out.println(stop_Time - start_Time+"Time Calculation   ");

		//Copying Same data of Binary Heap as the input value is one and the same

		ArrayList<Data_Node> fourWay_node_list = new ArrayList<Data_Node>(binary_node_list);
		Data_Node temp = new Data_Node();
		for(int i1=0;i1<3;i1++)
			fourWay_node_list.add(0,temp);

		four_Way_Heap four_heap = new four_Way_Heap();
		long startTime1 = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){    //run 10 times on given data set
			four_heap.build_tree_using_fourWay_heap(fourWay_node_list);
		}
		long stopTime1 = System.currentTimeMillis();
		System.out.println(stopTime1-startTime1+" MilliSec");

		ArrayList<Data_Node> pair_node_list = new ArrayList<Data_Node>(binary_node_list);
		pair_Heap pair_heap = new pair_Heap();
		long startTime2 = System.currentTimeMillis();
		for(int i = 0; i < 10; i++){    //run 10 times on given data set
			pair_heap.build_tree_using_pairing_heap(pair_node_list);
		}
		long stopTime2 = System.currentTimeMillis();
		System.out.println(stopTime2-startTime2+" MilliSec");

	}
}
