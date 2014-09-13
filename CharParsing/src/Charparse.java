import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Charparse {

	ArrayList<Edge> chartList = new ArrayList<Edge>(); // 存储非活动边
	ArrayList<Edge> activearcstList = new ArrayList<Edge>(); // 存储活动边
	ArrayList<RulePair> ruleList = new ArrayList<RulePair>(); // 存储句法规则

	public static void main(String[] args) {

		Charparse charparse = new Charparse();

		String file = "f://rule.txt";

		charparse.loadRule(file, "->"); // 读取句法规则

		charparse.parseUsingParser("f://sample.txt"); // 句法分析过程
	}

	/*
	 * 
	 * 读取分析文件，截取每一行进行分析
	 */
	private void parseUsingParser(String string) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(string));
			String str = null;

			while ((str = br.readLine()) != null) {
				charparsing(str);
			}
		} catch (IOException e) {
			System.out.println("发生" + e + "异常！");
		}

	}

	/*
	 * 句法分析主体，对str这个句子进行自底向上的分析
	 */
	private void charparsing(String str) {
		// TODO Auto-generated method stub
		// 去掉标点符号，并将句子切分成单词
		if (str.endsWith(".") || str.endsWith("!") || str.endsWith("?")
				|| str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		String[] words = str.toUpperCase().split("\\s");
		// aganda栈

		Stack<Edge> aganda = new Stack<Edge>();

		for (int i = 0; i < words.length; i++) {

			aganda.push(new Edge(words[i], words[i], "", i, i + 1)); // 向aganda中加入新边

			while (!aganda.isEmpty()) {
				Edge e = aganda.pop();
				/*
				 * 遍历每个产生式，对下面形式的每个规则：X->CX1...Xn，在activearcs中增加一条活动边：X->C º
				 * X1...Xn，位置为：p1-p2；X->C，把X加入agenda，位置为：p1-p2
				 */
				for (int j = 0; j < ruleList.size(); j++) {
					if (ruleList.get(j).latter.startsWith(e.head + " ")
							|| ruleList.get(j).latter.equals(e.head)) {

						String tempStr = ruleList.get(j).latter.substring(
								e.head.length()).trim();

						Edge newEdge = new Edge(ruleList.get(j).former, e.head,
								tempStr, e.start, e.end);
						if (tempStr.equals("")) { // 形如：X->C，把X加入agenda，位置为：p1-p2
							aganda.push(newEdge);
						} else { // 形如：X->CX1...Xn，在activearcs中增加一条活动边：X->C
									// ºX1...Xn，位置为：p1-p2；
							activearcstList.add(newEdge);
						}
					}// end if
				}// end for

				/*
				 * 边扩展对每个形式为：X->X1... º
				 * C...Xn的活动边，若它在p0-p1之间，则在activearcs中增加一条活动边：X->X1... C
				 * º...Xn，位置:p0-p2对每个形式为： X->X1... Xn º
				 * C的活动边，若它在p0-p1之间，则在agenda中增加一个成分：X，位置为：p0-p2
				 */

				// 加入非活动边
				chartList.add(e);
				for (int j = 0; j < activearcstList.size(); j++) {
					Edge tempEdge = activearcstList.get(j);
					if ((tempEdge.rest.startsWith(e.head + " ") || tempEdge.rest
							.equals(e.head)) && tempEdge.end == e.start) {

						String done = tempEdge.done + " " + e.head;
						String rest = tempEdge.rest.substring(e.head.length())
								.trim();

						Edge newEdge = new Edge(tempEdge.head, done, rest,
								tempEdge.start, e.end);

						if (rest.equals("")) { // 形如：对每个形式为：X->X1... º
													// C...Xn的活动边，若它在p0-p1之间，则在activearcs中增加一条活动边：X->X1...
													// C º...Xn，位置:p0-p2
							aganda.push(newEdge);
						} else { // 形如：对每个形式为：X->X1... º
									// C...Xn的活动边，若它在p0-p1之间，则在activearcs中增加一条活动边：X->X1...
									// C º...Xn，位置:p0-p2
							activearcstList.add(newEdge);
						}// end else
					}
				}// end for

			}// end while

		}// end for
		for (int j = 0; j < chartList.size(); j++) {
			Edge temp = chartList.get(j);
			if (temp.head.equals("S") && temp.rest.equals("")
					&& temp.start == 0 && temp.end == words.length) { // 规约为“S”
				System.out.println("句子"+str+"最终解析式为：");
				int deep = 0;
				printParse(j, deep);
				return;
			}// end if
		}// end for
		System.out.println("错误，句子"+str+"不能被解析！ ");
	}

	/*
	 * 输出句法分析结果
	 */
	private void printParse(int index, int deep) {
		// TODO Auto-generated method stub
		Edge temp = chartList.get(index);
		int length = deep; // 树的深度
		if (temp.head.equals(temp.done)) { // 形如：ART->ART,不打印
			return;
		}
		if (temp.rest.equals("")) {
			for (int i = 0; i < length; i++) {
				System.out.print("----");

			}
			temp.print();
		}

		/*
		 * 如s-> VP NP. stra=0,end=5=index_os;
		 * 从S的最后一个子边NP开始，往前循环遍历；在chartList中找head=NP的边，
		 * 且其end=index_os，设为edge；
		 * 递归打印edge。然后在index_os=edge.start；进行下一次循环
		 */
		int index_os = temp.end;
		String[] offspring = temp.done.split("\\s");
		for (int i = offspring.length - 1; i >= 0; i--) {
			for (int j = 0; j < chartList.size(); j++) {
				if (chartList.get(j).head.equals(offspring[i])
						&& chartList.get(j).end == index_os) {
					printParse(j, length + 1);
					index_os = chartList.get(j).start;
					break;
				}
			}

		}

	}

	/*
	 * 加载句法规则
	 */
	private void loadRule(String file, String string) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String str = null;
			String[] rule = null;

			while ((str = br.readLine()) != null) {

				rule = str.split(string);
				ruleList.add(new RulePair(rule[0].trim(), rule[1].trim()));
			}
		} catch (IOException e) {
			System.out.println("发生" + e + "异常！");
		}

	}

}
