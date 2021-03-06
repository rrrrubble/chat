package com.by.communication.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import com.by.communication.App;
import com.by.communication.util.ConstantUtil;
import com.by.communication.util.TimeUtil;
import com.by.communication.widgit.adapter.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.io.File;

/**
 * Produced a lot of bug on 2017/3/31.
 */

@Entity
public class ChatMessage implements MultiItemEntity, Parcelable {

    public static final int TEXT  = 11;
    public static final int IMAGE = 12;
    public static final int AUDIO = 13;
    public static final int FILE  = 14;
    public static final int VIDEO = 15;

    public static final int TEXT_SELF  = 101;
    public static final int IMAGE_SELF = 102;
    public static final int AUDIO_SELF = 103;
    public static final int FILE_SELF  = 104;
    public static final int VIDEO_SELF = 105;

    public static final int TEXT_OTHER  = 201;
    public static final int IMAGE_OTHER = 202;
    public static final int AUDIO_OTHER = 203;
    public static final int FILE_OTHER  = 204;
    public static final int VIDEO_OTHER = 205;

    public static final int SENDING      = 21;
    public static final int SEND_SUCCESS = 22;
    public static final int SEND_FAILED  = 23;

    @Id
    @SerializedName("message_id")
    private Long id;

    private long   group_id;   //聊天组用到
    private long   sender_id;   //发送者id
    private long   receiver_id;  //接受者id
    private int    content_type; //内容类型
    private String content;  //内容
    private String local_root_path;
    private String path;
    private int    length;    //文件长度
    private int visible = 1;
    private String timestamp;

    @Transient
    private float progress;
    @Transient
    private int   download_status;  //0不确定 1 没有下载  2 下载中 3 已下载

    private int status = SEND_SUCCESS;  //发送状态

