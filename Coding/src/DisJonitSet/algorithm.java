package DisJonitSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class algorithm {

    public static void main(String[] args) {
        int[][] isConnexted = new int[][]{{1,1,0},{1,1,0},{0,0,1}};
//        int[][] isConnexted = new int[][]{{1,0,0,1},{0,1,1,0},{0,1,1,1},{1,0,1,1}};
        algorithm al = new algorithm();
        System.out.println(al.findCircleNum(isConnexted));
    }


    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        DisjiontSet ds = new DisjiontSet(n);
        for(int i = 0 ;i<n;i++){
            for(int j= 0;j<n;j++){
                if(isConnected[i][j] == 1){
                    ds.merge(i,j);
                }
            }
        }
        int[] self= ds.getSelf();
        Set<Integer> set = new HashSet<>();
        for(int i = 0;i<self.length;i++){
            set.add(ds.find(self[i]));
        }
        return set.size();
    }


}
