package com.function.youtubeparser.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LActivityUtil;
import com.core.utilities.LImageUtil;
import com.core.utilities.LLog;
import com.core.utilities.LScreenUtil;
import com.function.youtubeparser.models.videos.Video;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import loitp.core.R;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private ArrayList<Video> videos;
    private int rowLayout;
    private Context mContext;
    private final String TAG = getClass().getSimpleName();
    private int ivHeight;

    public VideoAdapter(ArrayList<Video> list, int rowLayout, Context context) {
        this.videos = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
        int screenWidth = LScreenUtil.INSTANCE.getScreenWidth();
        ivHeight = screenWidth * 9 / 16;
    }

    public void clearData() {
        if (videos != null) {
            videos.clear();
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder, final int position) {
        final Video currentVideo = videos.get(position);
        String pubDateString = currentVideo.getDate();
        //retrieve video link
        viewHolder.tvTitle.setText(currentVideo.getTitle());
        viewHolder.tvDate.setText(pubDateString);
        LImageUtil.INSTANCE.load(mContext, currentVideo.getCoverLink(), viewHolder.iv);
        viewHolder.itemView.setOnClickListener(view -> {
            //work lay thong tin video
            /*VideoStats videoStats = new VideoStats();
            String url = videoStats.generateStatsRequest(videoId, YoutubeParserActivity.API_KEY);
            LLog.d(TAG, "url:" + url);
            videoStats.execute(url);
            videoStats.onFinish(new VideoStats.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Statistics stats) {
                    LLog.d(TAG, "onTaskCompleted: " + new Gson().toJson(stats));
                    String body = "Views: " + stats.getViewCount() + "\n" +
                            "Like: " + stats.getLikeCount() + "\n" +
                            "Dislike: " + stats.getDislikeCount() + "\n" +
                            "Number of comment: " + stats.getCommentCount() + "\n" +
                            "Number of favourite: " + stats.getFavoriteCount();

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    dialogBuilder.setTitle("Stats for: \"" + videoTitle + "\"");
                    dialogBuilder.setMessage(body);
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialogBuilder.show();
                }

                @Override
                public void onError() {
                    LToast.showShort(mContext, "Unable to get statistic for this video. Please try again");
                }
            });*/
            LLog.d(TAG, "id " + videos.get(position).getVideoId());
            LLog.d(TAG, "getLinkYoutube " + videos.get(position).getLinkYoutube());
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position).getLinkYoutube())));
            LActivityUtil.tranIn(mContext);
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public ArrayList<Video> getList() {
        return videos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            iv = itemView.findViewById(R.id.iv);
            if (ivHeight != 0) {
                iv.getLayoutParams().height = ivHeight;
                iv.requestLayout();
            }
        }
    }
}