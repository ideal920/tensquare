package util;

import java.util.Random;

public class CheckCodeUtils {

    private static final String[] ARR = {"0","1","2","3","4","5","6","7","8","9"};

    private static final Random random =new Random();

    //生成指定长度的验证码
    public static String generateCheckcode(int length){

        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < length ;i++){
            sb.append(ARR[random.nextInt(ARR.length)]);
        }

        System.out.println(sb.toString());

        return sb.toString();
    }

}
