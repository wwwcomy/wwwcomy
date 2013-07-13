package com.iteye.wwwcomy.rmi.remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class TestRemote extends UnicastRemoteObject implements IRemoteTimer {

	private static final long serialVersionUID = 1L;

	protected TestRemote() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
//		System.setSecurityManager(new RMISecurityManager());
		try {
			LocateRegistry.createRegistry(1099);
			IRemoteTimer pt = new TestRemote();
			Naming.rebind("//1.1.7.81/time", pt);
			System.out.println("Ready to do time");
		} catch (Exception e) {
			System.out.println("Failed");
			e.printStackTrace();
		}
	}

	@Override
	public long getTime() throws RemoteException {
		return System.currentTimeMillis();
	}
}
