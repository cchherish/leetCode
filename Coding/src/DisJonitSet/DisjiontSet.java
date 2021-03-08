package DisJonitSet;

public class DisjiontSet {

    private int[] self;
    private int[] rank;

    public int[] getSelf() {
        return self;
    }

    public void setSelf(int[] self) {
        self = self;
    }

    public int[] getRank() {
        return rank;
    }

    public void setRank(int[] rank) {
        rank = rank;
    }

    public DisjiontSet(int n){
        self = new int[n];
        rank = new int[n];
        for(int i = 0 ; i < n ; i++){
            self[i] = i;
            rank[i] = 1;
        }
    }

    /**
     * 访问根节点
     * @param x
     * @return
     */
    public int find(int x){
        if(self[x] == x){
            return x;
        }else {
            //父节点设置为根节点
            self[x] = find(self[x]);
            return self[x];
        }
    }

    /**
     * 合并
     * @param i
     * @param j
     */
    public void  merge(int i,int j){
        int  x = find(i);
        int  y = find(j);
        if(rank[x] <= rank[y]){
            self[x] = y;
        }
        else{
            self[y] = x;
        }
        if(x!=y && rank[x] ==rank[y]){
            rank[y]++;
        }
    }
}
