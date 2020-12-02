package com.pooyeshgaran.tarootfaal.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pooyeshgaran.tarootfaal.Entities.Tarot;
import com.pooyeshgaran.tarootfaal.R;
import com.pooyeshgaran.tarootfaal.Utils;
import com.wajahatkarim3.easyflipview.EasyFlipView;
import java.util.ArrayList;
import java.util.HashMap;

public class TarotResultAdapter extends RecyclerView.Adapter<TarotResultAdapter.TarotResultViewHolder> {
    private static final String TAG = TarotResultAdapter.class.getSimpleName();
    private ArrayList<Tarot> mTarots;
    private Context mContext;
    private HashMap<Integer, TarotResultViewHolder> mMap = new HashMap<>();

    public TarotResultAdapter(ArrayList<Tarot> tarots, Context context) {
        this.mTarots = tarots;
        this.mContext = context;
    }

    @NonNull
    @Override
    public TarotResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TarotResultViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_tarot_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TarotResultViewHolder holder, int position) {
        mMap.put(position, holder);
        final Tarot mTarot = mTarots.get(position);
        setValues(holder, mTarot);
        checkFlipState(holder, mTarot);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.playSound(mContext, 4);
                if (mTarot.isFlipped()) {
                    mTarot.setFlipped(false);
                } else {
                    mTarot.setFlipped(true);
                }
                holder.mFlipper.setFlipDuration(600);
                holder.mFlipper.flipTheView();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTarots.size();
    }

    public class TarotResultViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon_tarot;
        private TextView mTitle_tarot, mDescription_tarot;
        private EasyFlipView mFlipper;

        public TarotResultViewHolder(View itemView) {
            super(itemView);

            mIcon_tarot = (ImageView) itemView.findViewById(R.id.mIcon_tarot);

            mTitle_tarot = (TextView) itemView.findViewById(R.id.mTitle_tarot);
            mDescription_tarot = (TextView) itemView.findViewById(R.id.mDescription_tarot);

            mFlipper = (EasyFlipView) itemView.findViewById(R.id.mFlipper);
        }
    }

//--------------------------------------------------------------------------------------------------

    private void setValues(TarotResultViewHolder holder, Tarot tarot) {
        Utils.loadImageFromAsset(mContext, holder.mIcon_tarot, tarot.getIcon());
        holder.mTitle_tarot.setText(tarot.getTitle());
        holder.mDescription_tarot.setText(tarot.getDescription());
    }

    private void checkFlipState(TarotResultViewHolder holder, Tarot tarot) {
        if (holder.mFlipper.getCurrentFlipState() == EasyFlipView.FlipState.FRONT_SIDE && tarot.isFlipped()) {
            holder.mFlipper.setFlipDuration(0);
            holder.mFlipper.flipTheView();
        } else if (holder.mFlipper.getCurrentFlipState() == EasyFlipView.FlipState.BACK_SIDE && !tarot.isFlipped()) {
            holder.mFlipper.setFlipDuration(0);
            holder.mFlipper.flipTheView();
        }
    }

    public void startFlipAnimation(int position) {
//        Utils.playSound(mContext, 4);
        mMap.get(position).mFlipper.flipTheView();
    }

}
