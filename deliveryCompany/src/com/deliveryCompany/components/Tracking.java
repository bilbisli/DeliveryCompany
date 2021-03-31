package com.deliveryCompany.components;

public class Tracking {

	private final int time;
	private final Node node;
	private final Status status;
	
	public Tracking(int time, Node node, Status status) {
		this.time = time;
		this.node = node;
		this.status = status;
	}

	public int getTime() {
		return time;
	}

	public Node getNode() {
		return node;
	}

	public Status getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return time + ": " + node + ", status=" + status;
	}
}
