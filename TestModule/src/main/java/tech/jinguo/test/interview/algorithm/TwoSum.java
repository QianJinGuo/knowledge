package tech.jinguo.test.interview.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和（LeetCode #1）
 * 给定一个整数数组 nums 和一个整数目标值 target，
 * 请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。
 *
 * 思路：使用哈希表，时间复杂度 O(n)，空间复杂度 O(n)
 */
public class TwoSum {

    /**
     * 哈希表解法：遍历数组，对每个元素 x，查找 target - x 是否在哈希表中
     */
    public int[] twoSum(int[] nums, int target) {
        // key: 数组元素值, value: 元素下标
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        TwoSum solution = new TwoSum();

        int[] nums1 = {2, 7, 11, 15};
        System.out.println(Arrays.toString(solution.twoSum(nums1, 9)));   // [0, 1]

        int[] nums2 = {3, 2, 4};
        System.out.println(Arrays.toString(solution.twoSum(nums2, 6)));   // [1, 2]

        int[] nums3 = {3, 3};
        System.out.println(Arrays.toString(solution.twoSum(nums3, 6)));   // [0, 1]
    }
}
