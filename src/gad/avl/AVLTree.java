package gad.avl;

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
        return root.validAVL();
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