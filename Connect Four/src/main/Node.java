package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node {
    int value;
    Integer col;
    List<Node> children;
    Node(){
        children = new ArrayList<>();
    }
    public void addChild(Node child){
        children.add(child);
    }
    public void setCol(Integer col) {
        this.col = col;
    }
    public Integer getCol() {
        return col;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public List<Node> getChildren() {
        return children;
    }
    public void printTree(){
        Node max = getChildren().get(0);
        boolean root = true;
        for (int i = 1; i < getChildren().size(); i++) {
            if (getChildren().get(i).getValue() > max.getValue()){
                max = getChildren().get(i);
            }
        }
        this.setValue(max.getValue());
        this.setCol(max.getCol());
        if (this == null)
            return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(this);
        int j = 0;
        while (!queue.isEmpty()){
            int n = queue.size();
            j++;
            System.out.println("Level " + j +" ");
            int count = 7;
            while (n > 0){
                Node p = queue.peek();
                queue.remove();
                count--;
                if (p != null){
                    System.out.print(p.getValue() + " ");
                    int size = p.getChildren().size();
                    for (int i = 0; i < size; i++) {
                        queue.add(p.getChildren().get(i));
                    }
                    for (int i = 0; i < 7 - size; i++) {
                        queue.add(null);
                    }
                }
                n--;
                if (count == 0 ){
                    System.out.println();
                    count = 7;
                }
            }
            if (queue.peek() == null)
                break;
            if (root){
                root = false;
                System.out.println();
            }
        }
    }

}
