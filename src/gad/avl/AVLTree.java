package gad.avl;

import java.util.HashSet;
import java.util.Set;

public class AVLTree {
    private AVLTreeNode root;

    public AVLTree() {
        root = null;
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
        return root.validAVL();
    }
    public void insert(int key) {
        if (root == null){
            root = new AVLTreeNode(key);
        }
        root = root.insert(key);
    }

    public boolean find(int key) {
        if (root == null){
            return false;
        }
        return root.findNode(key);
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