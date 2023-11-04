import java.util.ArrayList;

public class Vertex {
	private String label;
	private ArrayList<Edge> edges;
	private Vertex parent;
	private boolean visited;
	private int cost;
	private int betweenCount;
	private float closeCount;

	public Vertex(String label) {
		this.label = label;
		edges = new ArrayList<Edge>();
		parent = null;
		visited= false;
		cost =0;
		betweenCount=0;
		closeCount=0;
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	public boolean containsEdge(Edge e){
		if(edges.contains(e)){
			return true;
		}
		return false;
	}

	public String getLabel() {
		return label;
	}

	public boolean hasParent(){
		return parent != null;
	}
	public Vertex getParent(){
		return parent;
	}
	public void visit()
	{
		visited = true;
	} // end visit

	public void unVisit()
	{
		visited = false;
	}

	public boolean isVisited()
	{
		return visited;
	} // end isVisited

	public void setLabel(String label) {
		this.label = label;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}


	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getBetweenCount() {
		return betweenCount;
	}

	public void increaseBetween(int count){
		this.betweenCount +=count;
	}

	public void increaseCloseness(int pathLength){
		this.closeCount+= pathLength;
	}
	public void  setCloseness(){
		closeCount = 1 / closeCount;
	}
	public float getCloseCount() {
		return closeCount;
	}

	public void setCloseCount(float closeCount) {
		this.closeCount = closeCount;
	}

	public void setBetweenCount(int betweenCount) {
		this.betweenCount = betweenCount;
	}
}
