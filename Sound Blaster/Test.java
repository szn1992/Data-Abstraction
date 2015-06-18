package phaseA;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * AVLTree must be subclass of BinarySearchTree<E> and must use inheritance 
 * and calls to superclass methods to avoid unnecessary duplication or copying
 * of functionality.
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override incCount method such that it creates AVLNode instances instead 
 *    of BSTNode instances.
 * 3. Do not "replace" the left and right fields in BSTNode with new left and 
 *    right fields in AVLNode. This will instead mask the super-class fields 
 *    (i.e., the resulting node would actually have four node fields, with 
 *    code accessing one pair or the other depending on the type of the
 *    references used to access the instance). Such masking will lead to
 *    highly perplexing and erroneous behavior. Instead, continue using the
 *    existing BSTNode left and right fields. Cast their values to AVLNode 
 *    whenever necessary in your AVLTree. Note: This may require many casts, 
 *    but that is o.k. given that our goal is to reuse methods from BinarySearchTree.
 * 4. Do not override dump method of BinarySearchTree & toString method of 
 * 	  DataCounter. They are used for grading. 
 * TODO: Develop appropriate JUnit tests for your AVLTree (TestAVLTree in 
 *    testA package).
 */
public class AVLTree<E> extends BinarySearchTree<E> {
	
	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	// return the height of node t, or -1 if null
	private int height(BSTNode t) {
		return t == null ? -1 : ((AVLNode)t).height;
	}
	
	public int getHeight(E data) {
		return helpGetHeight(overallRoot, data);
	}
	
	private int helpGetHeight(BSTNode root, E data) {
		if(root == null)
			return -1;
		if(comparator.compare(data, root.data) == 0)
			return height(root);
		else if (comparator.compare(data, root.data) > 0)
			return helpGetHeight(root.right, data);
		else
			return helpGetHeight(root.left, data);
	}
	
	@Override
    public void incCount(E data) {
    	overallRoot = insert(data, overallRoot);
    }
	
    public BSTNode insert(E data, BSTNode root) {
    	if(root == null) 
    		return new AVLNode(data);
    	
    	int cmp = comparator.compare(data, root.data);
    	if(cmp == 0) {
    		root.count++;
    	} else if(cmp < 0) {
    		root.left = insert(data, root.left);
    	} else {
    		root.right = insert(data, root.right);
    	}
    	return balance(root);
    }
    
	private BSTNode balance(BSTNode root) {
    	if(root == null)
    		return root;
    	if(height(root.left) - height(root.right) > 1) {
    		if(height(root.left.left) >= height(root.left.right))
    			root = singleRotateLeft(root);
    		else
    			root = doubleRotateLeft(root);
    	}
    	else if((height(root.right) - height(root.left)) > 1) {
			if(height(root.right.right) >= height(root.right.left))
    			root = singleRotateRight(root);
    		else
    			root = doubleRotateRight(root);
		}
    	((AVLNode)root).height = Math.max(height(root.left), height(root.right)) + 1;
    	return root;
    }
    
    private BSTNode singleRotateLeft(BSTNode root2) {
    	BSTNode root1 = root2.left;
    	root2.left = root1.right;
    	root1.right = root2;
    	((AVLNode)root2).height = Math.max(height(root2.left), height(root2.right)) + 1;
    	((AVLNode)root1).height = Math.max(height(root1.left), height(root2.right)) + 1;
    	return root1;
    }
    
    private BSTNode singleRotateRight(BSTNode root2) {
    	BSTNode root1 =  root2.right;
    	root2.right = root1.left;
    	root1.left = root2;
    	((AVLNode)root2).height = Math.max(height(root2.right), height(root2.left)) + 1;
    	((AVLNode)root1).height = Math.max(height(root1.right), height(root2.left)) + 1;
    	return root1;
    }
    
    private BSTNode doubleRotateLeft(BSTNode root3) {
    	root3.left = singleRotateRight(root3.left);
    	return singleRotateLeft(root3);
    }
    
    private BSTNode doubleRotateRight(BSTNode root3) {
    	root3.right = singleRotateLeft(root3.right);
    	return singleRotateRight(root3);
    }
    
    // Inner class to represent a node in the tree. Each node includes a data of type E 
    // and an integer count. 
	private class AVLNode extends BSTNode {
		public int height;
		
		// constructor
		public AVLNode(E data) {
			super(data);
			height = 0;
		}
	}
}