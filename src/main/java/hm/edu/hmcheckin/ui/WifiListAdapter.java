/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.BaseListItem;
import hm.edu.hmcheckin.util.ListItemType;
import hm.edu.hmcheckin.util.Wifi;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Network list adapter for checkin activity.
 * 
 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
 * @version 1.0
 * 
 */
public class WifiListAdapter extends BaseAdapter {
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private final Context context;
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private final ArrayList<BaseListItem> wifiList;
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private final LayoutInflater inflater;

	/**
	 * Ctor.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param Context
	 */
	WifiListAdapter(Context cont) {
		context = cont;
		inflater = (LayoutInflater) cont
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		wifiList = new ArrayList<BaseListItem>();
	}

	/**
	 * add a network.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param network
	 */
	public void addItem(Wifi network) {
		wifiList.add(network);
		notifyDataSetChanged();
	}

	/**
	 * add a list of networks.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param list
	 *            of networks
	 */
	public void addItems(ArrayList<Wifi> netw) {
		// add wifis once only
		for (Wifi w : netw)
			if (!wifiList.contains(w))
				wifiList.add(w);

		notifyDataSetChanged();
	}

	/**
	 * delete all items and reset the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param list
	 *            of networks
	 */
	public void setWifiList(ArrayList<Wifi> list) {
		wifiList.clear();
		addItems(list);
		notifyDataSetChanged();
	}

	/**
	 * remove all items from the list
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @return <code>void<code>
	 */
	public void clearList() {
		wifiList.clear();
		notifyDataSetChanged();
	}

	/**
	 * get number of networks in the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @return list size
	 */
	@Override
	public int getCount() {
		return wifiList.size();
	}

	/**
	 * get a wifi from the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param index
	 * @return wifi
	 */
	@Override
	public Object getItem(int index) {
		return wifiList.get(index);
	}

	/**
	 * get the item id of a wifi on the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a> TODO (not
	 *         implemented)
	 * @return item id
	 */
	@Override
	public long getItemId(int arg0) {
		return -1;
	}

	/**
	 * returns a new list view element.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null)
			v = inflater.inflate(R.layout.row_wifilist, null);

		Wifi network = (Wifi) wifiList.get(position);

		/* set item or list seperator */
		if (network.getType() == ListItemType.ITEM) {
			v = inflater.inflate(R.layout.row_wifilist, null);

			/* set item labels */
			if (network != null) {
				TextView headline = (TextView) v
						.findViewById(R.id.wifi_item_headline);
				TextView subtitle = (TextView) v
						.findViewById(R.id.wifi_item_subtitle);
				TextView addrView = (TextView) v
						.findViewById(R.id.wifi_item_loc_addr);

				String ssid = network.getSsid();
				String addr = network.getLoc_addr();
				String room = network.getLoc_room();
				int locationsId = network.getLocationId();
				int wifiStrength = network.getSignalLevel();

				/* show room lable if availabel */
				if (locationsId > 0) {
					if (ssid.startsWith("ap"))
						headline.setText(addr + "\n" + room);
				} else {
					headline.setText(ssid);
					subtitle.setText(room);
				}

				addrView.setText(wifiStrength + "");
			}
		} else {
			v = inflater.inflate(R.layout.row_list_seperator, null);
		}
		return v;
	}

}
