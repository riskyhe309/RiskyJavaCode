
import java.io.IOException;
import study.CLibrary;
import com.sun.jna.Native;


public class Split_Data_NLPIR {
	
	public static String split(String line) throws IOException {

		CLibrary instance = (CLibrary) Native.loadLibrary(System.getProperty("user.dir") + "\\source\\NLPIR",
				CLibrary.class);
		int init_flag = instance.NLPIR_Init("", 1, "0");
		String resultString = null;
		if (0 == init_flag) {
			resultString = instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！\n" + resultString);
			return null;
		}

		String sInput = line;

		try {
			resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
		
		System.out.println("分词结果为：\n " + resultString);
	} catch (Exception e) {
		//System.out.println("错误信息：");
		e.printStackTrace();
	}
		return resultString;
	}

}
