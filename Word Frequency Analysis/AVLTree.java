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
	private int height(AVLNode t) {
		return t == null ? -1 : t.height;
	}
	
	public int getHeight(E data) {
		return helpGetHeight((AVLNode)overallRoot, data);
	}
	
	private int helpGetHeight(AVLNode root, E data) {
		if(root == null)
			return -1;
		if(comparator.compare(data, root.data) == 0)
			return height(root);
		else if (comparator.compare(data, root.data) > 0)
			return helpGetHeight((AVLNode) root.right, data);
		else
			return helpGetHeight((AVLNode) root.left, data);
	}
	
	// TODO: To-be implemented
    public void incCount(E data) {
    	overallRoot = insert(data, (AVLNode)overallRoot);
    }
	
    public AVLNode insert(E data, AVLNode root) {
    	AVLNode currentNode = (AVLNode) root;
    	if(root == null) {
    		currentNode = new AVLNode(data);
    		return currentNode;
    	}
    	int cmp = comparator.compare(data, root.data);
    	if(cmp == 0) {
    		root.count++;
    	} else if(cmp < 0) {
    		root.left = insert(data, (AVLNode) root.left);
    	} else {
    		root.right = insert(data, (AVLNode) root.right);
    	}
    	return balance(root);
    }
    
    @SuppressWarnings("unchecked")
	private AVLNode balance(AVLNode root) {
    	if(root == null)
    		return root;
    	if(height((AVLNode) root.left) - height((AVLNode)root.right) > 1) {
    		if(height((AVLNode)root.left.left) >= height((AVLNode)root.left.right))
    			root = singleRotateLeft(root);
    		else
    			root = doubleRotateLeft(root);
    	}
    	else if((height((AVLNode)root.right) - height((AVLNode)root.left)) > 1) {
			if(height((AVLNode)root.right.right) >= height((AVLNode)root.right.left))
    			root = singleRotateRight(root);
    		else
    			root = doubleRotateRight(root);
		}
    	root.height = Math.max(height((AVLNode)root.left), height((AVLNode)root.right)) + 1;
    	return root;
    }
    
    private AVLNode singleRotateLeft(AVLNode root2) {
    	AVLNode root1 = (AVLNode) root2.left;
    	root2.left = root1.right;
    	root1.right = root2;
    	root2.height = Math.max(height((AVLNode)root2.left), height((AVLNode)root2.right)) + 1;
    	root1.height = Math.max(height((AVLNode)root1.left), height((AVLNode)root2.right)) + 1;
    	return root1;
    }
    
    private AVLNode singleRotateRight(AVLNode root2) {
    	AVLNode root1 = (AVLNode) root2.right;
    	root2.right = root1.left;
    	root1.left = root2;
    	root2.height = Math.max(height((AVLNode)root2.right), height((AVLNode)root2.left)) + 1;
    	root1.height = Math.max(height((AVLNode)root1.right), height((AVLNode)root2.left)) + 1;
    	return root1;
    }
    
    private AVLNode doubleRotateLeft(AVLNode root3) {
    	root3.left = singleRotateRight((AVLNode) root3.left);
    	return singleRotateLeft(root3);
    }
    
    private AVLNode doubleRotateRight(AVLNode root3) {
    	root3.right = singleRotateLeft((AVLNode) root3.right);
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