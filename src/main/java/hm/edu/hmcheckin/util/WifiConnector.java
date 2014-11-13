package hm.edu.hmcheckin.util;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Handles Wifi network.
 * 
 * @author Acer TODO - Name
 * @version 1.0
 * 
 */
public class WifiConnector {
	private final Context context;
	private ArrayList<Wifi> wifiList = new ArrayList<Wifi>();
	
	/**
	 * Ctor. TODO - desciptions
	 * @author TODO
	 * @param context
	 *            the activity context
	 */
	public WifiConnector(Context context) {
		this.context = context;
	}

	/**
	 * Looking for accessable wifi networks.
	 * @author TODO
	 * @param TODO params
	 */
	public void scanForNetworks() {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		List<ScanResult> networks = manager.getScanResults();
		ArrayList<String> ssidArr = new ArrayList<String>();
//		ScanResult bestSignal = null;
		for (ScanResult netw : networks) {
			
			/* check local fixed access points only */
//			if (bestSignal == null || WifiManager.compareSignalLevel(bestSignal.level, netw.level) < 0)
//				bestSignal = netw;

			try {
				wifiList.add(new Wifi(netw.SSID, netw.BSSID, -1, manager.calculateSignalLevel(netw.level, 100), null, null));
			} catch (Exception e) {
				e.printStackTrace();
			}
			ssidArr.add(netw.BSSID);
		}
	}

	/**
	 * Get list of networks currently available.
	 * @author TODO
	 * @return list of networks
	 */
	public ArrayList<Wifi> getnetworkList() {
		wifiList.clear();
		scanForNetworks();
		return wifiList;
	}

}