    protected ChatMessage(Parcel in)
    {
        id = in.readLong();
        group_id = in.readLong();
        sender_id = in.readLong();
        receiver_id = in.readLong();
        content_type = in.readInt();
        content = in.readString();
        visible = in.readInt();
        timestamp = in.readString();
        status = in.readInt();
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in)
        {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size)
        {
            return new ChatMessage[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(id);
        parcel.writeLong(group_id);
        parcel.writeLong(sender_id);
        parcel.writeLong(receiver_id);
        parcel.writeInt(content_type);
        parcel.writeString(content);
        parcel.writeInt(visible);
        parcel.writeString(timestamp);
        parcel.writeInt(status);
    }

    public boolean isAudio()
    {
        return content_type == AUDIO;
    }

    public boolean isSending()
    {
        return status == SENDING;
    }

    @IntDef({SENDING, SEND_SUCCESS, SEND_FAILED})
    @interface SendStatus {

    }


    public ChatMessage(long id, long sender_id, long receiver_id, int content_type, String content, String file_name, int length, int status)
    {
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content_type = content_type;
        this.content = content;
        this.path = file_name;
        this.length = length;
        this.status = status;
        timestamp = TimeUtil.getCurrentTimeString();
    }

    @Generated(hash = 1040363771)
    public ChatMessage(Long id, long group_id, long sender_id, long receiver_id, int content_type, String content, String local_root_path,
                       String path, int length, int visible, String timestamp, int status)
    {
        this.id = id;
        this.group_id = group_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.content_type = content_type;
        this.content = content;
        this.local_root_path = local_root_path;
        this.path = path;
        this.length = length;
        this.visible = visible;
        this.timestamp = timestamp;
        this.status = status;
    }

    @Generated(hash = 2271208)
    public ChatMessage()
    {
    }


    @Override
    public int getItemType()
    {
        long userId = App.getInstance().getUserId();
        if (userId == sender_id) {
            switch (content_type) {
                case TEXT:
                    return TEXT_SELF;
                case IMAGE:
                    return IMAGE_SELF;
                case AUDIO:
                    return AUDIO_SELF;
                case FILE:
                    return FILE_SELF;
                case VIDEO:
                    return VIDEO_SELF;
            }
        } else {
            switch (content_type) {
                case TEXT:
                    return TEXT_OTHER;
                case IMAGE:
                    return IMAGE_OTHER;
                case AUDIO:
                    return AUDIO_OTHER;
                case FILE:
                    return FILE_OTHER;
                case VIDEO:
                    return VIDEO_OTHER;
            }
        }
        return TEXT_SELF;
    }

    public void setDownload_status(int download_status)
    {
        this.download_status = download_status;
    }

    public int getDownload_status()
    {
        if (download_status == 0) {
            File file = new File(ConstantUtil.FILE_BASE_PATH + path);
            if (file.exists()) {
                download_status = 3;
            } else {
                download_status = 1;
            }
        }
        return download_status;
    }

    public float getProgress()
    {
        return progress;
    }

    public void setProgress(float progress)
    {
        this.progress = progress;
        if (onProgressListener != null) {
            onProgressListener.onProgress(progress);
        }
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(@SendStatus int status)
    {
        this.status = status;
    }

    public Long getId()
    {
        return this.id;
    }


    public void setId(long id)
    {
        this.id = id;
    }


    public long getSender_id()
    {
        return this.sender_id;
    }


    public void setSender_id(long sender_id)
    {
        this.sender_id = sender_id;
    }


    public long getReceiver_id()
    {
        return this.receiver_id;
    }


    public void setReceiver_id(long receiver_id)
    {
        this.receiver_id = receiver_id;
    }


    public int getContent_type()
    {
        return this.content_type;
    }


    public void setContent_type(int content_type)
    {
        this.content_type = content_type;
    }


    public String getContent()
    {
        return this.content;
    }


    public void setContent(String content)
    {
        this.content = content;
    }


    public String getTimestamp()
    {
        return this.timestamp;
    }


    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }


    public int getVisible()
    {
        return this.visible;
    }


    public void setVisible(int visible)
    {
        this.visible = visible;
    }


    public long getGroup_id()
    {
        return this.group_id;
    }


    public void setGroup_id(long group_id)
    {
        this.group_id = group_id;
    }

    public String getPath()
    {
        return this.path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getLength()
    {
        return this.length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    @Transient
    private OnProgressListener onProgressListener;
    @Transient
    private boolean            isPlaying;
    @Transient
    private int                play_time;
    @Transient
    private OnPlayListener     onPlayListener;

    public OnProgressListener getOnProgressListener()
    {
        return onProgressListener;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener)
    {
        this.onProgressListener = onProgressListener;
    }

    public String getLocal_root_path()
    {
        return this.local_root_path;
    }

    public void setLocal_root_path(String local_root_path)
    {
        this.local_root_path = local_root_path;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public void setPlaying(boolean playing)
    {
        isPlaying = playing;
    }

    public int getPlay_time()
    {
        return play_time;
    }

    public void setPlay_time(int play_time)
    {
        this.play_time = play_time;
        if (onPlayListener != null) {
            onPlayListener.onPlay(play_time);
        }
    }

    public OnPlayListener getOnPlayListener()
    {
        return onPlayListener;
    }

    public void setOnPlayListener(OnPlayListener onPlayListener)
    {
        this.onPlayListener = onPlayListener;
    }

    public interface OnProgressListener {
        void onProgress(float progress);
    }

    public interface OnPlayListener {
        void onPlay(int time);
    }

    @Override
    public String toString()
    {
        return "ChatMessage{" +
                "id=" + id +
                ", group_id=" + group_id +
                ", sender_id=" + sender_id +
                ", receiver_id=" + receiver_id +
                ", content_type=" + content_type +
                ", content='" + content + '\'' +
                ", local_root_path='" + local_root_path + '\'' +
                ", path='" + path + '\'' +
                ", length=" + length +
                ", visible=" + visible +
                ", timestamp='" + timestamp + '\'' +
                ", progress=" + progress +
                ", download_status=" + download_status +
                ", status=" + status +
                '}';
    }
}
