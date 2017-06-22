package iamdilipkumar.com.spacedig.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.rover.MarsRoverPhoto;

/**
 * Created on 21/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class RoverListAdapter extends RecyclerView.Adapter<RoverListAdapter.GridListViewHolder> {

    private List<MarsRoverPhoto> mList;
    private RoverListAdapter.GridListClick mainListClick;
    private Context mContext;

    public RoverListAdapter(Context context, RoverListAdapter.GridListClick listClick,
                            List<MarsRoverPhoto> itemList) {
        this.mContext = context;
        this.mList = itemList;
        this.mainListClick = listClick;
    }

    @Override
    public RoverListAdapter.GridListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid, null);
        return new RoverListAdapter.GridListViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RoverListAdapter.GridListViewHolder holder, int position) {
        Picasso.with(mContext).load(mList.get(position)
                .getImgSrc()).into(holder.mDescriptiveImage);

        if (mList.get(position).getCamera() != null) {
            holder.mName.setText(mList.get(position).getCamera().getName());
        }
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    public interface GridListClick {
        void onMainItemClick(int position);
    }

    class GridListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_descriptive)
        ImageView mDescriptiveImage;

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
