package com.epson.iprojection.ui.activities.moderator.thumbnail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.ColorUtils;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.ui.activities.moderator.TouchPosGettableImageView;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class InflaterThumbnailAdapter extends BaseAdapter implements View.OnTouchListener {
    public static final int ME_ID = 0;
    private final Context _context;
    private final IOnDropListener _impl;
    private final IOnDragStateListener _implDragState;
    private final LayoutInflater _layoutInflater;
    private final ArrayList<D_ThumbnailInfo> _listDtoInflater;
    private int _imagePixelWidth = 0;
    private int _imagePixelHeight = 0;
    private ArrayList<D_MppLayoutInfo> _layout = null;

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return true;
    }

    public InflaterThumbnailAdapter(Context context, ArrayList<D_ThumbnailInfo> arrayList, IOnDropListener iOnDropListener, IOnDragStateListener iOnDragStateListener) {
        this._context = context;
        this._layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this._listDtoInflater = arrayList;
        this._impl = iOnDropListener;
        this._implDragState = iOnDragStateListener;
    }

    /* loaded from: classes.dex */
    public class ViewHolder {
        public int _position;
        public ImageView _thumbnail;
        public TextView _userName;
        public boolean isNoImage;
        public float lastTouchedX;
        public float lastTouchedY;

        public ViewHolder() {
        }
    }

    public void setImageWH(int i, int i2) {
        this._imagePixelWidth = i;
        this._imagePixelHeight = i2;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (this._listDtoInflater == null) {
            Lg.e("inflater is null.");
            return null;
        }
        if (view == null) {
            view = this._layoutInflater.inflate(R.layout.inflater_thumbnail, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder._userName = (TextView) view.findViewById(R.id.txt_thumbnail_user_name);
            viewHolder._thumbnail = (ImageView) view.findViewById(R.id.image_thumbnail);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this._imagePixelWidth, this._imagePixelHeight);
            layoutParams.gravity = 49;
            viewHolder._thumbnail.setLayoutParams(layoutParams);
            view.setTag(viewHolder);
            view.setOnTouchListener(this);
            mySetOnDragListener(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder._userName.setText(this._listDtoInflater.get(i).getUserName());
        viewHolder._position = i;
        if (this._listDtoInflater.get(i).bufByte != null) {
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(this._listDtoInflater.get(i).bufByte, 0, this._listDtoInflater.get(i).bufByte.length);
            if (decodeByteArray != null) {
                viewHolder._thumbnail.setImageBitmap(decodeByteArray);
            }
            viewHolder._userName.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            viewHolder.isNoImage = false;
        } else if (this._listDtoInflater.get(i).disconnected) {
            viewHolder._thumbnail.setImageResource(R.drawable.no_image);
            viewHolder._userName.setTextColor(MethodUtil.compatGetColor(this._context, R.color.BlackGrayOut));
            viewHolder.isNoImage = true;
        } else {
            viewHolder._thumbnail.setImageResource(R.drawable.no_image);
            viewHolder._userName.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            viewHolder.isNoImage = true;
        }
        setUserNameBgColor(viewHolder, this._listDtoInflater.get(i).userUniqueId);
        return view;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<D_ThumbnailInfo> arrayList = this._listDtoInflater;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        ArrayList<D_ThumbnailInfo> arrayList = this._listDtoInflater;
        if (arrayList == null) {
            return null;
        }
        return arrayList.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        D_ThumbnailInfo d_ThumbnailInfo = (D_ThumbnailInfo) getItem(i);
        if (d_ThumbnailInfo != null) {
            return d_ThumbnailInfo.userUniqueId;
        }
        return -1L;
    }

    public void setUserNameBgColorActive(ViewHolder viewHolder) {
        viewHolder._userName.setBackgroundColor(ColorUtils.get(R.color.thumb_active));
    }

    public void setUserNameBgColorInactive(ViewHolder viewHolder) {
        viewHolder._userName.setBackgroundColor(ColorUtils.get(R.color.thumb_inactive));
    }

    public void setUserNameBgColorNotSelected(ViewHolder viewHolder) {
        viewHolder._userName.setBackgroundColor(ColorUtils.get(R.color.BgMppThumbNotSelected));
    }

    public void updateLayout(ArrayList<D_MppLayoutInfo> arrayList) {
        this._layout = arrayList;
    }

    private void setUserNameBgColor(ViewHolder viewHolder, long j) {
        ArrayList<D_MppLayoutInfo> arrayList = this._layout;
        if (arrayList != null) {
            Iterator<D_MppLayoutInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                D_MppLayoutInfo next = it.next();
                if (next.uniqueId == j) {
                    if (next.active) {
                        setUserNameBgColorActive(viewHolder);
                        return;
                    } else {
                        setUserNameBgColorInactive(viewHolder);
                        return;
                    }
                }
            }
        }
        setUserNameBgColorNotSelected(viewHolder);
    }

    private void mySetOnDragListener(View view) {
        view.setOnDragListener(new View.OnDragListener() { // from class: com.epson.iprojection.ui.activities.moderator.thumbnail.InflaterThumbnailAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view2, DragEvent dragEvent) {
                return InflaterThumbnailAdapter.this.m105x52b86e7d(view2, dragEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$mySetOnDragListener$0$com-epson-iprojection-ui-activities-moderator-thumbnail-InflaterThumbnailAdapter  reason: not valid java name */
    public /* synthetic */ boolean m105x52b86e7d(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action == 1) {
            this._implDragState.onStartDrag();
            return true;
        } else if (action != 2) {
            if (action != 3) {
                if (action == 4) {
                    this._implDragState.onEndDrag();
                }
                return false;
            }
            boolean isEnabledDrop = this._implDragState.isEnabledDrop();
            this._implDragState.onEndDrag();
            if (isEnabledDrop) {
                try {
                    this._impl.onDropFromWindowToList(((TouchPosGettableImageView) dragEvent.getLocalState()).getWindID());
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Lg.e("x=" + motionEvent.getX() + ",y=" + motionEvent.getY());
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.lastTouchedX = motionEvent.getX();
        viewHolder.lastTouchedY = motionEvent.getY();
        return false;
    }
}
