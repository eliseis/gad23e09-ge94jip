package gad.avl;

import java.util.Set;

public class AVLTreeNode {
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
        // Überprüfung der Balance-Bedingung
        int leftHeight = (left != null) ? left.height() : 0;
        int rightHeight = (right != null) ? right.height() : 0;
        int balance = rightHeight - leftHeight;
        if (balance != this.balance) {
            return false;
        }

        // Überprüfung der Balance-Betrag-Bedingung
        if (Math.abs(balance) > 1) {
            return false;
        }

        // Überprüfung der Schlüssel-Bedingungen
        if (left != null && key < left.maxKey()) {
            return false;
        }
        if (right != null && key > right.minKey()) {
            return false;
        }

        // Rekursive Überprüfung der Bedingungen für die Teilbäume
        boolean leftValid = (left != null) ? left.validAVL() : true;
        boolean rightValid = (right != null) ? right.validAVL() : true;

        return leftValid && rightValid;
    }

    private int minKey() {
        return (left != null) ? left.minKey() : key;
    }

    private int maxKey() {
        return (right != null) ? right.maxKey() : key;
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