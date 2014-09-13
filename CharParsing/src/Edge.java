/*
 * 边类，用于表示chart parsing句法分析中各个活动或非活动边
 */
 public class Edge{
	String head, done, rest;
	int start, end;
	
	public Edge(String head, String done, String rest, int start, int end){
		this.head = head;
		this.done = done;
		this.rest = rest;
		this.start = start;
		this.end = end;
	}
	
	// 输出当前边
	public void print(){
		System.out.println(head + " -> " + done + " . " + rest);
	}
}