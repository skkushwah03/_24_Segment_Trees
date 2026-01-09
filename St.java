package _24_SegmentTrees;

public class St {
    static int tree[];

    // Initialize segment tree array
    public static void init(int n) {
        tree = new int[4 * n];
    }

    // Build Segment Tree
    public static int buildST(int arr[], int i, int start, int end) {
        if (start == end) {
            tree[i] = arr[start];
            return tree[i];
        }

        int mid = (start + end) / 2;
        buildST(arr, 2 * i + 1, start, mid);     // left child
        buildST(arr, 2 * i + 2, mid + 1, end);   // right child

        tree[i] = tree[2 * i + 1] + tree[2 * i + 2];
        return tree[i];
    }

    // Utility function to get sum in a given range [qi, qj]
    public static int getSumUtil(int i, int si, int sj, int qi, int qj) {
        // No overlap
        if (qj < si || qi > sj) {
            return 0;
        }

        // Complete overlap
        if (qi <= si && qj >= sj) {
            return tree[i];
        }

        // Partial overlap
        int mid = (si + sj) / 2;
        int left = getSumUtil(2 * i + 1, si, mid, qi, qj);
        int right = getSumUtil(2 * i + 2, mid + 1, sj, qi, qj);
        return left + right;
    }

    // Query function
    public static int getSum(int arr[], int qi, int qj) {
        int n = arr.length;
        return getSumUtil(0, 0, n - 1, qi, qj);
    }
    public static void updateUtil(int i,int si,int sj,int idx,int diff){
        if(idx>=sj||idx<=si){
            return;
        }
        tree[i]+=diff;
        if(si!=sj){
            int mid=(si+sj)/2;
            updateUtil(2*i+1, si, mid, idx, diff); 
            updateUtil(2*i+2, mid+1, sj, idx, diff);
        }
    }
    public static void update(int arr[],int idx,int newval){
        int n=arr.length;
        int diff=newval-arr[idx];
        arr[idx]=newval;
        updateUtil(0,0, n-1, idx, diff);
    }

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 6, 7, 8};
        int n = arr.length;

        init(n);
        buildST(arr, 0, 0, n - 1);

        // Query sum from index 2 to 5 (3+4+5+6 = 18)
        System.out.println("Sum of range [2,5] = " + getSum(arr, 2, 5));
        update(arr, 2, 2);
        System.out.println(getSum(arr, 2, 5));
}
}
