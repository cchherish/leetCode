package LinerTable;

import com.sun.deploy.util.ArrayUtil;
import com.sun.xml.internal.bind.v2.model.util.ArrayInfoUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.w3c.dom.ls.LSException;

import java.lang.reflect.Array;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

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
        LinerTable al =  new LinerTable();
//        System.out.println(al.letterCombinations1("2345"));
//        System.out.println(al.threeSum(new int[]{-1,0,1,2,-1,-4}));
//        al.letterCombinations("23454452356777");
//        al.letterCombinations1("23454452356777");
//        System.out.println(al.isValid("()[]{}"));
//        al.nextPermutation(new int[]{1,1,5});
        System.out.println(al.longestValidParentheses("()(())"));
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

    /**
     * 两个增序数组的中位数
     * point:二分查找
     * 注意边界点
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //四指针，分别指向两个数组的首尾
        int[] res = new int[nums1.length+nums2.length];
//        System.arraycopy(nums1,0,res,0,nums1.length);
//        System.out.println(",,");
//        System.arraycopy(nums2,0,res,nums1.length,nums2.length);
        Arrays.sort(res);
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

    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length){
            int[] temp =nums2;
            nums2 = nums1;
            nums1 =temp;
        }
        int m = nums1.length;
        int n = nums2.length;
        int totalNum = (m+n+1)/2;
        int left = 0;
        int right = nums1.length;
        while (left<right){
            //num1[i]>num2[j]&& num1[i-1]
            int i = left +(right-left)/2;
            int j = totalNum - i;
            if(nums1[i-1]>nums2[j]){
                //下一轮搜索  [left,i-1]
                right = i;
            }else{
                //下一轮搜索 [i,right]
                //[left,right]中只有2个元素时,即right = left+1，会进入死循环，因为 i= (left+right) = (2*left+1) = left
                //使i = (left+right+1)/2 即可 i= left +1 ==> left = i = left +1 =right ，跳出循环
                left = i;
            }
            //。。
        }

        return 1.0;
    }


    /**
     * 最长回文子串
     */
    //暴力解法
    //T: O(n^3)
    // S: O(1)
    public String longestPalindrome(String s) {
        int len =s.length();
        int begin = 0;
        int maxlen = 1;
        char[] array = s.toCharArray();
        for(int i =0;i<len-1;i++){
            for(int j = i+1;j<len;j++){
                if((j-i+1)>maxlen&&isPalindrom(array,i,j)){
                    maxlen = j-i+1;
                    begin = i;
                }
            }
        }
        return s.substring(begin,begin+maxlen);
    }
    public boolean isPalindrom(char[] array, int left,int rigth){
        while(left < rigth){
            if(array[left]!=array[rigth]){
                return false;
            }
            rigth--;
            left++;
        }
        return true;
    }

    //中心扩散
    public String longestPalindrome1(String s) {
        int len =s.length();
        int begin = 0;
        int maxlen = 1;
        char[] array = s.toCharArray();
        for(int i =0;i<len-1;i++){
            //分别以奇数和偶数进行中心扩散
            int oddLen = expendAroundCenter(array,i,i);
            int evenLen = expendAroundCenter(array,i,i+1);

            int curMaxLen  =  Math.max(oddLen,evenLen);
            if(curMaxLen>maxlen){
                maxlen =curMaxLen;
                begin = i;
            }
        }
        return s.substring(begin,begin+maxlen);
    }

    public int expendAroundCenter(char[] array,int left,int right){
        int len = array.length;
        int i = left;
        int j = right;
        while(i >= 0 &&j < len){
            if(array[i] == array[j]){
                i--;
                j++;
            }else{
                break;
            }

        }
        //退出循环，回文串不包括i和j指向的字符  所以 为 j-i+1-2 = j-i-1
        return j - i -1;
    }

