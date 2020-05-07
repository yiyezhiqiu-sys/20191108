package com.mini.conf;

public class LineType {
    public static void main(String[] args) {

        System.out.println(3<<0);
       // java.util.Queue\
        String[] arr = new String[]{"a","b","c"};
        arr[0]=null;
        for (int i = 0; i <arr.length ; i++) {
            System.out.println(arr[i]);
        }



    }

    private void lincktable(ListNode node){

        node.val = node.next.val;
        node.next = node.next.next ;

    }

    public class ListNode<X>{
        X val ;
        ListNode<X> next;
        public ListNode(X val){
            this.val = val;
        }

    }
}
