package forpdateam.ru.forpda.fragments.favorites;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import forpdateam.ru.forpda.App;
import forpdateam.ru.forpda.R;
import forpdateam.ru.forpda.api.favorites.models.FavItem;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by radiationx on 22.09.16.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private RealmList<FavItem> list;

    public FavoritesAdapter(){
        list = new RealmList<>();
    }

    private FavoritesAdapter.OnItemClickListener itemClickListener;
    private FavoritesAdapter.OnLongItemClickListener longItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position, FavoritesAdapter adapter);
    }

    public void setOnItemClickListener(final FavoritesAdapter.OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(View view, int position, FavoritesAdapter adapter);
    }

    public void setOnLongItemClickListener(final FavoritesAdapter.OnLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView title, lastNick, date;
        public ImageView pinIcon, lockIcon, pollIcon;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.fav_item_title);
            lastNick = (TextView) v.findViewById(R.id.fav_item_last_nick);
            date = (TextView) v.findViewById(R.id.fav_item_date);
            pinIcon = (ImageView) v.findViewById(R.id.fav_item_pin_icon);
            lockIcon = (ImageView) v.findViewById(R.id.fav_item_lock_icon);
            pollIcon = (ImageView) v.findViewById(R.id.fav_item_poll_icon);

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, getLayoutPosition(), FavoritesAdapter.this);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (longItemClickListener != null) {
                longItemClickListener.onLongItemClick(view, getLayoutPosition(), FavoritesAdapter.this);
                return true;
            }
            return false;
        }
    }

    /*public FavoritesAdapter(List<FavItem> list) {
        this.list = list;
    }
    public FavoritesAdapter() {
        this.list = new RealmList<>();
    }*/

    public void addAll(RealmResults<FavItem> results) {
        list.addAll(results);
        notifyDataSetChanged();
    }
    public void clear() {
        list.clear();
    }

    private void add(FavItem item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fav_item, parent, false);
        return new FavoritesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
        FavItem item = list.get(position);
        Log.d("kek", "bind view holder " + position + " : " + item.getFavId());
        holder.title.setText(item.getTopicTitle());
        holder.title.setTypeface(null, item.isNewMessages() ? Typeface.BOLD : Typeface.NORMAL);
        holder.pinIcon.setVisibility(item.isPin() ? View.VISIBLE : View.GONE);
        holder.lockIcon.setVisibility(item.getInfo().contains("X") ? View.VISIBLE : View.GONE);
        holder.pollIcon.setVisibility(item.getInfo().contains("^") ? View.VISIBLE : View.GONE);
        if (item.getInfo().contains("+^"))
            holder.pollIcon.setColorFilter(ContextCompat.getColor(App.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        else
            holder.pollIcon.clearColorFilter();

        holder.lastNick.setText(item.getLastUserNick());
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public FavItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
