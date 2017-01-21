package com.yhcloud.thankyou.module.homework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.homework.bean.QuestionBean;
import com.yhcloud.thankyou.utils.Tools;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/19.
 */

public class StudentHomeworkBlankListAdapter extends RecyclerView.Adapter<StudentHomeworkBlankListAdapter.HomeworkBlankViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<QuestionBean> mBeen;
    private ISaveDataListener mISaveDataListener;

    public StudentHomeworkBlankListAdapter(Context context, ArrayList<QuestionBean> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBeen = list;
    }

    @Override
    public HomeworkBlankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_homeworkinfo_blank_list, parent, false);
        HomeworkBlankViewHolder viewHolder = new HomeworkBlankViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeworkBlankViewHolder holder, final int position) {
        holder.mEditText.setHint(MessageFormat.format("请输入第{0}格答案", Tools.toChineseLower(position + 1)));
        if (null != mBeen.get(position).getAnswerContent()) {
            holder.mEditText.setText(mBeen.get(position).getAnswerContent());
        } else {
            holder.mEditText.setText("");
        }
        if (null != mISaveDataListener) {
            holder.mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mBeen.get(position).setAnswerContent(s.toString().trim());
                    mISaveDataListener.saveDate(mBeen);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeen.size();
    }

    public void setISaveDataListener(ISaveDataListener iSaveDataListener) {
        this.mISaveDataListener = iSaveDataListener;
    }

    public static class HomeworkBlankViewHolder extends RecyclerView.ViewHolder {
        EditText mEditText;
        public HomeworkBlankViewHolder(View itemView) {
            super(itemView);
            mEditText = (EditText) itemView.findViewById(R.id.et_homeworkinfo_blank_list);
        }
    }

    public interface ISaveDataListener {
        void saveDate(ArrayList<QuestionBean> list);
    }
}
