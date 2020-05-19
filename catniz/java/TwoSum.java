import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Woody SY
 * Date: 19/05/2020
 * Time: 5:21 오후
 * Copyright Coupang. All rights reserved.
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for(int i=0; i<nums.length; i++) {
            if(map.containsKey(target-nums[i])) {
                return new int[] { map.get(target - nums[i]), i };
            }

            map.put(nums[i], i);
        }

        return new int[2];
    }
}
