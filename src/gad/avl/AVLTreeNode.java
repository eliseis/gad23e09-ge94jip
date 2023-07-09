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
    private boolean findNode(AVLTreeNode node, int value) {
        if (node == null) {
            return false;
        }

        if (node.getKey() == value) {
            return true;
        } else if (value < node.getKey()) {
            return findNode(node.getLeft(), value);
        } else {
            return findNode(node.getRight(), value);
        }
    }
    public boolean FindNode(int value) {
        return findNode(this,value);
    }

    public void insert(int key) {
        if (key < this.key) {
            if (left == null) {
                left = new AVLTreeNode(key);
            } else {
                left.insert(key);
            }
        } else {
            if (right == null) {
                right = new AVLTreeNode(key);
            } else {
                right.insert(key);
            }
        }
        updateBalance(this);
        balance(this);
    }
    private void updateBalance(AVLTreeNode node) {
        if (node != null) {
            int leftHeight = node.getLeft().height();
            int rightHeight = node.getRight().height();
            node.setBalance(rightHeight - leftHeight);

            updateBalance(node.getLeft());
            updateBalance(node.getRight());
        }
    }
    private AVLTreeNode balance(AVLTreeNode node) {
        int balance = node.getBalance();
        if (balance < -1) {
            if (node.getLeft().getBalance() > 0) {
                // Doppelrotation: Links-Rechts
                node.setLeft(rotateLeft(node.getLeft()));
                rotateRight(node.getLeft());
            }
            // Einfachrotation: Rechts
            return rotateRight(node);
        } else if (balance > 1) {
            if (node.getRight().getBalance() < 0) {
                // Doppelrotation: Rechts-Links
                node.setRight(rotateRight(node.getRight()));
                rotateLeft(node.getRight());
            }
            // Einfachrotation: Links
            return rotateLeft(node);
        }

        // Keine Rebalancierung erforderlich
        return node;
    }
    private AVLTreeNode rotateLeft(AVLTreeNode node) {
        AVLTreeNode newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        updateBalance(node);
        updateBalance(newRoot);

        return newRoot;
    }
    private AVLTreeNode rotateRight(AVLTreeNode node) {
        AVLTreeNode newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        updateBalance(node);
        updateBalance(newRoot);

        return newRoot;
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
        Set<AVLTreeNode> V = new HashSet<>();
        AVLTreeNode current = this;
        while (current.getLeft() != null) {
            current = current.getLeft();
            if (V.contains(current)){
                return current.getKey();
            }
            V.add(current);
        }
        return current.getKey();
    }

    private int maxKey() {
        Set<AVLTreeNode> V = new HashSet<>();
        AVLTreeNode current = this;
        while (current.getRight() != null) {
            current = current.getRight();
            if (V.contains(current)){
                return current.getKey();
            }
            V.add(current);
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