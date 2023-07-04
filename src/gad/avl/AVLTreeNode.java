package gad.avl;

import java.util.HashSet;
import java.util.Set;

public class    AVLTreeNode {
    private int key;
    private int balance = 0;
    private AVLTreeNode left = null;
    private AVLTreeNode right = null;

    public AVLTreeNode(int key) {
        this.key = key;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public int getBalance() {
        return balance;
    }

    public int getKey() {
        return key;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int height() {
        int leftHeight = (left != null) ? left.height() : 0;
        int rightHeight = (right != null) ? right.height() : 0;
        return Math.max(leftHeight, rightHeight) + 1;
    }
    public boolean validAVL() {
        Set<AVLTreeNode> visited = new HashSet<>();
        return checkNode(this, visited);
    }

    private boolean checkNode(AVLTreeNode node, Set<AVLTreeNode> visited) {
        if (node == null) {
            return true;
        }

        if (visited.contains(node)) {
            // Der Knoten wurde bereits besucht, es gibt einen Kreis im Baum
            return false;
        }

        visited.add(node);

        int leftHeight = (node.getLeft() != null) ? node.getLeft().height() : 0;
        int rightHeight = (node.getRight() != null) ? node.getRight().height() : 0;
        int balance = rightHeight - leftHeight;
        if (balance != node.getBalance()) {
            return false;
        }

        if (Math.abs(balance) > 1) {
            return false;
        }

        if (node.getLeft() != null && node.getKey() < node.getLeft().maxKey()) {
            return false;
        }
        if (node.getRight() != null && node.getKey() > node.getRight().minKey()) {
            return false;
        }

        return checkNode(node.getLeft(), visited) && checkNode(node.getRight(), visited);
    }

    private int minKey() {
        AVLTreeNode current = this;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getKey();
    }

    private int maxKey() {
        AVLTreeNode current = this;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getKey();
    }


    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @param sb der StringBuilder für die Ausgabe
     */
    public void dot(StringBuilder sb) {
        dotNode(sb, 0);
    }

    private int dotNode(StringBuilder sb, int idx) {
        sb.append(String.format("\t%d [label=\"%d, b=%d\"];%n", idx, key, balance));
        int next = idx + 1;
        if (left != null) {
            next = left.dotLink(sb, idx, next, "l");
        }
        if (right != null) {
            next = right.dotLink(sb, idx, next, "r");
        }
        return next;
    }

    private int dotLink(StringBuilder sb, int idx, int next, String label) {
        sb.append(String.format("\t%d -> %d [label=\"%s\"];%n", idx, next, label));
        return dotNode(sb, next);
    }
}