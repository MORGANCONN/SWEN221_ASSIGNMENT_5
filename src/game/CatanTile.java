// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package game;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import graph.Node;
import model.Direction;
import model.Location;
import model.Resource;
import model.ResourceCounter;
import model.Road;
import model.Settlement;
import model.Tile;

/**
 * The main Tile class
 *
 * @author Julian Mackay
 *
 */
public class CatanTile implements Tile{

	private final Integer id;
	private final Map<Location, Settlement> settlements;
	private final Map<Direction, Road> roads;
	private final Node<Direction, Tile> currentNode;
	private Resource resource;
	private ResourceCounter resourceCounter;

	public CatanTile(Integer id, Node<Direction, Tile> currentNode) {
		this.id = id;
		this.settlements = new LinkedHashMap<>();
		this.roads = new LinkedHashMap<>();
		this.currentNode = currentNode;
	}

	@Override
	public Integer getID() {
		return this.id;
	}

	@Override
	public Boolean setResource(Resource r) {
		this.resource = r;
		return true;
	}

	@Override
	public Resource getResource() {
		return this.resource;
	}

	@Override
	public Integer getResourceNumber() {
		return resourceCounter.getNumber();
	}

	@Override
	public Boolean setResourceCounter(ResourceCounter resourceCounter) {
		this.resourceCounter = resourceCounter;
		return true;
	}

	@Override
	public Boolean hasSettlement(Location loc) {
		return settlements.get(loc)!=null;
	}

	@Override
	public Boolean addSettlement(Settlement s, Location loc) {
		settlements.put(loc,s);
		return settlements.get(loc) == s;
	}

	@Override
	public Boolean hasRoad(Direction dir) {
		return roads.get(dir)!=null;
	}

	@Override
	public Boolean addRoad(Road r, Direction dir) {
		if(!roads.keySet().contains(dir)){
			roads.put(dir,r);
		}
		currentNode.go(dir).getValue().addRoad(r,dir.inverse());
		return true;
	}

	@Override
	public List<Settlement> getSettlements(){
		//TODO: Replace this method
		throw new Error("implement CatanTile.getSettlements()");
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

}