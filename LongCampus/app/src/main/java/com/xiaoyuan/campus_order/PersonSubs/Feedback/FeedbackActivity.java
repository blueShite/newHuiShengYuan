package com.xiaoyuan.campus_order.PersonSubs.Feedback;

import android.os.Bundle;
import android.widget.EditText;

import com.xiaoyuan.campus_order.Base.BaseActivity;
import com.xiaoyuan.campus_order.Manage.LoginManage;
import com.xiaoyuan.campus_order.PersonSubs.Feedback.Interface.FeedbackInterface;
import com.xiaoyuan.campus_order.PersonSubs.Feedback.Presenter.FeedbackPresenter;
import com.xiaoyuan.campus_order.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class FeedbackActivity extends BaseActivity implements FeedbackInterface {

    @BindView(R.id.edit_feedback_feed)
    EditText mEditFeedbackFeed;
    @BindView(R.id.edit_feedback_phone)
    EditText mEditFeedbackPhone;

    private FeedbackPresenter mPresenter = new FeedbackPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        mPresenter.setContext(FeedbackActivity.this);
    }

    @OnClick(R.id.button_feedback_submit)
    public void onViewClicked() {
        if(mEditFeedbackFeed.getText().toString().length()<1){
            Toasty.error(FeedbackActivity.this,"请输入您要提出的意见!").show();
            return;
        }

        if(mEditFeedbackPhone.getText().toString().length()<1){
            Toasty.error(FeedbackActivity.this,"请输入您的手机号码!").show();
            return;
        }
        mPresenter.requestSubmit(mEditFeedbackFeed.getText().toString(),mEditFeedbackPhone.getText().toString(), LoginManage.getInstance().getLoginBean().getId());
    }

    @Override
    public void requestSubmitSucess() {
        finish();
    }
}
