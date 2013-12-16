package com.simplecontextaware.events;

import java.util.ArrayList;
import java.util.List;

import com.squareup.otto.Bus;


public class BusManager {
	
	private static final Bus bus = new Bus();
	
	private static ArrayList<Object> producers = new ArrayList<Object>();
	
	private static ArrayList<Object> receivers = new ArrayList<Object>();
	
	public BusManager() {
	}
	
	public static void registerProducer(Object obj) {
		if (!verifyIfExists(producers,obj)) {
			bus.register(obj);
			producers.add(obj);
		}
	}
	
	public static void registerReceiver(Object obj) {
		if (!verifyIfExists(receivers,obj)) {
			bus.register(obj);
			receivers.add(obj);
		}
	}
	
	public static Bus getBus() {
		return bus;
	}
	
	private static boolean verifyIfExists(List<Object> parent, Object object) {
		
		for (Object obj : parent) {
			if (obj.getClass().getCanonicalName()
					.compareTo(object.getClass().getCanonicalName()) == 0) {
				return true;
			}
		}
		
		return false;
	}
}
