package com.pooyeshgaran.tarootfaal.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pooyeshgaran.tarootfaal.Entities.Tarot;
import com.pooyeshgaran.tarootfaal.R;
import com.pooyeshgaran.tarootfaal.Utils;
import com.wajahatkarim3.easyflipview.EasyFlipView;
import java.util.ArrayList;
import java.util.HashMap;

public class TarotAdapter extends RecyclerView.Adapter<TarotAdapter.TarotViewHolder> {
    private static final String TAG = TarotAdapter.class.getSimpleName();
    private ArrayList<Tarot> mTarots;
    private Context mContext;
    private HashMap<Integer, TarotViewHolder> mMap = new HashMap<>();

    public TarotAdapter(ArrayList<Tarot> tarots, Context context) {
        this.mTarots = tarots;
        this.mContext = context;
    }

    @Override
    public TarotAdapter.TarotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_tarot, parent, false);
        return new TarotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TarotAdapter.TarotViewHolder holder, final int position) {
        mMap.put(position, holder);
        Tarot mTarot = mTarots.get(position);
        Utils.loadImageFromAsset(mContext, holder.mIcon_tarot2, mTarot.getIcon());

        holder.mIcon_tarot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.playSound(mContext, 1);
                hideTarotIcon(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTarots.size();
    }

    public class TarotViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon_tarot1, mIcon_tarot2;
        private EasyFlipView mFlipper;

        public TarotViewHolder(View itemView) {
            super(itemView);

            mIcon_tarot1 = (ImageView) itemView.findViewById(R.id.mIcon_tarot1);
            mIcon_tarot2 = (ImageView) itemView.findViewById(R.id.mIcon_tarot2);
            mFlipper = (EasyFlipView) itemView.findViewById(R.id.mFlipper);
        }
    }

// -------------------------------------------------------------------------------------------------

    public void visibleTarotIcon(int position) {
        mMap.get(position).mIcon_tarot1.setImageResource(R.drawable.card);
        mMap.get(position).mIcon_tarot1.setTag(R.drawable.card);

        showTarotAnimation(mMap.get(position));
    }
    private void hideTarotIcon(int position) {
        if (mMap.get(position).mIcon_tarot1.getTag() != null) {
            mMap.get(position).mIcon_tarot1.setImageResource(R.drawable.tarot_hidden);
            mTarotFragment.visibleTarot(position);
        }
    }

    private void showTarotAnimation(TarotViewHolder holder) {
        YoYo.with(Techniques.FadeInUp)
                .duration(500)
                .repeat(0)
                .playOn(holder.itemView.findViewById(R.id.mIcon_tarot1));
    }
    public void startFlipAnimation(int position) {
        try {
//            Utils.playSound(mContext, 4);
            mMap.get(position).mFlipper.flipTheView();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage()+"");
        }
    }

    public void setTarotTag(int position) {
        mMap.get(position).mIcon_tarot1.setTag(null);
    }

    public boolean isTarotFalAvailable(int cardCount) {
        boolean mFlag = false;
        for (int i = 0; i < cardCount; i++) {
            if (mMap.get(i).mIcon_tarot1.getTag() == null) {
                mFlag = false;
                break;
            } else {
                mFlag = true;
            }
        }
        return mFlag;
    }



}
