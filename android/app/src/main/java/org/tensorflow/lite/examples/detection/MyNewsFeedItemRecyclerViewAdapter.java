package org.tensorflow.lite.examples.detection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.examples.detection.NewsFeedItemFragment.OnListFragmentInteractionListener;
import org.tensorflow.lite.examples.detection.dummy.DummyContent.DummyItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsFeedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsFeedItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyNewsFeedItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newsfeeditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DummyItem item =  holder.mItem = mValues.get(position);

        TextView user = holder.mView.findViewById(R.id.newsfeed_username);
        user.setText(item.user);


        CircleImageView avatar = holder.mView.findViewById(R.id.newsfeed_avatar);
        avatar.setImageResource(item.avatar);

        TextView content = holder.mView.findViewById(R.id.newsfeed_content);
        content.setText(item.content);

        ImageView imgView = holder.mView.findViewById(R.id.newsfeed_imageView);
        imgView.setImageBitmap(BitmapFactory.decodeResource(imgView.getContext().getResources(), item.image));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("ITEMS", "A " + mValues.size());
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

        }

        @Override
        public String toString() {
            return super.toString() + " '" +  "'";
        }
    }
}
