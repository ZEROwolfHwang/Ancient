
package com.liuzr.ancient.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.liuzr.ancient.R;
import com.liuzr.ancient.ui.theme.TraditionalColorNamer;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BgColorPickDialogFragment extends DialogFragment {

    private ListView colorListView;

    private OnBackgroundColorChangedListener onBackgroundColorChangedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bg_color_pick, container, false);
        getDialog().setTitle(getResources().getString(R.string.please_choose_bg_color));
        colorListView = root.findViewById(R.id.bg_color_list_view);
        colorListView.setAdapter(new BgColorPickAdapter(getActivity()));

        return root;
    }

    private class BgColorPickAdapter extends BaseAdapter {

        private class ViewHolder {
            TextPointView colorHint;
            TextView colorName;
        }

        private List<TraditionalColorNamer> colorNamerList;
        private Context context;

        public BgColorPickAdapter(Context context) {
            this.context = context;
            this.colorNamerList = TraditionalColorNamer.getAllColorNamer();
        }

        @Override
        public int getCount() {
            return colorNamerList.size();
        }

        @Override
        public Object getItem(int position) {
            return colorNamerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.view_bg_color_pick, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.colorHint = (TextPointView) convertView.findViewById(R.id.bg_color_hint);
                viewHolder.colorName = (TextView) convertView.findViewById(R.id.bg_color_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final TraditionalColorNamer colorNamer = colorNamerList.get(position);
            viewHolder.colorName.setText(colorNamer.getColorName());
            viewHolder.colorHint.setCircleBackgroundColor(R.color.colorPrimary);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (onBackgroundColorChangedListener != null) {
                        onBackgroundColorChangedListener.onBackgroundColorChanged(
                                colorNamer.getColorRes());
                    }
                }
            });
            return convertView;
        }

    }

    public void setOnBackgroundColorChangedListener(
            OnBackgroundColorChangedListener onBackgroundColorChangedListener) {
        this.onBackgroundColorChangedListener = onBackgroundColorChangedListener;
    }

    public interface OnBackgroundColorChangedListener {
        void onBackgroundColorChanged(int newColorRes);
    }

}
