package com.pooyeshgaran.tarootfaal.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mohssenteck.pishgo.Entities.TarotCard;
import com.mohssenteck.pishgo.Fragments.TarotFragment;
import com.mohssenteck.pishgo.R;
import com.mohssenteck.pishgo.Utils;
import com.pooyeshgaran.tarootfaal.Entities.TarotCard;
import com.pooyeshgaran.tarootfaal.Utils;

import java.util.ArrayList;

public class TarotCardAdapter extends RecyclerView.Adapter<TarotCardAdapter.TarotCardViewHolder> {
    private static final String TAG = TarotCardAdapter.class.getSimpleName();
    private ArrayList<TarotCard> mTarotCards;
    private Context mContext;

    public TarotCardAdapter(ArrayList<TarotCard> tarotCards, Context context) {
        this.mTarotCards = tarotCards;
        this.mContext = context;
    }

    @Override
    public TarotCardAdapter.TarotCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_tarot_card, parent, false);
        return new TarotCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TarotCardAdapter.TarotCardViewHolder holder, int position) {
        final TarotCard mTarotCard = mTarotCards.get(position);
        setValues(holder, mTarotCard);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.playSound(mContext, 1);
                mTarotFragment.openTarotCard(mTarotCard);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTarotCards.size();
    }

    public class TarotCardViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon_tarot_card;

        public TarotCardViewHolder(View itemView) {
            super(itemView);

            mIcon_tarot_card = (ImageView) itemView.findViewById(R.id.mIcon_tarot_card);
        }
    }

// -------------------------------------------------------------------------------------------------

    private void setValues(TarotCardViewHolder holder, TarotCard tarotCard) {
        Utils.loadImageFromAsset(mContext, holder.mIcon_tarot_card, tarotCard.getIcon());
    }

}
