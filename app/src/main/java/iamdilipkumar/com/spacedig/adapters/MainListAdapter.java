package iamdilipkumar.com.spacedig.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;

/**
 * Created on 20/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder> {

    private List<SimpleItemModel> mList;
    private MainListClick mainListClick;

    public MainListAdapter(MainListClick listClick, List<SimpleItemModel> itemList) {
        this.mList = itemList;
        this.mainListClick = listClick;
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered_main, null);
        return new MainListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MainListViewHolder holder, int position) {
        holder.mDescriptiveImage.setImageResource(mList.get(position).getImageRes());
        holder.mName.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    public interface MainListClick {
        void onMainItemClick(int position);

        void onMainItemInformationClick(int position);
    }

    class MainListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_descriptive)
        ImageView mDescriptiveImage;

        @BindView(R.id.tv_list_name)
        TextView mName;

        @OnClick(R.id.iv_item_information)
        void informationDialog() {
            mainListClick.onMainItemInformationClick(getAdapterPosition());
        }


        MainListViewHolder(View itemView) {
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
