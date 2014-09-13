/**
 * Created by RISKYHE on 2014/5/5.
 */
public class Atof {

    public static void main(String[] args) throws Exception {

        String str1 = "100.23";
        String str2 = "-100.23";
        String str3 = "100e-23";




    double r = StringTODouble(str1) + StringTODouble(str2) + StringTODouble(str3);
        System.out.println(r);

    }

    public static int StringToInt(String str) throws Exception {

        if (str.length() == 0){
            return 0;
        }

        int number = 0;
        char[] numChar = str.toCharArray();
        int star = 0;
        int end = numChar.length;
        char sign = '+';

        if (numChar[0] == '-') {
            sign = '-';
            star = 1;

        }
        if (numChar[0] == '+') {
            sign = '+';
            star = 1;
        }
        for (int i = star; i < end; i++) {

            if (numChar[i] > 57 || numChar[i] < 48) {

                throw new Exception("The String is Error");
            }

            number += (numChar[i] - 48) * Math.pow(10, end - i - 1);

        }
        if (sign == '-') {
            number = 0 - number;
        }
        return number;

    }


    public static double StringTODouble(String str) throws Exception {

        double result;
        char[] numChar = str.toCharArray();
        int star = 0;
        int end = numChar.length;
        // whether the num is positive
        char sign = '+';

        if (numChar[0] == '-') {
            sign = '-';
            star = 1;
        }
        if (numChar[0] == '+') {
            sign = '+';
            star = 1;
        }

        //find the point's location
        int location;
        boolean isNotContainsPoint = false;
        for (location = star; location < end; location++) {

            if (numChar[location] == '.') {
                break;
            }

            if (numChar[location] == 'e') {
                isNotContainsPoint = true;
                break;
            }

            if (numChar[location] > 57 || numChar[location] < 48) {

                throw new Exception("The String is Error");
            }
        }

        String tempStr1 = str.substring(star, location);
        String tempStr2;

        if (location >= end){
            tempStr2 = "";
        }
        else{
            tempStr2 = str.substring(location + 1, end);
        }


        int big = StringToInt(tempStr1);
        int small = StringToInt(tempStr2);


        if (isNotContainsPoint) {

            result = big * Math.pow(10, small);
        } else {

            result = big + small * Math.pow(10, -tempStr2.length());
        }


        if (sign == '-') {
            result = 0 - result;
        }

        return result;
    }
}
