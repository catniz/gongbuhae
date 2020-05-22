import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Woody SY
 * Date: 22/05/2020
 * Time: 5:45 오후
 * Copyright Coupang. All rights reserved.
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int currentLength = 0;

        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if(map.containsKey(c)) {
                currentLength=Math.min(i-map.get(c), currentLength+1);
            } else {
                currentLength++;
            }
            map.put(c, i);
            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }
}
