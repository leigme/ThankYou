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
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.homework.adapter.StudentHomeworkBlankListAdapter;
import com.yhcloud.thankyou.module.homework.adapter.StudentHomeworkSubjectiveListAdapter;
import com.yhcloud.thankyou.module.homework.adapter.StudentHomeworkRadioListAdpater;
import com.yhcloud.thankyou.module.homework.bean.StudentQuestionBean;
import com.yhcloud.thankyou.module.homework.bean.QuestionBean;
import com.yhcloud.thankyou.module.image.view.BigImageActivity;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.DividerItemDecoration;
import com.yhcloud.thankyou.utils.Tools;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/19.
 */

public class HomeworkStudentViews {

    private Context mContext;
    private LayoutInflater mInflater;

    public HomeworkStudentViews(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    //创建主观题视图
    public View getSubjectiveView(StudentQuestionBean studentQuestionBean, int i, int j) {

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
        title.setText(studentQuestionBean.getQuestionTitle());
        content.setText(studentQuestionBean.getQuestionTitleName());
        answer.setVisibility(View.GONE);
        final ArrayList<String> subjectiveImageList = new ArrayList<>();
        for (String s: studentQuestionBean.getAnswerPic()) {
            subjectiveImageList.add(s);
        }
        StudentHomeworkSubjectiveListAdapter hsla = new StudentHomeworkSubjectiveListAdapter(mContext, subjectiveImageList);
        questionList.setLayoutManager(new GridLayoutManager(mContext, 4));
        hsla.setIOnClickListener(new ItemClinkListener() {
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
        questionList.setAdapter(hsla);
        answerTitle.setVisibility(View.VISIBLE);
        String aw = " ";
        for (String s: studentQuestionBean.getAnswerContent()) {
            aw += s + " ";
        }
        answerTitle.setText(aw);
        return view;
    }

    //创建学生端单选题视图
    public View getRadioView(final StudentQuestionBean studentQuestionBean, int i, int j, boolean status) {

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
        title.setText(studentQuestionBean.getQuestionTitle());
        content.setText(studentQuestionBean.getQuestionTitleName());
        final ArrayList<QuestionBean> questionBeen = new ArrayList<>();
        for (String s: studentQuestionBean.getQuestionContent()) {
            QuestionBean qb = new QuestionBean(s);
            questionBeen.add(qb);
        }
        for (String s: studentQuestionBean.getAnswerContent()) {
            questionBeen.get(Tools.getSelectNum(s)).setStatus(true);
        }
        final StudentHomeworkRadioListAdpater hrla = new StudentHomeworkRadioListAdpater(mContext, questionBeen);
        questionList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        questionList.setLayoutManager(new LinearLayoutManager(mContext));
        questionList.setAdapter(hrla);
        if (!status) {
            answer.setVisibility(View.GONE);
            answerTitle.setVisibility(View.GONE);
            hrla.setIOnClickListener(new ItemClinkListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    ArrayList<String> list = new ArrayList<>();
                    for (QuestionBean qb: questionBeen) {
                        qb.setStatus(false);
                    }
                    questionBeen.get(position).setStatus(true);
                    for (int j = 0; j < questionBeen.size(); j++) {
                        if (questionBeen.get(j).isStatus()) {
                            list.add(Tools.getSelectLetter(j));
                        }
                    }
                    studentQuestionBean.setAnswerContent(list);
                    hrla.refreshData(questionBeen);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
        } else {
            answerTitle.setVisibility(View.VISIBLE);
            answer.setVisibility(View.VISIBLE);
            String aw= " ";
            for (String s: studentQuestionBean.getCorrectAnswer()) {
                aw += s + " ";
            }
            answer.setText(aw);
        }
        return view;
    }

    //创建学生端多选题视图
    public View getChoiceView(final StudentQuestionBean studentQuestionBean, int i, int j, boolean status) {

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
        title.setText(studentQuestionBean.getQuestionTitle());
        content.setText(studentQuestionBean.getQuestionTitleName());
        final ArrayList<QuestionBean> questionBeen = new ArrayList<>();
        for (String s: studentQuestionBean.getQuestionContent()) {
            QuestionBean qb = new QuestionBean(s);
            questionBeen.add(qb);
        }
        for (String s: studentQuestionBean.getAnswerContent()) {
            questionBeen.get(Tools.getSelectNum(s)).setStatus(true);
        }
        final StudentHomeworkRadioListAdpater hrla = new StudentHomeworkRadioListAdpater(mContext, questionBeen);
        questionList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        questionList.setLayoutManager(new LinearLayoutManager(mContext));
        questionList.setAdapter(hrla);
        if (!status) {
            answer.setVisibility(View.GONE);
            answerTitle.setVisibility(View.GONE);
            hrla.setIOnClickListener(new ItemClinkListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    ArrayList<String> list = new ArrayList<>();
                    if (questionBeen.get(position).isStatus()) {
                        questionBeen.get(position).setStatus(false);
                    } else {
                        questionBeen.get(position).setStatus(true);
                    }
                    for (int j = 0; j < questionBeen.size(); j++) {
                        if (questionBeen.get(j).isStatus()) {
                            list.add(Tools.getSelectLetter(j));
                        }
                    }
                    studentQuestionBean.setAnswerContent(list);
                    hrla.refreshData(questionBeen);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
        } else {
            answerTitle.setVisibility(View.VISIBLE);
            answer.setVisibility(View.VISIBLE);
            String aw = " ";
            for (String s: studentQuestionBean.getCorrectAnswer()) {
                aw += s + " ";
            }
            answer.setText(aw);
        }
        return view;
    }

    //创建学生填空题视图
    public View getBlankView(final StudentQuestionBean studentQuestionBean, int i, int j, boolean status) {

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
        title.setText(studentQuestionBean.getQuestionTitle());
        content.setText(studentQuestionBean.getQuestionTitleName());
        if (!status) {
            answer.setVisibility(View.GONE);
            answerTitle.setVisibility(View.GONE);
            final ArrayList<QuestionBean> questionBeen = new ArrayList<>();
            for (String s: studentQuestionBean.getQuestionContent()) {
                QuestionBean qb = new QuestionBean();
                questionBeen.add(qb);
            }
            for (int k = 0; k < questionBeen.size(); k++) {
                if (null != studentQuestionBean.getAnswerContent() && 0 != studentQuestionBean.getAnswerContent().size()) {
                    questionBeen.get(k).setAnswerContent(studentQuestionBean.getAnswerContent().get(k));
                }
            }
            final StudentHomeworkBlankListAdapter hbla = new StudentHomeworkBlankListAdapter(mContext, questionBeen);
            questionList.setLayoutManager(new LinearLayoutManager(mContext));
            questionList.setAdapter(hbla);
            hbla.setISaveDataListener(new StudentHomeworkBlankListAdapter.ISaveDataListener() {
                @Override
                public void saveDate(ArrayList<QuestionBean> list) {
                    ArrayList<String> answerList = new ArrayList<>();
                    for (QuestionBean questionBean: list) {
                        answerList.add(questionBean.getAnswerContent());
                    }
                    studentQuestionBean.setAnswerContent(answerList);
                }
            });
        } else {
            answerTitle.setVisibility(View.VISIBLE);
            answer.setVisibility(View.VISIBLE);
            String aw = " ";
            for (String s: studentQuestionBean.getCorrectAnswer()) {
                aw += s + " ";
            }
            answer.setText(aw);
        }
        return view;
    }
}
