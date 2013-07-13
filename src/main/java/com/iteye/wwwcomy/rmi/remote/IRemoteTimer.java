package com.iteye.wwwcomy.rmi.remote;

import java.rmi.*;

public interface IRemoteTimer extends Remote {
	long getTime() throws RemoteException;
}
