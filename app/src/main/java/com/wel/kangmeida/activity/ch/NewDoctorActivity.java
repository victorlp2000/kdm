package com.wel.kangmeida.activity.ch;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.wel.kangmeida.BaseApp;
import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.bean.DbDoctor;
import com.wel.kangmeida.utils.AppConstat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewDoctorActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlFanhui;
    private EditText etName;
    private Spinner spExpert;
    private RelativeLayout rlSave;
    private List<DbDoctor> listDoctor;
    private EditText etHospital;
    private EditText etTel;
    private EditText etAddress;
    private EditText etWeixin;
    private ImageView ivIcon;
    private String[] items = new String[]{"选择本地图片", "拍照"};

    /*头像名称*/
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdoctor);
        initView();
    }

    private void dbData() {
        listDoctor = Hawk.get("doctorList");
        if (listDoctor == null) {
            listDoctor = new ArrayList<>();
        }
        //获取医生姓名、专科
        String doctorname = etName.getText().toString();
        String expert = spExpert.getSelectedItem().toString();
        if (doctorname != null && !doctorname.equals("")) {
            DbDoctor dbDoctor = new DbDoctor();
            dbDoctor.setDoctorname(doctorname);
            dbDoctor.setExpert(expert);
            listDoctor.add(dbDoctor);
            Hawk.put("doctorList", listDoctor);
            finish();
            BaseApp.showToast("新建成功");
        } else {
            BaseApp.showToast("请输入姓名");
        }
    }

    private void initView() {
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(this);
        etName = (EditText) this.findViewById(R.id.et_name);
        rlSave = (RelativeLayout) this.findViewById(R.id.rl_save);
        rlSave.setOnClickListener(this);
        spExpert = (Spinner) this.findViewById(R.id.sp_zhuanke);
        etAddress = (EditText) this.findViewById(R.id.et_dizhi);
        etHospital = (EditText) this.findViewById(R.id.et_yiyuan);
        etTel = (EditText) this.findViewById(R.id.et_tel);
        etWeixin = (EditText) this.findViewById(R.id.et_weixin);
        ivIcon = (ImageView) this.findViewById(R.id.iv_doctoricon);

        //让所有Edtext点击之后提示文字消失
        initEt();
    }

    private void initEt() {
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etName.setHint(etName.getTag().toString());
                } else {
                    String hint = etName.getHint().toString();
                    etName.setTag(hint);
                    etName.setHint("");
                }
            }
        });
        etHospital.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etHospital.setHint(etHospital.getTag().toString());
                } else {
                    String hint = etHospital.getHint().toString();
                    etHospital.setTag(hint);
                    etHospital.setHint("");
                }
            }
        });
        etTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etTel.setHint(etTel.getTag().toString());
                } else {
                    String hint = etTel.getHint().toString();
                    etTel.setTag(hint);
                    etTel.setHint("");
                }
            }
        });
        etAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etAddress.setHint(etAddress.getTag().toString());
                } else {
                    String hint = etAddress.getHint().toString();
                    etAddress.setTag(hint);
                    etAddress.setHint("");
                }
            }
        });
        etWeixin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etWeixin.setHint(etWeixin.getTag().toString());
                } else {
                    String hint = etWeixin.getHint().toString();
                    etWeixin.setTag(hint);
                    etWeixin.setHint("");
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_fanhui:
                finish();
                break;
            case R.id.rl_save:
                //保存
                dbData();
                setResult(AppConstat.DOCTOR_NEW_RESULT_CODE);
                break;
            case R.id.iv_doctoricon:
                showDialog();
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initValues();
    }

    private void initValues() {

    }

    private void showDialog() {

        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                                break;
                            case 1:
                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (hasSdcard()) {
                                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                                }
                                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (hasSdcard()) {
                        File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                    }

                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        setImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            ivIcon.setImageDrawable(drawable);
        }
    }

    //判断Sd卡是否存在
    private boolean hasSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }
}
