package tech.jinguo.test.interview.algorithm;

/**
 * 二分查找及其变体
 * 面试中常见的二分查找变体：
 * 1. 标准二分查找
 * 2. 查找第一个等于目标值的元素
 * 3. 查找最后一个等于目标值的元素
 * 4. 查找第一个大于等于目标值的元素
 */
public class BinarySearch {

    /**
     * 标准二分查找
     * 时间复杂度 O(log n)，空间复杂度 O(1)
     *
     * @return 目标值的下标，不存在则返回 -1
     */
    public int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;  // 防止整数溢出
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个等于目标值的元素（处理数组中有重复元素的情况）
     *
     * @return 第一个等于 target 的下标，不存在则返回 -1
     */
    public int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // nums[mid] == target，检查是否是第一个
                if (mid == 0 || nums[mid - 1] != target) {
                    return mid;
                }
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个等于目标值的元素
     *
     * @return 最后一个等于 target 的下标，不存在则返回 -1
     */
    public int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // nums[mid] == target，检查是否是最后一个
                if (mid == nums.length - 1 || nums[mid + 1] != target) {
                    return mid;
                }
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于目标值的元素（lower_bound）
     *
     * @return 第一个 >= target 的下标，若所有元素都小于 target 则返回 nums.length
     */
    public int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();

        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("标准二分查找 target=7: " + bs.binarySearch(nums, 7));  // 6
        System.out.println("标准二分查找 target=11: " + bs.binarySearch(nums, 11)); // -1

        int[] nums2 = {1, 2, 2, 2, 3, 4, 5};
        System.out.println("第一个等于2的下标: " + bs.findFirst(nums2, 2));  // 1
        System.out.println("最后一个等于2的下标: " + bs.findLast(nums2, 2)); // 3
        System.out.println("第一个>=3的下标: " + bs.lowerBound(nums2, 3));   // 4
    }
}
