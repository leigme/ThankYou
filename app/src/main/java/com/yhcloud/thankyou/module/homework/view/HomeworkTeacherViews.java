package com.yhcloud.thankyou.module.homework.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.SubmitCallBack;
import com.yhcloud.thankyou.module.homework.adapter.TeacherHomeworkObjectiveListAdapter;
import com.yhcloud.thankyou.module.homework.adapter.TeacherHomeworkSubjectiveListAdapter;
import com.yhcloud.thankyou.module.homework.bean.TeacherQuestionBean;
import com.yhcloud.thankyou.module.image.view.BigImageActivity;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.DividerItemDecoration;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/20.
 */

public class HomeworkTeacherViews {

    private Context mContext;
    private LayoutInflater mInflater;

    public HomeworkTeacherViews(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public View getSubjectiveView(TeacherQuestionBean teacherQuestionBean, int i, int j) {

        View view = mInflater.inflate(R.layout.fragment_homeworkinfo, null);
        TextView title = (TextView) view.findViewById(R.id.tv_homework_title);
        TextView content = (TextView) view.findViewById(R.id.tv_homework_title_value);
        TextView pageNum = (TextView) view.findViewById(R.id.tv_homework_page_num);
        RecyclerView questionList = (RecyclerView) view.findViewById(R.id.rv_homework_list);
        TextView answerTitle = (TextView) view.findViewById(R.id.tv_homework_answer_title);
        TextView answer = (TextView) view.findViewById(R.id.tv_homework_answer_value);

        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        questionList.setBackgroundColor(Color.parseColor("#FFFFFF"));
        pageNum.setText(MessageFormat.format("{0}/{1}", i + 1, j));
        title.setText(teacherQuestionBean.getTitle());
        content.setText(teacherQuestionBean.getContent());
        answer.setVisibility(View.GONE);
        final ArrayList<String> subjectiveImageList = new ArrayList<>();
        for (String s: teacherQuestionBean.getQuestionPic()) {
            subjectiveImageList.add(s);
        }
        TeacherHomeworkSubjectiveListAdapter thsla = new TeacherHomeworkSubjectiveListAdapter(mContext, subjectiveImageList);
        questionList.setLayoutManager(new GridLayoutManager(mContext, 4));
        thsla.setIOnClickListener(new SubmitCallBack() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Intent intent = new Intent(mContext, BigImageActivity.class);
                intent.putExtra(Constant.PAGE_NUM, position);
                intent.putStringArrayListExtra(Constant.IMAGEURLS, subjectiveImageList);
                mContext.startActivity(intent);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });
        questionList.setAdapter(thsla);
        answerTitle.setVisibility(View.VISIBLE);
        String aw = " ";
        for (String s: teacherQuestionBean.getCorrectAnswer()) {
            aw += s + " ";
        }
        answerTitle.setText(aw);
        return view;
    }

    public View getObjectiveView(TeacherQuestionBean teacherQuestionBean, int i, int j) {
        View view = mInflater.inflate(R.layout.fragment_homeworkinfo, null);

        TextView title = (TextView) view.findViewById(R.id.tv_homework_title);
        TextView content = (TextView) view.findViewById(R.id.tv_homework_title_value);
        TextView pageNum = (TextView) view.findViewById(R.id.tv_homework_page_num);
        RecyclerView questionList = (RecyclerView) view.findViewById(R.id.rv_homework_list);
        TextView answerTitle = (TextView) view.findViewById(R.id.tv_homework_answer_title);
        TextView answer = (TextView) view.findViewById(R.id.tv_homework_answer_value);

        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        questionList.setBackgroundColor(Color.parseColor("#FFFFFF"));
        pageNum.setText(MessageFormat.format("{0}/{1}", i + 1, j));
        title.setText(teacherQuestionBean.getTitle());
        content.setText(teacherQuestionBean.getContent());
        final ArrayList<String> questionBeen = new ArrayList<>();
        for (TeacherQuestionBean.QuestionExtendBean questionExtendBean: teacherQuestionBean.getQuestionExtend()) {
            questionBeen.add(MessageFormat.format("{0}. {1}", questionExtendBean.getPropertyName(), questionExtendBean.getPropertyValue()));
        }
        TeacherHomeworkObjectiveListAdapter thola = new TeacherHomeworkObjectiveListAdapter(mContext, questionBeen);
        questionList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        questionList.setLayoutManager(new LinearLayoutManager(mContext));
        questionList.setAdapter(thola);
        answerTitle.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);
        String strAnswer = " ";
        for (String s: teacherQuestionBean.getCorrectAnswer()) {
            strAnswer += s + " ";
        }
        answer.setText(strAnswer);
        return view;
    }
}
