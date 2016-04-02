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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.wel.kangmeida.BaseApp;
import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.activity.HobbyActivity;
import com.wel.kangmeida.bean.DbChengyuan;
import com.wel.kangmeida.utils.AppConstat;
import com.wel.kangmeida.utils.DoubleDatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewMemberActivity extends BaseActivity {
    private RelativeLayout rlBirthday;
    private EditText etBirthday;
    private RelativeLayout rlFanhui;
    private EditText etPharmacy;
    private RelativeLayout rlSave;
    private EditText etName;
    private Spinner spSex;
    private List<DbChengyuan> cyList;
    private EditText etHigh;
    private EditText etWeigh;
    private EditText etEmall;
    private EditText etTel;
    private ImageView ivIcon;
    private TextView tvHobby;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmember);
        initView();
    }

    private void initView() {
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        etBirthday = (EditText) this.findViewById(R.id.et_birthday);
        etPharmacy = (EditText) this.findViewById(R.id.et_yongyao);
        rlSave = (RelativeLayout) this.findViewById(R.id.rl_save);
        etName = (EditText) this.findViewById(R.id.et_name);
        spSex = (Spinner) this.findViewById(R.id.sp_sex);
        etEmall = (EditText) this.findViewById(R.id.et_emall);
        etHigh = (EditText) this.findViewById(R.id.et_high);
        etWeigh = (EditText) this.findViewById(R.id.et_tizhong);
        etTel = (EditText) this.findViewById(R.id.et_tel);
        ivIcon = (ImageView) this.findViewById(R.id.iv_icon);
        tvHobby = (TextView) this.findViewById(R.id.tv_hobby);

        //让所有Edtext点击之后提示文字消失
        initEt();
        //监听事件
        initEvent();
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
        etEmall.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etEmall.setHint(etEmall.getTag().toString());
                } else {
                    String hint = etEmall.getHint().toString();
                    etEmall.setTag(hint);
                    etEmall.setHint("");
                }
            }
        });
        etHigh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etHigh.setHint(etHigh.getTag().toString());
                } else {
                    String hint = etHigh.getHint().toString();
                    etHigh.setTag(hint);
                    etHigh.setHint("");
                }
            }
        });
        etWeigh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {// 失去焦点
                    etWeigh.setHint(etWeigh.getTag().toString());
                } else {
                    String hint = etWeigh.getHint().toString();
                    etWeigh.setTag(hint);
                    etWeigh.setHint("");
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


    }

    private void initEvent() {
        rlFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回
                finish();
            }
        });
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //生日
                getTime();
            }
        });
        etPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用药
                getYongyao();
            }
        });
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存
                dbCy();
                setResult(AppConstat.CY_NEW_RESULT_CODE);
            }
        });
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择头像
                showDialog();
            }
        });
        tvHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMemberActivity.this, HobbyActivity.class);
                startActivityForResult(intent, AppConstat.HOBBY);
            }
        });
    }

    //存数据
    private void dbCy() {
        cyList = Hawk.get("cyList");
        if (cyList == null) {
            cyList = new ArrayList<>();
        }
        //获取输入的成员姓名、性别、生日
        String cyName = etName.getText().toString();
        String cySex = spSex.getSelectedItem().toString();
        String cyAge = etBirthday.getText().toString();
        if (cyName != null && !cyName.equals("")) {
            if (cyAge != null && !cyAge.equals("")) {
                DbChengyuan dbChengyuan = new DbChengyuan();
                dbChengyuan.setCyName(cyName);
                dbChengyuan.setCySex(cySex);
                dbChengyuan.setCyAge(cyAge);
                cyList.add(dbChengyuan);
                Hawk.put("cyList", cyList);
                finish();
                BaseApp.showToast("新建成功");
            } else {
                BaseApp.showToast("请输入生日");
            }
        } else {
            BaseApp.showToast("请输入姓名");
        }
    }

    //获得生日时间
    private void getTime() {
        // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
        Calendar c = Calendar.getInstance();
        new DoubleDatePickerDialog(NewMemberActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth) {
                String textString = String.format("%d年%d月%d日", startYear,
                        startMonthOfYear + 1, startDayOfMonth);
                etBirthday.setText(textString);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
    }

    //获得用药时间
    private void getYongyao() {
        // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
        Calendar c = Calendar.getInstance();
        new DoubleDatePickerDialog(NewMemberActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth) {
                String textString = String.format("%d年%d月%d日", startYear,
                        startMonthOfYear + 1, startDayOfMonth);
                etPharmacy.setText(textString);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
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
                .setItems(AppConstat.items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery, AppConstat.IMAGE_REQUEST_CODE);
                                break;
                            case 1:
                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (hasSdcard()) {
                                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), AppConstat.IMAGE_FILE_NAME)));
                                }
                                startActivityForResult(intentFromCapture, AppConstat.CAMERA_REQUEST_CODE);
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
                case AppConstat.IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case AppConstat.CAMERA_REQUEST_CODE:
                    if (hasSdcard()) {
                        File tempFile = new File(Environment.getExternalStorageDirectory(), AppConstat.IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case AppConstat.RESULT_REQUEST_CODE:
                    if (data != null) {
                        setImageToView(data);
                    }
            }
        }
        if (resultCode == AppConstat.HOBBY) {
            //返还选择的爱好
            Bundle bundle = data.getExtras();
            int id = bundle.getInt("position");
            tvHobby.setText(AppConstat.iconName[id]);
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




