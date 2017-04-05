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
class Data_Node
{
	int key_val=0;
	long count=0;
	Data_Node left = null;
	Data_Node right= null;
}
class code_Table
{
	int key=0;
	StringBuffer code;
}
class four_Way_Heap
{
	void build_four_Way_heap(ArrayList<Data_Node> fourWay_node_list)
	{
		int fourWay_heap_size = fourWay_node_list.size();

		for(int i=((fourWay_heap_size-1)/4)+2; i>=3; i--){
			fourWayHeapify(fourWay_node_list, i, fourWay_heap_size);
		}
//		for(int k=0;k<fourWay_node_list.size();k++)
//			System.out.print(fourWay_node_list.get(k).count+" ");
//		System.out.println("");
	}
	void fourWayHeapify(ArrayList<Data_Node> node,int i,int size)
	{
		int child1,child2,child3,child4;
		child1=4*(i-2);
		child2=4*(i-2)+1;
		child3=4*(i-2)+2;
		child4=4*(i-2)+3;
		int min=i;
//		if(i==3) System.out.println(node.get(child1).count+" "+node.get(child2).count
//				+" "+node.get(child3).count+" "+node.get(child4).count);
		if(child1< size && node.get(child1).count < node.get(min).count)
			min = child1;
		if(child2< size && node.get(child2).count < node.get(min).count)
			min = child2;
		if(child3< size && node.get(child3).count < node.get(min).count)
			min = child3;
		if(child4< size && node.get(child4).count < node.get(min).count)
			min = child4;
//		System.out.println("min="+node.get(min).count+":P");
		if(min!=i)
		{
			Collections.swap(node, i, min);
			fourWayHeapify(node,min,size);
		}
	}
	Data_Node fourWay_Extract_Min(ArrayList<Data_Node> node_list)
	{
		Data_Node min_node = new Data_Node();
		min_node = node_list.get(3);
//		System.out.println("min"+min_node.count);
		node_list.set(3 , node_list.get(node_list.size()-1));
		node_list.remove(node_list.size()-1);
		fourWayHeapify(node_list,3,node_list.size());
		return min_node;
	}
	Data_Node build_tree_using_fourWay_heap(ArrayList<Data_Node> freq_table) throws IOException
	{
//		System.out.println("nn in build tree");
		ArrayList<Data_Node> temp_freq_table = new ArrayList<Data_Node>(freq_table);
		build_four_Way_heap(temp_freq_table);
		Data_Node root = new Data_Node();
		while(temp_freq_table.size()>4)
		{
			Data_Node data_node1,data_node2,data_node3;
//			for(int k=0;k<temp_freq_table.size();k++)
//				System.out.print(temp_freq_table.get(k).count+" ");
//			System.out.println(" ");
			data_node1 = new Data_Node();
			data_node1 = fourWay_Extract_Min(temp_freq_table);
			data_node2 = new Data_Node();
			data_node2 = fourWay_Extract_Min(temp_freq_table);
			data_node3 = new Data_Node();
			data_node3.count = data_node1.count + data_node2.count;
			data_node3.key_val =  0;
			//Constructing Tree
			data_node3.left = data_node1;
			data_node3.right= data_node2;
			//Tree  Construction Completed
			temp_freq_table.add(data_node3);
			int parent = ((temp_freq_table.size()-1)/4)+2;
			int i = temp_freq_table.size()-1;
			while(i>=4 && temp_freq_table.get(parent).count > temp_freq_table.get(i).count)
			{
				Collections.swap(temp_freq_table, i, parent);
				i = parent;
				//Index Vs Size
				parent = (i/4)+2;
			}
			if(temp_freq_table.size()==4)
				root = data_node3;
		}


		return root;

	}
	void generate_code_table(Data_Node root,StringBuffer path,Data_Node parent,ArrayList<code_Table> code_res)
	{
		code_Table code_table = new code_Table();
//		StringBuffer left,right;
		if(root==null)
			return;
		if(parent!=null)
		{
			if(parent.left==root)
				path.append(0);
			else
				path.append(1);
		}
		if(root.left==null && root.right==null)
		{
			code_table.code = new StringBuffer(path);
//			System.out.println(code_table.code);
			code_table.key = root.key_val;
			code_res.add(code_table);
//			System.out.println(root.key_val+"--->"+path+"--->"+root.count);
			return ;
		}
		generate_code_table(root.left,path,root,code_res);
		path.deleteCharAt(path.length()-1);
		generate_code_table(root.right,path,root,code_res);
		path.deleteCharAt(path.length()-1);
	}
	void code_Table_Output(ArrayList<code_Table>code_res) throws IOException
	{
		FileWriter fw = new FileWriter("code_table.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		String content = "";
		for(int j=0;j<code_res.size();j++)
		{
//			System.out.println(code_res.get(j).code.toString());
			content+=code_res.get(j).key+" ";
			content+=code_res.get(j).code.toString()+"\n";
			bw.write(content);
			content="";
		}
		bw.close();

	}
	void encodeData(ArrayList<code_Table> code_res,String file_name) throws NumberFormatException, IOException
	{
		Map<Integer, StringBuffer> code_hash=new HashMap<Integer, StringBuffer>();
		for(int i=0;i<code_res.size();i++){
			code_hash.put(code_res.get(i).key,code_res.get(i).code);
		}
		FileOutputStream output=new FileOutputStream("encoded.bin");
		FileReader  input;

		try
		{
			input= new FileReader(file_name);
			BufferedReader breader = new BufferedReader(input);



			String S=new String();
		//String codeOP=new String();
			StringBuilder codeOP=new StringBuilder();
			while((S=breader.readLine())!=null)
			{
				int hash_index = Integer.parseInt(S);
				codeOP.append(code_hash.get(hash_index));
			}

			for(int i=0;i<codeOP.length();i=i+8){
				String tempS=codeOP.substring(i, i+8);
				int tempInt= Integer.parseInt(tempS, 2);
			//System.out.println(i);
				output.write(tempInt);
			}
			output.close();

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class encoder {
	public static void main(String[] args) throws IOException {
		File file = new File(args[0]);
//		System.out.println(args[0]);
		String file_name = args[0];
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
		for(Map.Entry<Integer,Long> entry: hash.entrySet())
		{
			Data_Node dt_node = new Data_Node();
			dt_node.key_val = entry.getKey();
			dt_node.count = entry.getValue();
			binary_node_list.add(dt_node);
		}
		ArrayList<Data_Node> fourWay_node_list = new ArrayList<Data_Node>(binary_node_list);
		Data_Node temp = new Data_Node();
		for(int i1=0;i1<3;i1++)
			fourWay_node_list.add(0,temp);
		four_Way_Heap four_heap = new four_Way_Heap();
		long startTime1 = System.currentTimeMillis();


		Data_Node root = four_heap.build_tree_using_fourWay_heap(fourWay_node_list);
		ArrayList<code_Table> code_res = new ArrayList<code_Table>();
		StringBuffer path= new StringBuffer("");
		four_heap.generate_code_table(root,path,null,code_res);
		four_heap.code_Table_Output(code_res);
		four_heap.encodeData(code_res,file_name);

		long stopTime1 = System.currentTimeMillis();
		System.out.println(stopTime1-startTime1+" MilliSec");

	}
}