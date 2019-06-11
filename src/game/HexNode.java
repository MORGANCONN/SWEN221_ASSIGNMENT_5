// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package game;

import java.util.*;
import java.util.stream.Stream;

import graph.Node;
import model.Direction;

/**
 *
 * A graph where each node has up to 6 edges.
 *
 * @author Julian Mackay
 *
 * @param <V>
 *            the value stored in each node
 */
public class HexNode<V> implements Node<Direction, V> {

	private final Map<Direction, Node<Direction, V>> neighbors=new LinkedHashMap<>();
	private V v;

	public HexNode(V v) {
		this.v = v;
	}

	public HexNode() {}

	@Override
	public V getValue() {
		return v;
	}

	@Override
	public Boolean setValue(V v) {
		if (this.v != null) {return false;}
		this.v = v;
		return true;
	}

	@Override
	public Node<Direction, V> go(Direction direction) {
		if (!this.neighbors.containsKey(direction)) {return null;}
		return this.neighbors.get(direction);
	}

	@Override
	public Boolean connect(Direction direction, Node<Direction, V> n) {
	    if(n==null||n.go(direction)!=null||this==n){
	        return false;
        } else{
            add(direction,n);
            neighbors.get(direction).add(direction.inverse(),this);
            checkValid();
            return true;
        }
	}

	private void checkValid(){
		this.collectAll().stream().forEach(n->isValidOne(n));
    }

	@Override
	public Boolean isConnected(Direction direction, Node<Direction, V> n) {
		if(go(direction)==n&&go(direction).go(direction.inverse())==this){
		    return true;
        } else{
		    return false;
        }
	}

	@Override
	public Boolean add(Direction direction, Node<Direction, V> n) {
		neighbors.put(direction,n);
		if(neighbors.get(direction).equals(n)){
			return true;
		} else{
			return false;
		}
	}

	@Override
	public Boolean hasNeighbor(Direction dir) {
		if(neighbors.get(dir)!=null){
			return true;
		} else{
			return false;
		}
	}

	@Override
	public Node<Direction, V> fillNeighborhood() {
		if(neighbors.keySet().size()==6){
			return this;
		}
		for(Direction D : Direction.values()){
		    if(!hasNeighbor(D)){
		        HexNode toAdd = new HexNode();
		        connect(D,toAdd);
			}
        }
		return this;
	}

	@Override
	public HexNode<V> generate(Integer depth) {
			if(depth==1){return this;}
			else if(depth==2){
				fillNeighborhood();
			} else if(depth>2){
            	for(Direction D : Direction.values()) {
            		expandGraph(D,this, depth);
				}
            }
            System.out.println(this.collectAll().size());
        return this;
	}

	private void expandGraph(Direction d, HexNode<V> vHexNode, Integer depth) {
		Node currentNode = vHexNode;
		for(int i = 1;i<depth;i++){
			currentNode.fillNeighborhood();
			currentNode = currentNode.go(d);
		}
	}

	@Override
	public List<Node<Direction, V>> toList() {
		Node<Direction,V> currentNode = getTopLeft(this);
		List<Node<model.Direction,V>> graphList = new ArrayList<>();
		Node<Direction,V> currentMaxLeftNode = currentNode;

		while(currentMaxLeftNode.go(Direction.SOUTHWEST)!=null){
			graphList.add(currentMaxLeftNode);
			while (currentNode.go(Direction.EAST)!=null){
				currentNode = currentNode.go(Direction.EAST);
				graphList.add(currentNode);
			}
			currentMaxLeftNode = currentMaxLeftNode.go(Direction.SOUTHWEST);
			currentNode = currentMaxLeftNode;
		}
		while(currentMaxLeftNode.go(Direction.SOUTHEAST)!=null){
			graphList.add(currentMaxLeftNode);
			while (currentNode.go(Direction.EAST)!=null){
				currentNode = currentNode.go(Direction.EAST);
				graphList.add(currentNode);
			}
			currentMaxLeftNode = currentMaxLeftNode.go(Direction.SOUTHEAST);
			currentNode = currentMaxLeftNode;
		}
		graphList.add(currentNode);
		while(currentNode.go(Direction.EAST)!=null){
			currentNode = currentNode.go(Direction.EAST);
			graphList.add(currentNode);
		}
		return graphList;
	}

