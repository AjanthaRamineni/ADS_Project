import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class decode_Node
{
	int key=-1;
	String code="";
	decode_Node left = null;
	decode_Node right = null;
}
class Huffmann_Tree
{
	decode_Node root = new decode_Node();
	void build_Huffmann_Tree(String CODETABLE)
	{

		try
		{
			FileReader f_read=new FileReader(CODETABLE);
			BufferedReader b_read=new BufferedReader(f_read);
			ArrayList<decode_Node> d_list=new ArrayList<decode_Node>();
			String input = new String();
			while((input=b_read.readLine())!=null){
				decode_Node dn = new decode_Node();
				String[] sSplit=input.split(" ");
				dn.key=Integer.parseInt(sSplit[0]);
				dn.code=sSplit[1];
				d_list.add(dn);
			}
			b_read.close();
			//Decoding content in code Table
			for(int i=0;i<d_list.size();i++){
				decode_Node node = root;
				String code=d_list.get(i).code;
				//System.out.println(code+" "+i+" "+code.length());
				for(int j=0;j<code.length();j++){
					if(code.charAt(j)=='1'){
						if(node.right == null)
							node.right = new decode_Node();
						node=node.right;
					}
					else {
						if(node.left == null)
							node.left=new decode_Node();
						node=node.left;
					}
				}
				node.key=d_list.get(i).key;
			}

		}
		catch(Exception e)
		{

		}
	}
	void decoder_file(String ENCODED_BIN) throws IOException
	{

			FileInputStream f_istream=new FileInputStream(ENCODED_BIN);
			String decode_Writer="decoded.txt";
			FileWriter f_write = new FileWriter(decode_Writer);
			BufferedWriter b_write = new BufferedWriter(f_write);
//			b_write.write("hello");
			byte[] bytes_buffer = new byte[f_istream.available()];
			f_istream.read(bytes_buffer);
			f_istream.close();
			StringBuffer sbuffer=new StringBuffer();
			for(int i=0;i<bytes_buffer.length;i++){
				byte b1 = bytes_buffer[i];
				String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
				sbuffer.append(s1);
			}
			decode_Node temp=root;
			long l=sbuffer.length();
			for(int i = 0; i < l; i++)
			{

				if(sbuffer.charAt(i)=='0'){
					temp=temp.left;
				}
				else temp=temp.right;
				if(temp.key != -1)
				{
					StringBuilder content = new StringBuilder();
					content.append(temp.key);
					content.append("\n");
//					System.out.println(content);
					b_write.write(content.toString());
					temp = root;
				}
//				System.out.println("Closing File "+i);
			}
//			System.out.println("Closing File :P");
			b_write.close();

	}
}

public class decoder  {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String BINFILE=args[0];
		String CODETABLE=args[1];
		String ENCODED_BIN=args[0];

		Huffmann_Tree h_tree=new Huffmann_Tree();
		h_tree.build_Huffmann_Tree(CODETABLE);
		long startTime = System.currentTimeMillis();
		h_tree.decoder_file(ENCODED_BIN);
		long stopTime = System.currentTimeMillis();
		System.out.println("time for decode:"+(stopTime-startTime)+" MilliSec");
//		File file1 = new File("file1.txt");
//		File file2 = new File("file2.txt");
//		boolean isTwoEqual = FileUtils.contentEquals(file1, file2);
	}
}
