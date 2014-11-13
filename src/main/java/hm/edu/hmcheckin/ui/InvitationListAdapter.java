/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.Invitation;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * TODO Javadoc
 */
public class InvitationListAdapter extends BaseAdapter {

	/**
	 * TODO Javadoc
	 */
	private final ArrayList<Invitation> invitations = new ArrayList<Invitation>();
	/**
	 * TODO Javadoc
	 */
	private final LayoutInflater inflater;
	/**
	 * TODO Javadoc
	 */
	private final InvitationFragment fragment;

	/**
	 * TODO Javadoc
	 */
	public InvitationListAdapter(Context context, InvitationFragment fragment) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.fragment = fragment;
	}

	/**
	 * TODO Javadoc
	 */
	public void addItem(Invitation invitation) {
		invitations.add(invitation);
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	public void addItems(ArrayList<Invitation> list) {
		for (Invitation i : list) {
			invitations.add(i);
		}
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	public void removeItem(int position) {
		invitations.remove(position);
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	public void clear() {
		invitations.clear();
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public int getCount() {
		return invitations.size();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public Invitation getItem(int index) {
		return invitations.get(index);
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_invitation, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.textViewName);
			holder.waiting = (TextView) convertView
					.findViewById(R.id.textViewWait);
			holder.accept = (Button) convertView
					.findViewById(R.id.buttonAccept);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Invitation inv = invitations.get(position);
		holder.name.setText(inv.getFirstName() + " " + inv.getLastName());
		if (inv.isNeedConfirm()) {
			holder.waiting.setVisibility(View.GONE);
			holder.accept.setVisibility(View.VISIBLE);
			holder.accept.setOnClickListener(new OnClickListener() {

				/**
				 * TODO Javadoc
				 */
				@Override
				public void onClick(View v) {
					fragment.confirmInvitation(String.valueOf(inv
							.getInvitationId()));
					removeItem(position);
				}
			});
		} else {
			holder.accept.setVisibility(View.GONE);
			holder.waiting.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	/**
	 * TODO Javadoc
	 */
	public static class ViewHolder {
		public TextView name;
		public TextView waiting;
		public Button accept;
	}
}
