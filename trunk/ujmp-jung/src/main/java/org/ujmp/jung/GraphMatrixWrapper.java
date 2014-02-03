/*
 * Copyright (C) 2008-2014 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.jung;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ujmp.core.graphmatrix.GraphMatrix;

import edu.uci.ics.jung.graph.AbstractTypedGraph;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class GraphMatrixWrapper<V, E> extends AbstractTypedGraph<V, E> implements DirectedGraph<V, E> {
	private static final long serialVersionUID = -3871581250021217530L;

	private final GraphMatrix<V, E> graphMatrix;

	public GraphMatrixWrapper(GraphMatrix<V, E> graphMatrix) {
		super(EdgeType.DIRECTED);
		this.graphMatrix = graphMatrix;
	}

	public Collection<E> getInEdges(V vertex) {
		return graphMatrix.getEdgesToParents(vertex);
	}

	public Collection<E> getOutEdges(V vertex) {
		return graphMatrix.getEdgesToChildren(vertex);
	}

	public Collection<V> getPredecessors(V vertex) {
		return graphMatrix.getParents(vertex);
	}

	public Collection<V> getSuccessors(V vertex) {
		return graphMatrix.getChildren(vertex);
	}

	public V getSource(E directed_edge) {
		for (V node : graphMatrix.getNodeList()) {
			for (E edge : graphMatrix.getEdgesToChildren(node)) {
				if (edge == directed_edge) {
					return node;
				}
			}
		}
		return null;
	}

	public V getDest(E directed_edge) {
		for (V node : graphMatrix.getNodeList()) {
			for (E edge : graphMatrix.getEdgesToParents(node)) {
				if (edge == directed_edge) {
					return node;
				}
			}
		}
		return null;
	}

	public boolean isSource(V vertex, E edge) {
		V source = getSource(edge);
		return vertex == source;
	}

	public boolean isDest(V vertex, E edge) {
		V dest = getDest(edge);
		return vertex == dest;
	}

	public Pair<V> getEndpoints(E edge) {
		for (final long[] c : graphMatrix.availableCoordinates()) {
			final E e = graphMatrix.getEdge(c[0], c[1]);
			if (e.equals(edge)) {
				return new Pair<V>(graphMatrix.getNode(c[0]), graphMatrix.getNode(c[1]));
			}
		}
		return null;
	}

	public Collection<E> getEdges() {
		return graphMatrix.getEdgeList();
	}

	public Collection<V> getVertices() {
		return graphMatrix.getNodeList();
	}

	public boolean containsVertex(V vertex) {
		return graphMatrix.getNodeList().contains(vertex);
	}

	public boolean containsEdge(E edge) {
		return graphMatrix.getEdgeList().contains(edge);
	}

	public int getEdgeCount() {
		return graphMatrix.getEdgeCount();
	}

	public int getVertexCount() {
		return graphMatrix.getNodeCount();
	}

	public Collection<V> getNeighbors(V vertex) {
		List<V> neighbors = new ArrayList<V>();
		neighbors.addAll(graphMatrix.getParents(vertex));
		neighbors.addAll(graphMatrix.getChildren(vertex));
		return neighbors;
	}

	public Collection<E> getIncidentEdges(V vertex) {
		List<E> edges = new ArrayList<E>();
		edges.addAll(graphMatrix.getEdgesToParents(vertex));
		edges.addAll(graphMatrix.getEdgesToChildren(vertex));
		return edges;
	}

	public boolean addVertex(V vertex) {
		graphMatrix.addNode(vertex);
		return true;
	}

	public boolean removeVertex(V vertex) {
		graphMatrix.removeNode(vertex);
		return true;
	}

	public boolean removeEdge(E edge) {
		graphMatrix.removeEdge(edge);
		return true;
	}

	@Override
	public boolean addEdge(E edge, Pair<? extends V> endpoints, EdgeType edgeType) {
		graphMatrix.setEdge(edge, endpoints.getFirst(), endpoints.getSecond());
		if (edgeType == EdgeType.UNDIRECTED) {
			graphMatrix.setEdge(edge, endpoints.getSecond(), endpoints.getFirst());
		}
		return true;
	}

	public GraphMatrix<V, E> getGraphMatrix() {
		return graphMatrix;
	}

}
