package forpdateam.ru.forpda.ui.fragments.history;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import forpdateam.ru.forpda.R;
import forpdateam.ru.forpda.entity.app.history.HistoryItem;
import forpdateam.ru.forpda.ui.views.adapters.BaseAdapter;
import forpdateam.ru.forpda.ui.views.adapters.BaseViewHolder;

/**
 * Created by radiationx on 06.09.17.
 */

public class HistoryAdapter extends BaseAdapter<HistoryItem, HistoryAdapter.HistoryHolder> {
    private BaseAdapter.OnItemClickListener<HistoryItem> itemClickListener;

    public void setItemClickListener(BaseAdapter.OnItemClickListener<HistoryItem> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryHolder(inflateLayout(parent, HistoryHolder.LAYOUT));
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.HistoryHolder holder, int position) {
        holder.bind(getItem(position), position);
    }


    class HistoryHolder extends BaseViewHolder<HistoryItem> {
        public final static int LAYOUT = R.layout.item_history;
        private TextView title;
        private TextView date;

        public HistoryHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            date = (TextView) itemView.findViewById(R.id.item_date);
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(getItem(getLayoutPosition()));
                }
            });
            itemView.setOnLongClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemLongClick(getItem(getLayoutPosition()));
                    return true;
                }
                return false;
            });
        }

        @Override
        public void bind(HistoryItem item, int position) {
            title.setText(item.getTitle());
            date.setText(item.getDate());
        }
    }

}
