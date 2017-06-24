package iamdilipkumar.com.spacedig.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;

/**
 * Created on 21/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class GeneralListAdapter extends RecyclerView.Adapter<GeneralListAdapter.GridListViewHolder> {

    private List<SimpleItemModel> mList;
    private GeneralListAdapter.GridListClick mainListClick;

    public GeneralListAdapter(GeneralListAdapter.GridListClick listClick,
                            List<SimpleItemModel> itemList) {
        this.mList = itemList;
        this.mainListClick = listClick;
    }

    @Override
    public GeneralListAdapter.GridListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid, parent,false);
        return new GeneralListAdapter.GridListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(GeneralListAdapter.GridListViewHolder holder, int position) {
        holder.mName.setText(mList.get(position).getName());
        holder.mInfo.setText(mList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    public interface GridListClick {
        void onMainItemClick(int position);
    }

    class GridListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_list_info)
        TextView mInfo;

        @BindView(R.id.tv_list_name)
        TextView mName;


        GridListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            mainListClick.onMainItemClick(getAdapterPosition());
        }
    }
}
