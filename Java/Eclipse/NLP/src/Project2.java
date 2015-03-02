import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Project2 {
	public static void main(String[] args) throws IOException{
		
		System.out.println("Please Input The sentence (If you want end this process ,please input: End):");
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		String qw=null;
//		qw=br.readLine();
		File file=new File("ce.txt");
		ArrayList<String> word=readTheDictionary(file);
//		if(qw.equals("End")){
//			System.exit(0);
//		}
		while(!(qw=br.readLine()).equals("End")){
			forwardMatch(qw,word);
			reverseMatch(qw,word);
		}

		}	
	
	
	/*
	 * ����ƥ��
	 * */
	private static void reverseMatch(String qw, ArrayList<String> word) {	
		StringBuffer sentence=new StringBuffer();
		String tempStr=qw.substring(0, qw.length());
		int count=0;
		boolean flag=false;
		String temp=null;
		while(tempStr.length()!=0){
			//ֻʣһ����ʱ������ֿ������ԣ�ƥ���ʱ�������Ӵ��������ַ�
			flag=false;
			for(int i=0;i<tempStr.length();i++){											
				temp=tempStr.substring(i,tempStr.length());
				if(i==tempStr.length()-1){		//ֻʣһ����	
					count++;
					sentence.insert(0, temp+"/");
					break;
					
				}
				for(int j=0;j<word.size();j++){
					if(temp.equals(word.get(j))){
						count++;
						sentence.insert(0, temp+"/");
						flag=true;
						break;
					}
				}
				if(flag){
					break;
				}
			}			
			tempStr=qw.substring(0, qw.length()-sentence.length()+count);	//��ǰʣ��δƥ����Ӵ�
		}
		sentence.deleteCharAt(sentence.length()-1);				//ɾ�����һ����/��
		System.out.print("The REVERSE spliting resulr is： ");
		System.out.println("       "+sentence.toString());	
	}

	/*
	 * ����ƥ��
	 * */
	private static void forwardMatch(String qw, ArrayList<String> word) {
		
		StringBuffer sentence=new StringBuffer();
		String tempStr=qw.substring(0, qw.length());
		int count=0;
		boolean flag=false;
		String temp=null;
		while(tempStr.length()!=0){
			//ֻʣһ����ʱ������ֿ������ԣ�ƥ���ʱ�������Ӵ��������ַ�
			flag=false;
			for(int i=tempStr.length();i>0;i--){											
				temp=tempStr.substring(0,i);
				if(i==1){		//ֻʣһ����	
					count++;
					sentence.append(temp+"/");
					break;
					
				}
				for(int j=0;j<word.size();j++){
					if(temp.equals(word.get(j))){
						count++;
						sentence.append(temp+"/");
						flag=true;
						break;
					}
				}
				if(flag){
					break;
				}
			}			
			tempStr=qw.substring(sentence.length()-count, qw.length());	//��ǰʣ��δƥ����Ӵ�
		}
		sentence.deleteCharAt(sentence.length()-1);				//ɾ�����һ����/��
		System.out.print("The FORWORD spliting resulr is ： ");
		System.out.println("       "+sentence.toString());
		
	}


	/*
	 * ��ȡ�ʵ䣬����ƥ��ʿ�
	 * */
	public static ArrayList<String> readTheDictionary(File file) throws IOException{
		ArrayList<String> words=new ArrayList<String>();
		String str=null;
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		while((str=br.readLine())!=null){
			String[] strs=str.split(",");
			//System.out.println(strs[0]);
			words.add(strs[0]);
		}
		return words;
		
	}
}

