/*
 * 产生式规则存储类，former为左部，latter为右部
 */
public class RulePair{
	String former, latter;
	
	public RulePair(String former, String latter){
		this.former = former;
		this.latter = latter;
	}
}