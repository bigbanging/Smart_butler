package com.bigbang.smartbutler.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbang.smartbutler.R;
import com.bigbang.smartbutler.entity.MyUser;
import com.bigbang.smartbutler.ui.LoginActivity;
import com.bigbang.smartbutler.ui.LogisticActivity;
import com.bigbang.smartbutler.ui.PhoneActivity;
import com.bigbang.smartbutler.utils.L;
import com.bigbang.smartbutler.utils.UtilTools;
import com.bigbang.smartbutler.view.SelfDialog;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.tv_edit_info)
    TextView tvEditInfo;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_gender)
    EditText etGender;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.btn_edit_info)
    Button btnEditInfo;
    @BindView(R.id.tv_logistic)
    TextView tvLogistic;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_exit_login)
    Button btnExitLogin;
    Unbinder unbinder;
    private CircleImageView ivHeader;
    private SelfDialog mSelfDialog;

    private Button btnCamera, btnGallery, btnCancel;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ivHeader = view.findViewById(R.id.iv_header);
        unbinder = ButterKnife.bind(this, view);
        tvEditInfo.setOnClickListener(this);
        btnEditInfo.setOnClickListener(this);
        btnExitLogin.setOnClickListener(this);
        tvLogistic.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        ivHeader.setOnClickListener(this);
        // onCreate的时候将头像信息取出
//        UtilTools.getHeaderFromShared(getActivity(), ivHeader);
        //信息不能编辑状态
        setEnabled(false);
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        etUsername.setText(userInfo.getUsername());
        etAge.setText(userInfo.getAge() + "");
        etGender.setText(userInfo.isGender() ? "男" : "女");
        etInfo.setText(userInfo.getInfo());

        //初始化Dialog
        mSelfDialog = new SelfDialog(getActivity(), 0, 0, R.layout.dialog_header,
                R.style.theme_dialog, Gravity.BOTTOM, 0);
        mSelfDialog.setCancelable(false);
        btnCamera = mSelfDialog.findViewById(R.id.btn_camera);
        btnGallery = mSelfDialog.findViewById(R.id.btn_gallery);
        btnCancel = mSelfDialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
    }

    private void setEnabled(boolean enable) {
        etUsername.setEnabled(enable);
        etAge.setEnabled(enable);
        etGender.setEnabled(enable);
        etInfo.setEnabled(enable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //编辑信息
            case R.id.tv_edit_info:
                setEnabled(true);
                btnEditInfo.setVisibility(View.VISIBLE);
                break;
            //完成信息修改
            case R.id.btn_edit_info:
                String username = etUsername.getText().toString();
                String gender = etGender.getText().toString();
                String age = etAge.getText().toString();
                String info = etInfo.getText().toString();
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(gender) & !TextUtils.isEmpty(age)) {
                    if (!TextUtils.isEmpty(info)) {
                        etInfo.setText(info);
                    } else {
                        etInfo.setText("此用户很懒，什么也没留下");
                    }
                    MyUser myUser = new MyUser();
                    myUser.setUsername(username);
                    if (gender.equals("男") || gender.equals("male")) {
                        myUser.setGender(true);
                    } else if (gender.equals("女") || gender.equals("female")) {
                        myUser.setGender(false);
                    }
                    myUser.setAge(Integer.parseInt(age));
                    myUser.setInfo(info);
                    BmobUser currentUser = BmobUser.getCurrentUser();
                    myUser.update(currentUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                                btnEditInfo.setVisibility(View.GONE);
                                setEnabled(false);
                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                                L.i("修改信息失败：" + e.getErrorCode() + ":" + e.getMessage());
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "更改信息内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            // 退出登录
            case R.id.btn_exit_login:
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Objects.requireNonNull(getActivity()).finish();
                break;
            //选取头像
            case R.id.iv_header:
                mSelfDialog.show();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_gallery:
                toGallery();
                break;
            case R.id.btn_cancel:
                mSelfDialog.dismiss();
                break;
            //查询物流信息
            case R.id.tv_logistic:
                startActivity(new Intent(getActivity(),LogisticActivity.class));
                break;
            // 查询归属地
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(),PhoneActivity.class));
                break;
            default:
                break;
        }
    }

    private static final String PHOTO_IMAGE_FILE_NAME = "fileImage.jpg";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    /**
     * 跳转图库
     */
    private void toGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
        mSelfDialog.dismiss();
    }
    /**
     * 跳转相机
     */
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内容卡是否可用
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        mSelfDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相机数据
                case CAMERA_REQUEST_CODE:
                    cropImage(data.getData());
                    break;
                //相册数据
                case GALLERY_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    cropImage(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        // 拿到图片进行头像设置
                        setImgToView(data);
                        //重新设置了新图片 则把原先的头像删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setImgToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            ivHeader.setImageBitmap(bitmap);
        }
    }

    /**
     * Crop image 裁剪图片
     *
     * @param uri
     */
    public void cropImage(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪的图片质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 保存
        UtilTools.setHeaderImg(getActivity(), ivHeader);
    }
}
