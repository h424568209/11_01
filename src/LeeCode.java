import java.util.*;

public class LeeCode {
    /**
     * 使用栈进行存放每一个函数的函数名和函数的时间
     * @param n 函数
     * @param logs 链表
     * @return 链表中每个函数的生命周期
     */
    public static int[] exclusiveTime(int n, List<String> logs) {
        int []res = new int[n];
        if(logs.size() == 0){
            return res;
        }
        Stack<int[]> stack=  new Stack<>();
        for(int i = 0 ;i<logs.size();i++){
            String[]values = logs.get(i).split(":");
            int id = Integer.valueOf(values[0]);
            String action = values[1];
            int ts = Integer.valueOf(values[2]);
            if(action.equals("start")){
                stack.push(new int[]{id,ts});
            }else{
                int[] tem = stack.pop();
                int num =  ts +1 - tem[1];
                res[tem[0]] += num;
                if(!stack.isEmpty()){
                    res[stack.peek()[0]] -= num;
                }
            }
        }
        return res;
    }
    /**
     * 给定一个经过编码的字符串 返回它解码后的字符串 比如 x[a] 表示a出现了x次
     * @param s 经过编码的字符串
     * @return 解码后的字符串
     */
    private static String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        for(int i = 0 ; i < s.length(); i ++){
            if(s.charAt(i)==']'){
                String q = "";
                while(!stack.peek().equals("[")){
                    q = stack.pop()+q;
                }
                stack.pop();
                String num = "";
                while((!stack.isEmpty())&&(stack.peek().charAt(0)>='0'&&stack.peek().charAt(0)<='9')){
                    num = stack.pop()+num;
                }
                int x= Integer.valueOf(num);
                String t = "";
                for(int j = 0 ; j < x ;j ++){
                    t = t + q;
                }
                stack.push(t);
            }else{
                String str =  ""+s.charAt(i);
                stack.push(str);
            }
        }
        String aaa = "";
        while(!stack.isEmpty()){
            aaa = stack.pop()+aaa;
        }
        return aaa;
    }
    /**
     * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素）
     * 输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，
     * 这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
     *栈中存放元素的下标；访问的区间为两个数组的大小
     * @return  数组中下一个比当前元素大的数的集合
     */
    private static int[] nextGreaterElements(int[] nums) {
        int [] res = new int[nums.length];
        Arrays.fill(res,-1);
        Stack<Integer> stack = new Stack<>();
        int n = nums.length;
        for(int i = 0 ; i < 2 * n ; i ++){
            int num = nums[i%n];
            //当前元素大于栈顶（下标）所对应的数组中的元素 就更新res
            while(! stack.isEmpty() && num > nums[stack.peek()]){
                res[stack.pop()] = num;
            }
            if(i < n ){
                stack.push(i);
            }
        }
        return res;
}
    /**
     *将数组2中的第一个元素先入栈 如果当前元素小于栈顶元素 则将当前元素继续入栈
     * 如果当前元素大于栈顶元素，则将栈顶元素出栈，和当前元素在map中组成一对键值对
     * 最后进行遍历数组1，如果能在map中没找到对应的键值对就返回-1；否则就将元素添加到数组中；
     * @param nums1 数组2的子数组
     * @param nums2 没有重复元素的数组
     * @return 数组1中每个元素在数组2中的下一个比其大的值
     */
    private static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int []res = new int[nums1.length];
        Stack<Integer> stack = new Stack<>();
        Map<Integer,Integer> map = new HashMap<>();
        for(int num:nums2){
            while(!stack.isEmpty()&&num>stack.peek()){
                map.put(stack.pop(),num);
            }
            stack.push(num);
        }
        for(int i = 0 ;i < nums1.length;i++){
            res[i] = map.getOrDefault(nums1[i],-1);
        }
        return res;
    }

    public static void main(String[] args) {
        int []nums1 = {1,2,3,4};
        int []nums2 = {1,2,3,4,5};
        System.out.println(Arrays.toString(LeeCode.nextGreaterElement(nums1, nums2)));
        int []nums3 = {1,2,3,4,5,1};
        System.out.println(Arrays.toString(LeeCode.nextGreaterElements(nums3)));
        System.out.println(LeeCode.decodeString("3[a]2[bc]"));
        List <String> list =new ArrayList<>() ;
        list.add("0:start:0");
        list.add("1:start:2");
        list.add("1:end:5");
        list.add("0:end:6");
        System.out.println(Arrays.toString(LeeCode.exclusiveTime(2, list)));
    }
}