// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package graph;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import model.Direction;

/**
 * @author Julian Mackay
 *
 * @param <K> The edge key type.
 * @param <V> The value type
 */
/**
 * @author Julian Mackay
 *
 * @param <K>
 * @param <V>
 */
public interface Node<K extends Object, V extends Object> {

	/**
	 * Traverse the graph in the direction specified.
	 *
	 * @param direction
	 * @return the node in the specified direction
	 */
	public Node<K, V> go(K direction);

	/**
	 * Connect this node to a node in the specified direction.
	 * This method can assume the nodes are on a valid graph,
	 * and must guarantee that the result is still a valid graph,
	 * as detailed in the assignment handout.
	 *
	 * @param direction: the direction this connects to n
	 * @param n: the connected node
	 * @return A boolean indicating success of the connection;
	 *   in case the connection is not successful, the nodes should not be mutated.
	 *   Connection failure can happen, for example, if a node is already connected to
	 *   a node in the given direction.
	 */
	public Boolean connect(K direction, Node<K, V> n);

	/**
	 * Checks that the specified node is connected to this node in the direction.
	 * The graph is assumed to be valid, and this method can not modify the nodes.
	 *
	 * @param direction
	 * @param n
	 * @return Boolean indicating connectivity
	 */
	public Boolean isConnected(K direction, Node<K, V> n);

	/**
	 * Generates a list from the graph, from left to right, top to bottom
	 * This method can assumes that the graph is valid.
	 * @return
	 */
	public List<Node<K, V>> toList();

	/**
	 * Adds a node in the given direction. This is unidirectional, i.e. this does
	 * not add this node as a neighbor of n in the opposite direction.
	 * This method do *not* require the graph to be valid, and may
	 * make a valid graph invalid, or vice versa make an invalid graph valid.
	 *
	 * @param direction
	 * @param n
	 * @return Boolean indicating success of the addition. Addition fails if there
	 *   is already a connected node in the given direction.
	 *   If the addition fails, the nodes are not modified.
	 */
	public Boolean add(K direction, Node<K, V> n);

	/**
	 * Sets the node value
	 *
	 * @param v
	 * @return Boolean indicating success of the setter
	 */
	public Boolean setValue(V v);

	/**
	 * Generate a connected graph of specified depth. Using this node as the centre
	 * generate constructs a connected graph surrounding it of the specified depth,
	 * constructing new nodes where appropriate. The depth is an integer measuring
	 * the number of nodes from this node (the middle) to the outer edge. This is
	 * inclusive of this node. That is a single node has a depth of 1, a node
	 * entirely surrounded by other nodes has a depth of 2. Adding another layer to
	 * that gives us a layer of depth of 3, etc.
	 *
	 *
	 * @param depth
	 * @return this node
	 */
	public Node<K, V> generate(Integer depth);

	/**
	 * Checks if this node has a neighbor in the direction dir
	 *
	 * @param dir
	 * @return Boolean indicating neighborhood
	 */
	public Boolean hasNeighbor(K dir);

	/**
	 * Getter for value
	 *
	 * @return
	 */
	public V getValue();

	/**
	 * Surrounds the current node with nodes. This creates new nodes where
	 * appropriate and connects them together.
	 *
	 * @return this node
	 */
	public Node<K, V> fillNeighborhood();

	/**
	 * Returns a stream that starts from the north west most corner and streams row
	 * by row, west to east
	 *
	 * @return
	 */
	public Stream<Node<K, V>> stream();

	/**
	 * Returns a stream starting from the north west most node, spiraling in toward
	 * the centre.
	 *
	 * @return
	 */
	public Stream<Node<K, V>> clockwiseStream();

	/**
	 * Checks if the node belongs to a valid graph.
	 * This method is designed to be used in assertions, so it either throw an assertion
	 * error or returns true.
	 *
	 * @return true, or throw AssertionError
	 */
	public Boolean isValid();

}