	private Node<Direction,V> getTopLeft(HexNode<V> vHexNode) {
		Node<Direction,V> currentNode = vHexNode;
		while(currentNode.go(Direction.NORTHWEST)!=null){
			currentNode = currentNode.go(Direction.NORTHWEST);
		}
		while(currentNode.go(Direction.NORTHEAST)!=null){
			currentNode = currentNode.go(Direction.NORTHEAST);
		}
		while(currentNode.go(Direction.WEST)!=null){
			currentNode = currentNode.go(Direction.WEST);
		}
		return currentNode;
	}

	public List<Node<Direction, V>> getClockwiseList(){
		Node<Direction,V> currentNode = getTopLeft(this);
		List<Node<model.Direction,V>> graphList = new ArrayList<>();
		graphList.add(currentNode);
		while (graphList.size()!=this.collectAll().size()) {
			while(currentNode.go(Direction.EAST)!=null&&!graphList.contains(currentNode.go(Direction.EAST))){
				currentNode = currentNode.go(Direction.EAST);
				graphList.add(currentNode);
			}
			while(currentNode.go(Direction.SOUTHEAST)!=null&&!graphList.contains(currentNode.go(Direction.SOUTHEAST))){
				currentNode =currentNode.go(Direction.SOUTHEAST);
				graphList.add(currentNode);
			}
			while(currentNode.go(Direction.SOUTHWEST)!=null&&!graphList.contains(currentNode.go(Direction.SOUTHWEST))){
				currentNode =currentNode.go(Direction.SOUTHWEST);
				graphList.add(currentNode);
			}
			while(currentNode.go(Direction.WEST)!=null&&!graphList.contains(currentNode.go(Direction.WEST))){
				currentNode =currentNode.go(Direction.WEST);
				graphList.add(currentNode);
			}
			while(currentNode.go(Direction.NORTHWEST)!=null&&!graphList.contains(currentNode.go(Direction.NORTHWEST))){
				currentNode =currentNode.go(Direction.NORTHWEST);
				graphList.add(currentNode);
			}
			while(currentNode.go(Direction.NORTHEAST)!=null&&!graphList.contains(currentNode.go(Direction.NORTHEAST))){
				currentNode =currentNode.go(Direction.NORTHEAST);
				graphList.add(currentNode);
			}
		}
		return graphList;
	}

	@Override
	public Stream<Node<Direction, V>> stream() {
		return toList().stream();
	}

	@Override
	public Stream<Node<Direction, V>> clockwiseStream() {
		return getClockwiseList().stream();
	}

	@Override
	public String toString() {
		return v==null?"*"+this.hashCode():v.toString();
	}
	@Override
	public Boolean isValid() {
		return true;
	}
	/**
	 * Very general algorithm to collect all the nodes of a graph
	 * @return
	 */
	private Set<Node<Direction,V>> collectAll(){
		Set<Node<Direction,V>> res=new LinkedHashSet<>();
		collectAll(this,res);
		return res;
	}
	private static<V> void collectAll(Node<Direction,V> n,Set<Node<Direction,V>> acc){
		if(n==null) {return;}
		if(acc.contains(n)) {return;}
		acc.add(n);
		Stream.of(Direction.values()).forEach(d->collectAll(n.go(d),acc));
	}

	public static<V> void isValidOne(Node<Direction,V> n) {
		for(Direction d : Direction.values()){
		    if(!n.hasNeighbor(d)){continue;}
			Node<Direction, V> n1 = n.go(d);
			Node<Direction, V> n2 = n.go(d.clockwise());
			if(n1!=null){
				if(n2==null) {continue;}
				if(n1.go(d.inverse().antiClockwise())!=n2){
					n1.add(d.inverse().antiClockwise(),n2);
					n2.add(d.inverse().antiClockwise().inverse(),n1);
				}
			}
		}
		for(Direction d : Direction.values()){
            if(!n.hasNeighbor(d)){continue;}
            Node<Direction, V> n1 = n.go(d);
			Node<Direction, V> n2 = n.go(d.antiClockwise());
			if(n1!=null){
				if(n2==null) {continue;}
				if(n1.go(d.inverse().clockwise())==n2){
					n1.add(d.inverse().clockwise(),n2);
					n2.add(d.inverse().clockwise().inverse(),n1);
				}
			}
		}
	}

}