//正则匹配
    public boolean isMatch(String s, String p) {
        for(char ch : s.toCharArray()){

        }
        return false;
    }

    //盛水最多的容器
    //自己的解法双层for
    public int maxArea(int[] height) {
        int maxNum = 0;

        for(int i =0;i<height.length -1;i++){
            for(int j = i+1 ; j< height.length;j++){
                int num = (j-i)*Math.min(height[i],height[j]);
                maxNum = num > maxNum ? num :maxNum;
            }
        }
        return maxNum;

    }

    //优化：双指针
    public int maxArea1(int[] height) {
        int left = 0;
        int right = height.length-1;
        int res = 0;
        while(left < right){
            int cur = (right - left)*Math.min(height[left],height[right]);
            res = cur > res? cur:res;
            if(height[left]>height[right]){
                right--;
            }else{
                left++;
            }
        }
        return res;
    }


    //三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        int len = nums.length;
        for(int i =0 ;i<nums.length;i++){
            if(nums[i]>0){
                break;
            }
            if(i >0 &&nums[i] == nums[i-1]){
                continue;
            }
            int L =i+1;
            int R =len -1;
            while(L < R){
                int sum = nums[i] + nums[L] + nums[R];
                if(sum == 0){
                    //记录
                    ans.add(Arrays.asList(nums[i],nums[L],nums[R]));

                    // 去重
                    while (L<R && nums[L] == nums[L+1]) {L++;}
                    // 去重
                    while (L<R && nums[R] == nums[R-1]) {R--;}
                    L++;
                    R--;
                }
                if(sum < 0){
                    L++;
                }else{
                    R--;
                }
            }
        }
        return ans;
    }


    public List<String> letterCombinations(String digits) {
        Long start  = System.currentTimeMillis();
        List<String> res = new ArrayList<>();
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};

        for(int i = 0;i<=digits.length() -1;i++){
            List<String> res1 = new ArrayList<>();
            String curStr =  phoneMap.get(digits.charAt(i));
            if(res.size() !=0){
                for(String str : res){
                    for(Character ch :curStr.toCharArray()){
                        res1.add(str + ch);
                    }
                }
            }else{
                //第一次
                for(Character ch :curStr.toCharArray()){
                    res1.add(ch.toString());
                }

            }
            res = res1;
        }
        System.out.println("first"+(System.currentTimeMillis() -start));
        return res;
    }

    public List<String> letterCombinations1(String digits) {
        long start = System.currentTimeMillis();
        List<String> res = new ArrayList<>();
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        if(digits.length() ==0 ){
            return res;
        }
        backtrack(res,phoneMap,0,new StringBuffer(),digits);

        System.out.println("second"+(System.currentTimeMillis()-start));
        return res;
    }

    public void backtrack(List<String> res,Map<Character, String> phoneMap,int index,StringBuffer combination,String digits){
        if (index == digits.length()) {
            res.add(combination.toString());
        }else{
            String cur  = phoneMap.get(digits.charAt(index));
            for(Character ch : cur.toCharArray()){
                combination.append(ch);
                backtrack(res,phoneMap,index+1,combination,digits);
                combination.deleteCharAt(index);
            }
        }

    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        ListNode second = head;
        for(int i =0 ;i<n ;i++){
            first = first.next;
        }
        while(first !=null){
            first =first.next;
            second = second.next;
        }
        second.next = second.next.next;

        return head;

    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null){
            return l2;
        }
        if(l2 ==null){
            return l1;
        }
        ListNode head =null;
        if(l1.val <l2.val){
             head = l1;
             l1 = l1.next;
        }else{
            head = l2;
            l2 = l2.next;
        }
        ListNode cur =head;
        while(l1!=null && l2!=null){
            if(l1.val <l2.val){
                cur.next =l1;
                l1 =l1.next;
                cur =cur.next;
            }else{
                cur.next =l2;
                l2 =l2.next;
                cur =cur.next;
            }
        }
        if(l1!=null){
            cur.next =l1;

        }
        if (l2!=null){
            cur.next =l2;

        }
        return head;
    }

    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        for(Character ch : s.toCharArray()){
            if(stack.isEmpty()){
                stack.push(ch);
            }else{
                Character ch1 = stack.peek();
                if(matchCh(ch1,ch)){
                    //能匹配
                    stack.pop();
                }else{
                    stack.push(ch);
                }
            }
        }
        if(stack.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean matchCh(char ch1,char ch2){
        if(ch1 == '(' && ch2 ==')'){
            return true;
        }
        if(ch1 == '{' && ch2 =='}'){
            return true;
        }
        if(ch1 == '[' && ch2 ==']'){
            return true;
        }
        return false;
    }

    //自己写的  不能生成(())(())
    public List<String> generateParenthesis(int n) {
        Map<Integer,Set<String>> map = new HashMap<>();
        Set<String> first = new HashSet<>();
        first.add("()");
        map.put(1,first);
        for(int i = 2 ; i<=n ;i++){
            Set<String> pre = map.get(i-1);
            Set<String> cur = new HashSet<>();
            for(String str : pre){
                cur.add(str+"()");
                cur.add("()"+str);
                cur.add("("+str+")");
            }
            map.put(i,cur);
        }
        List<String> res=  new ArrayList<>();
        res.addAll(map.get(n));
        return res;
    }

    //dfs+剪枝
    public List<String> generateParenthesis1(int n) {
        List<String> res = new ArrayList<>();
        dfs("",n,n,res);
        return res;
    }

    /**
     *
     * @param cur  当前的字符串
     * @param left （数量
     * @param right  ）数量
     * @param res  结果集
     */
    public void dfs(String cur,int left,int right,List<String> res){
        if(left ==0 && right ==0){
            res.add(cur);
            return;
        }
        if(left >right){
            return;
        }
        if (left>0){
            dfs(cur+"(",left-1,right,res);
        }
        if(right>0){
            dfs(cur+")",left,right-1,res);
        }
    }

//    public ListNode mergeKLists(ListNode[] lists) {
//        if(lists.length == 0){
//
//        }
//
//        if(lists.length %2 ==0){
//            mergeListOfTwo(lists);
//        }
//    }

    public ListNode[] mergeListOfTwo(ListNode[] lists){
        if(lists.length == 1){
            return lists;
        }
        if(lists.length%2 ==0){
            ListNode[] cur = new ListNode[lists.length/2];
            for(int i = 0;i<cur.length; i ++){
                mergeTwoLists(lists[2*i],lists[2*i+1]);
            }
        }
        if(lists.length%2 ==1){
            ListNode[] cur = new ListNode[lists.length/2+1];
        }
        return lists;
    }


    public void nextPermutation(int[] nums) {
        if(nums.length <= 1){
            return ;
        }
        boolean flag = false;
        int len  = nums.length -1;
        for(int i = nums.length -1 ;i > 0 ;i--){
            if(nums[i] > nums[i-1]){
                //从最后一个元素开始，找到第一个比num[i-1]小的数字
                for(int j = len; j> i-1;j--){
                    if(nums[j] > nums[i-1]){
                        //交换
                    }
                }
            }

        }
        //没有return，返回逆序
        if(!flag){
            Arrays.sort(nums);
        }
    }

    public int longestValidParentheses(String s) {
        //  ()(()
        Stack<Character> stack = new Stack<>();
        int count = 0;
        int max =0;
        boolean popFlag = false;
        for(char ch : s.toCharArray()){
            if(stack.isEmpty()){
                stack.push(ch);
                if(ch == ')'){
                    count =0;
                }
            }else{
                char topCh = stack.peek();
                if(ch == ')'){
                    if(topCh == '('){
                        stack.pop();
                        count = count +2;
                        max = max> count ? max:count;
                    }else{
                        count = 0;
                    }
                }else{
                    stack.push(ch);
                }
            }

        }
        return max;
    }

}
