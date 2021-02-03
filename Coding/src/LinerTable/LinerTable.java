package LinerTable;

import org.w3c.dom.ls.LSException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * @author cherish
 */
public class LinerTable {

    public static void main(String[] args) {
//        ListNode l1 = new ListNode(2);
//        l1.next = new ListNode(4);
//        l1.next.next = new ListNode(1);
//
//        ListNode l2 = new ListNode(5);
//        l2.next = new ListNode(6);
//        l2.next.next = new ListNode(4);
//        ListNode res = addTwoNumbers(l1,l2);
//        while(res != null){
//            System.out.println(res.val);
//            res = res.next;
//        }
//        System.out.println(lengthOfLongestSubstring("au"));
        System.out.println(findMedianSortedArrays(new int[]{1,2},new int[]{3,4}));
    }

    /**
    *@Des 1.求两数之和
    *@Author cherish
    *@Date 2021/2/2 13:48
    */
    //暴力解法,时间复杂度O(n)
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        for(int i = 0 ; i < nums.length - 1 ; i++){
            for(int j = i + 1 ; j < nums.length ; j++){
                if(nums[i] + nums[j] == target){
                    res[0] = i;
                    res[1] = j;
                    break;
                }
            }
        }
        return res;
    }
    //Hash表解法，时间复杂度O(1)
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0 ;  i < nums.length ; i++){
            if(map.containsKey(target - nums[i])){
                return new int[]{i,map.get(target - nums[i])};
            }else{
                map.put(nums[i],i);
            }
        }
        return null;
    }
    
    /**
    *@Des 2.求链表之和
    *@Author cherish
    *@Date 2021/2/2 15:22
    */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode res = null;
        int increase =0;
        while(l1 != null && l2 != null){
            if(head == null){
                head = res = new ListNode((l1.val +l2.val + increase)%10);
                increase = (l1.val +l2.val + increase)/10;
                l1 =l1.next;
                l2 = l2.next;
            }else{
                res.next = new ListNode((l1.val +l2.val + increase)%10 );
                increase = (l1.val +l2.val +increase)/10;
                res = res.next;
                l1 =l1.next;
                l2 = l2.next;
            }
        }
        while(l1 != null){
            res.next = new ListNode((l1.val +increase)%10);
            increase =(l1.val +increase)/10;
            res = res.next;
            l1 =l1.next;
        }
        while(l2 != null){
            res.next = new ListNode((l2.val +increase)%10);
            increase =(l2.val +increase)/10;
            res = res.next;
            l2 = l2.next;
        }
        if(increase !=0){
            res.next = new ListNode(increase);
        }
        return head;
    }

    /**
    *@Des 3. 无重复字符的最长子串
    *@Author cherish
    *@Date 2021/2/2 15:56
    */
    //自己的解法，
    public static int lengthOfLongestSubstring(String s) {
        if(s.length() ==0){
            return 0;
        }
        int l =0;
        int r =1;
        int max =1;
        while (r <= s.length()){
            String str = s.substring(l,r);
            Boolean flag = true;
            Map<Character,Integer> map = new HashMap<>();
            for(char ch : str.toCharArray()){
                if(map.containsKey(ch)){
                    flag = false;
                }else{
                    map.put(ch,1);
                }
            }
            if(flag){
                max = r-l>max ? r-l : max;
                r=r+1;
            }else{
                l++;
                r++;
            }
        }
        return max;
    }

    public static int lengthOfLongestSubstring2(String s){
        int left=0;
        int max = 0;
        HashMap<Character,Integer> map = new HashMap<>();

        /**
         1、首先，判断当前字符是否包含在map中，如果不包含，将该字符添加到map（字符，字符在数组下标）,
         此时没有出现重复的字符，左指针不需要变化。此时不重复子串的长度为：i-left+1，与原来的maxLen比较，取最大值；

         2、如果当前字符 ch 包含在 map中，此时有2类情况：
         1）当前字符包含在当前有效的子段中，如：abca，当我们遍历到第二个a，当前有效最长子段是 abc，我们又遍历到a，
         那么此时更新 left 为 map.get(a)+1=1，当前有效子段更新为 bca；
         2）当前字符不包含在当前最长有效子段中，如：abba，我们先添加a,b进map，此时left=0，我们再添加b，发现map中包含b，
         而且b包含在最长有效子段中，就是1）的情况，我们更新 left=map.get(b)+1=2，此时子段更新为 b，而且map中仍然包含a，map.get(a)=0；
         随后，我们遍历到a，发现a包含在map中，且map.get(a)=0，如果我们像1）一样处理，就会发现 left=map.get(a)+1=1，实际上，left此时
         应该不变，left始终为2，子段变成 ba才对。

         为了处理以上2类情况，我们每次更新left，left=Math.max(left , map.get(ch)+1).
         另外，更新left后，不管原来的 s.charAt(i) 是否在最长子段中，我们都要将 s.charAt(i) 的位置更新为当前的i，
         因此此时新的 s.charAt(i) 已经进入到 当前最长的子段中！
         */
        for(int i =0;i<s.length();i++){
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i))+1);
            }
            map.put(s.charAt(i) , i);
            max = max >= i -left+1 ? max : i -left+1;

        }
        return max;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //四指针，分别指向两个数组的首尾
        int[] res = new int[nums1.length+nums2.length];
        int n1 = 0;
        int n2 = 0;
        int i = 0;
        while (n1<nums1.length&&n2<nums2.length){
            if(nums1[n1]<nums2[n2]){
                res[i++]  = nums1[n1++];
            }else{
                res[i++]  = nums2[n2++];
            }
        }
        while(n1<nums1.length){
            res[i++]  = nums1[n1++];
        }
        while(n2<nums2.length){
            res[i++]  = nums2[n2++];

        }
        int length = res.length;
        if(length%2==0){
            int nu1 = res[length/2];
            int nu2 = res[length/2 -1];
            return  (res[length/2]+res[length/2 -1])/2.0;
        }else{
            return res[res.length/2];
        }
    }
}
