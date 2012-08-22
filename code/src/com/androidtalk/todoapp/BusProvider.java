package com.androidtalk.todoapp;

import com.squareup.otto.Bus;

public class BusProvider {
	private static final Bus BUS = new Bus();

	public synchronized static Bus getInstance(){
		return BUS;
	}

	private BusProvider(){ /* no instances..*/  }
}
