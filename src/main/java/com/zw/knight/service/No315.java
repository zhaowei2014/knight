package com.zw.knight.service;

import java.util.*;

/**
 * TODO:DOCUMENT ME!
 *
 * @author zw
 * @date 2020/7/11
 * 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
 */
public class No315 {
    public List<Integer> countSmaller(int[] nums) {
        for (nums) {

        }
    }
/**
 *
 */
    /**
     * 时间复杂度：我们梳理一下这个算法的流程，这里离散化使用哈希表去重，然后再对去重的数组进行排序，
     * 时间代价为O(nlogn)；初始化树状数组的时间代价是 O(n)；
     * 通过值获取离散化  id 的操作单次时间代价为O(logn)；
     * 对于每个序列中的每个元素，都会做一次查询  idid、单点修改和前缀和查询，总的时间代价为 O(nlogn)。
     * 故渐进时间复杂度为 O(nlogn)
     * 空间复杂度：这里用到的离散化数组、树状数组、哈希表的空间代价都是 O(n)，故渐进空间复杂度为 O(n).
     */
    class Solution {
        private int[] c;
        private int[] a;

        public List<Integer> countSmaller(int[] nums) {
            List<Integer> resultList = new ArrayList<>();
            discretization(nums);
            init(nums.length + 5);
            for (int i = nums.length - 1; i >= 0; --i) {
                int id = getId(nums[i]);
                resultList.add(query(id - 1));
                update(id);
            }
            Collections.reverse(resultList);
            return resultList;
        }

        private void init(int length) {
            c = new int[length];
            Arrays.fill(c, 0);
        }

        private int lowBit(int x) {
            return x & (-x);
        }

        private void update(int pos) {
            while (pos < c.length) {
                c[pos] += 1;
                pos += lowBit(pos);
            }
        }

        private int query(int pos) {
            int ret = 0;
            while (pos > 0) {
                ret += c[pos];
                pos -= lowBit(pos);
            }

            return ret;
        }

        private void discretization(int[] nums) {
            Set<Integer> set = new HashSet<>();
            for (int num : nums) {
                set.add(num);
            }
            int size = set.size();
            a = new int[size];
            int index = 0;
            for (int num : set) {
                a[index++] = num;
            }
            Arrays.sort(a);
        }

        private int getId(int x) {
            return Arrays.binarySearch(a, x) + 1;
        }
    }
    /*
* 首先[树状数组BIT](https://www.bilibili.com/video/BV1pE41197Qj/)
* 可以从右往左逆序方式遍历nums
  并将nums[i]放入对应的桶中，
  eg[5 2 6 1 1]
  因为最大值是6，开一个长度为7的桶数组(有一个无用索引0，这里写下标是1开始的)
  从右往左遍历到1，则++数组[1]，即数组[1 0 0 0 0 0]
  从右往左遍历到1，则++数组[1]，即数组[2 0 0 0 0 0]
  从右往左遍历到6，则++数组[6]，即数组[2 0 0 0 0 1]
  从右往左遍历到2，则++数组[2]，即数组[2 1 0 0 0 1]
  从右往左遍历到5，则++数组[5]，即数组[2 1 0 0 1 1]
* 这样通过数组从右往左遍历的过程，因为桶的设计是[1]~[max_element(nums)]
  则遍历到数字i时，需要++桶数组[i]，
  那么<i的桶统计了对于数字i在nums中右边小于数字i的个数的统计
  那么只需要前缀和的方式即可求出当前右侧小于当前数字的个数
  eg[5 2 6 1 1]
  从右往左遍历到5，则++数组[5]，即数组[2 1 0 0 1 1]
  那么小于5的个数就是数组[1]~数组[4]的区间和，即2+1+0+0=3
* 前缀和+要单点修改，树状数组就可以派上用场
* 但是有个地方注意，上面设置桶是按nums最大值来设置桶数组的长度
  但万一nums不是全为正数的数组，或者万一nums最大值是一个很大的数
  直接按照值=index的方式设置桶是不行的，一会mle，二会有很多空桶没有作用，且增加复杂度
* 所以采用离散化数组，即按数值从小到大来映射桶
  即[5 2 6 1 1]有用的数值只有[1 2 5 6]可以设置映射
  f[1]=1 f[2]=2 f[5]=3 f[6]=4即f[nums中数值]=BIT数组的索引index
  这样优化空间且不用担心负数的情况
*/
}
