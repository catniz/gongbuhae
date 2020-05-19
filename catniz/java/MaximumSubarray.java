/**
 * Created with IntelliJ IDEA.
 * User: Woody SY
 * Date: 19/05/2020
 * Time: 6:13 오후
 * Copyright Coupang. All rights reserved.
 */
public class MaximumSubarray {
    public int nSquare(int[] nums) {
        int result = Integer.MIN_VALUE;

        for(int i=0; i<nums.length; i++) {
            int sum = 0;
            for(int j=i; j<nums.length; j++) {
                sum += nums[j];
                result = Math.max(result, sum);
            }
        }

        return result;
    }

    public int dp(int[] nums) {
        int result = nums[0];
        int before = nums[0];

        for(int i=1; i<nums.length; i++) {
            before = Math.max(before+nums[i], nums[i]);
            result = Math.max(result, before);
        }

        return result;
    }

    public int divideAndConquer(int[] nums) {
        return calc(nums, 0, nums.length-1);
    }

    private int calc(int[] nums, int l, int r) {
        if(l==r) {
            return nums[l];
        }

        int mid = (l+r)/2;
        int left = calc(nums, l, mid);
        int right = calc(nums, mid+1, r);

        int tmpSum = 0;
        int subLeftMaxSum = Integer.MIN_VALUE;
        for(int i=mid; i>=l; i--) {
            tmpSum+=nums[i];
            subLeftMaxSum = Math.max(subLeftMaxSum, tmpSum);
        }

        tmpSum=0;
        int subRightMaxSum = Integer.MIN_VALUE;
        for(int i=mid+1; i<=r; i++) {
            tmpSum+=nums[i];
            subRightMaxSum = Math.max(subRightMaxSum, tmpSum);
        }

        return Math.max(Math.max(left, right), subLeftMaxSum+subRightMaxSum);
    }
}
