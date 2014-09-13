import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by RISKYHE on 14-3-23.
 */
public class UserRelation {

	static String[] id;

	public static double[][] computRelation(double[][] result,
			LinkedHashMap<String, LinkedHashMap<String, Integer>> score) {
		Set<String> userKey;
		userKey = score.keySet();
		Iterator<String> it = userKey.iterator();

		id = new String[result.length];
		// 迭代求取各个用户的相关性
		int i = 0;
		while (it.hasNext()) {
			String key = it.next();
			LinkedHashMap<String, Integer> value = score.get(key);
			result[i] = userCompute(value, userKey, score);
			// 记录该用户ID
			id[i] = key;
			i++;
		}

		return result;
	}

	/**
	 * 计算某用户与其他所有用户之间的相关系数，用户与自己相关系数也计算，结果为1
	 * */
	private static double[] userCompute(
			LinkedHashMap<String, Integer> brandScore, Set<String> key,
			LinkedHashMap<String, LinkedHashMap<String, Integer>> score) 
	{
		
		double[] result = new double[key.size()];
		Iterator<String> temp = key.iterator();

		int index = 0;

		while (temp.hasNext()) {
			String tempKey = temp.next();
			LinkedHashMap<String, Integer> tempValue = score.get(tempKey);

			result[index] = computCosine(brandScore, tempValue);
			index++;

		}
		return result;
	}

	/**
	 * 计算余弦相关度，提取两个用户品牌的并集，若A有该商品，B没有,则B的评分默认为1
	 * */
	private static double computCosine(
			LinkedHashMap<String, Integer> brandScore,
			LinkedHashMap<String, Integer> tempValue) {
		
		double cosine;
		Set<String> brands = new HashSet<String>();

		Set<String> user1set = brandScore.keySet();
		Iterator<String> user1 = user1set.iterator();
		Set<String> user2set = tempValue.keySet();
		Iterator<String> user2 = user2set.iterator();

		while (user1.hasNext()) {
			brands.add(user1.next());
		}

		while (user2.hasNext()) {
			String str = user2.next();
			if (!brands.contains(str)) {
				brands.add(str);
			}
		}

		int[] user1value = new int[brands.size()];
		int[] user2value = new int[brands.size()];

		// 迭代brands集合求出用户相应的评分
		Iterator<String> setit = brands.iterator();

		int dex = 0;
		while (setit.hasNext()) {
			String brandsKey = setit.next();

			Integer value1 = brandScore.get(brandsKey);

			// 若没有评分，则默认为1
			if (value1 == null) {
				value1 = 1;
			}
			Integer value2 = tempValue.get(brandsKey);

			if (value2 == null) {
				value2 = 1;
			}
			user1value[dex] = value1;
			user2value[dex] = value2;

			dex++;
		}

		cosine = compute(user1value, user2value);
		brands.clear();
		return cosine;
	}

	/**
	 * 计算两个向量的余弦相关性
	 * 
	 * */
	private static double compute(int[] value1, int[] value2) {
		double result;

		// 两者之间乘积的和
		int total12 = 0;
		// value各项平方和
		int total1square = 0;
		int total2square = 0;

		for (int i = 0; i < value1.length; i++) 
		{

			total12 = total12 + (value1[i] * value2[i]);

			total1square = total1square + (value1[i] * value1[i]);
			total2square = total2square + (value2[i] * value2[i]);
		}

		result = total12 / (Math.sqrt( total1square) * Math.sqrt(total2square));
		if(Double.isNaN(result)){
			System.out.println(total12+"    "+total1square+"    "+total2square);
		}
		return result;
	}

	/**
	 * 返回与relation数组顺序一致的ID数组
	 * */
	public static String[] getArrayId() {
		return id;
	}
}
