import java.util.*;


public class Graph {
	private HashMap<String, Vertex> vertices;
	private HashMap<String, Edge> edges;
	private HashMap<String, Stack<String>> allPaths;

	public Graph() {
		this.vertices = new HashMap<>();
		this.edges=new HashMap<>();
		this.allPaths = new HashMap<>();
	}

	public void addEdge(String source, String destination) {

		Vertex sourceVertex= getVertex(source);
		Vertex destinationVertex= getVertex(destination);
		Edge edge = new Edge(sourceVertex,destinationVertex);
		if((sourceVertex != null) && (destinationVertex != null)){
			if (!(edges.containsKey(edge.getHashTableCode()) ||
					(sourceVertex.containsEdge(edge) || destinationVertex.containsEdge(edge))
			)){
				sourceVertex.addEdge(edge);
				destinationVertex.addEdge(edge);
				edges.put(edge.getHashTableCode(), edge);
			}
		}

	}
	public boolean isSamePath(String start , String end){
		Vertex startVertex = vertices.get(start);
		Stack<String> path = null;
		if(allPaths.containsKey(end+"_" + start)){
			startVertex.increaseCloseness((allPaths.get(end+"_" + start).size() -1));
			path= allPaths.get(end+"_" + start);
		}
		if(path != null){
			while(!path.isEmpty()){
				Vertex node= vertices.get(path.pop());
				node.increaseBetween(1);
			}
			return true;
		}
		return false;
	}
	public void allPossiblePaths(){
		for(Vertex vertex : vertices.values()){
			for(Vertex v : vertices()){
				if(!vertex.equals(v)){
					getShortestPath(vertex.getLabel(),v.getLabel());
				}
			}
			vertex.setCloseness();
		}
	}
	public void getShortestPath (String start , String end ){
		Stack<String> shortestPath= new Stack<>();
		resetVertices();
		boolean done=false;
		Queue<Vertex> vertexQueue = new LinkedList<>();
		Vertex startVertex = vertices.get(start);
		Vertex endVertex= vertices.get(end);
		startVertex.visit();
		vertexQueue.add(startVertex);
		while(!isSamePath(start,end) && !done && !vertexQueue.isEmpty()){
			Vertex frontVertex = vertexQueue.poll();
			Iterator<Edge> neighbors = frontVertex.getEdges().iterator();
			while(!done && neighbors.hasNext()){
				Edge nextNeighbor = neighbors.next();
				if(!nextNeighbor.getDestination().isVisited()){
					nextNeighbor.getDestination().visit();
					nextNeighbor.getDestination().setCost(1 + frontVertex.getCost());
					nextNeighbor.getDestination().setParent(frontVertex);
					vertexQueue.add(nextNeighbor.getDestination());
				}
				else if(!nextNeighbor.getSource().isVisited()){
					nextNeighbor.getSource().visit();
					nextNeighbor.getSource().setCost(1 + frontVertex.getCost());
					nextNeighbor.getSource().setParent(frontVertex);
					vertexQueue.add(nextNeighbor.getSource());
				}
				if(nextNeighbor.getDestination().equals(endVertex) || nextNeighbor.getSource().equals(endVertex)){
					done=true;
				}
			}
		}
		int pathLength = endVertex.getCost();
		if(pathLength != 0){
			startVertex.increaseCloseness(pathLength);
			shortestPath.push(endVertex.getLabel());
			Vertex vertex= endVertex;
			endVertex.increaseBetween(1);
			while(vertex.hasParent()){
				vertex = vertex.getParent();
				shortestPath.push(vertex.getLabel());
				vertex.increaseBetween(1);
			}
			allPaths.put(start +"_" + end, shortestPath);
		}
	}
	private void resetVertices(){
		Iterator<Vertex> vertexIterator = vertices().iterator();
		while (vertexIterator.hasNext()){
			Vertex nextVertex = vertexIterator.next();
			nextVertex.unVisit();
			nextVertex.setCost(0);
			nextVertex.setParent(null);
		}
	}
	private Vertex getVertex(String node){
		if(vertices.containsKey(node)){
			return vertices.get(node);
		}
		else{
			Vertex vertex = new Vertex(node);
			vertices.put(node,vertex);
			return vertex;
		}
	}
	public Iterable<Vertex> vertices() {
		return vertices.values();
	}
	public Vertex  getMaxNodeForBetween(){
		int max=0;
		Vertex node = null;
		for(Vertex vertex:vertices()){
			if(max < vertex.getBetweenCount()){
				max=vertex.getBetweenCount();
				node = vertex;
			}

		}
		return node;
	}
	public Vertex getMaxNodeOfClose(){
		float max=0;
		Vertex node =null;
		for(Vertex vertex:vertices()){
			if(max < vertex.getCloseCount() &&  vertex.getCloseCount() != 1){
				max=vertex.getCloseCount();
				node = vertex;
			}
		}
		return node;
	}

}
