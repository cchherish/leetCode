package DisJonitSet;

public class SOlution {



    public int[] findRedundantConnection(int[][] edges) {
        return new int[]{1,1};
    }


    public void union(int[] parent,int i,int j){
        parent[find(parent,i)] = parent[find(parent,j)];
    }
    public int find(int[] parent ,int i){
        if(parent[i] == i){
            return i;
        }else {
            return find(parent,parent[i]);
        }
    }
}
