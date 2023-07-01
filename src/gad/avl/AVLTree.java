package gad.avl;

import java.util.HashSet;
import java.util.Set;

public class AVLTree {
    private AVLTreeNode root = null;

    public AVLTree() {
    }

    public AVLTreeNode getRoot() {
        return root;
    }

    public void setRoot(AVLTreeNode root) {
        this.root = root;
    }

    public int height() {
        if (root != null) {
            return root.height();
        }
        return 0;
    }

    public boolean validAVL() {
        if (root == null) {
            // Ein leerer Baum gilt als valider AVL-Baum
            return true;
        }
        return checkNode(root) && !checkCycles(root, new HashSet<>());
    }

    private boolean checkNode(AVLTreeNode node) {
        if (node == null) {
            // Ein leerer Knoten gilt als valider AVL-Baum
            return true;
        }

        // Überprüfung der Balance-Bedingung
        int leftHeight = (node.getLeft() != null) ? node.getLeft().height() : 0;
        int rightHeight = (node.getRight() != null) ? node.getRight().height() : 0;
        int balance = rightHeight - leftHeight;
        if (balance != node.getBalance()) {
            return false;
        }

        // Überprüfung der Balance-Betrag-Bedingung
        if (Math.abs(balance) > 1) {
            return false;
        }

        // Überprüfung der Schlüssel-Bedingungen
        if (node.getLeft() != null && node.getKey() < node.getLeft().getMaxKey()) {
            return false;
        }
        if (node.getRight() != null && node.getKey() > node.getRight().getMinKey()) {
            return false;
        }

        // Rekursive Überprüfung der Bedingungen für die linken und rechten Teilbäume
        boolean leftValid = checkNode(node.getLeft());
        boolean rightValid = checkNode(node.getRight());

        return leftValid && rightValid;
    }

    private boolean checkCycles(AVLTreeNode node, Set<AVLTreeNode> visited) {
        if (node == null) {
            return false;
        }
        if (visited.contains(node)) {
            return true;
        }
        visited.add(node);
        return checkCycles(node.getLeft(), visited) || checkCycles(node.getRight(), visited);
    }

    private int minKey() {
        AVLTreeNode current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getKey();
    }

    private int maxKey() {
        AVLTreeNode current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getKey();
    }


    public void insert(int key) {
    }

    public boolean find(int key) {
        return false;
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @return der Baum im Graphiz-Format
     */
    private String dot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {" + System.lineSeparator());
        if (root != null) {
            root.dot(sb);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return dot();
    }
}