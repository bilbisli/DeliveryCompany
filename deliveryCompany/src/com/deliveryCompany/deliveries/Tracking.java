package com.deliveryCompany.deliveries;

public class Tracking {
	private int time;
	private Node node;
	private Status status;
	
	public Tracking(int time, Node node, Status status) {
		this.time = time;
		this.node = node;
		this.status = status;
	}
}
