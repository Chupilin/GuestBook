package by.chupilin.web.guestbook.utils;

import java.util.Random;


public abstract class RandomStringUtil {
   public static String generate(int capacity) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz ".toCharArray();
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < capacity; i++) {
            char c = chars[random.nextInt(chars.length)];
            builder.append(c);
        }
        return builder.toString();
    }
}
