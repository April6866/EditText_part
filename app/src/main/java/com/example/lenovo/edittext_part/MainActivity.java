package com.example.lenovo.edittext_part;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editTextb;
    private Drawable drawable;
    private Drawable drawableb;
    private TextWatcher textWatcher;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private boolean mIsChecked=false;
     private Button btn_sign_in;    //登录按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_sign_in=findViewById(R.id.btn_sign_in);
        setEdit();
        setView();
        signin();
    }
   //这个函数用于对点击登录按钮事件做出响应，完成登录功能
    private void signin() {
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /**核对账号密码，如果都没有问题的话就实现跳转,跳转到一个新的界面，本质是跳转到一个
              * 新的Activity.采用intent对两个Activity进行通信连接*/
            /**★★★注意这个判空条件*/
             if(TextUtils.isEmpty(editText.getText())||TextUtils.isEmpty(editTextb.getText())){
                 Log.d("2222","点击");
                 Toast.makeText(MainActivity.this,"用户名与密码不能为空",Toast.LENGTH_SHORT);
                 new AlertDialog.Builder(MainActivity.this).
                         setTitle("☆ ☆ ☆").setMessage("账户名和密码不能为空").
                         setNegativeButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                             }
                         });

             }
             /**在此处以后应当再加入SQLite操作，将已注册的信息进行核实，未注册的不允许登录
               Toast.makeText(MainActivity.this,"lalala",Toast.LENGTH_SHORT);
              我也很绝望啊，Mate10的手机，打开权限看了允许通知，但是无法弹出Toast。。。我想了想，
              弹出对话框得了,结果对话框也没给我弹出来，我这里只能看到log日志打印出来*/
               else{
                   //我就先直接写登录跳转了
                 Intent mintent=new Intent(MainActivity.this,Activity_B.class);
                 //启动
                 //Log.d("3333","桥梁");
                 startActivity(mintent);

             }
            }
        });
    }

    //记住密码了之后，还需要能够将其回显出来
    private void setView() {
        if (sharedPreferences == null)
            sharedPreferences = getApplication().getSharedPreferences("config", MODE_PRIVATE);
        editText.setText(sharedPreferences.getString("账号",""));
        editTextb.setText(sharedPreferences.getString("密码",""));
        mIsChecked=sharedPreferences.getBoolean("状态",false);
        checkBox.setChecked(mIsChecked);
    }

    //这个函数用于调整为edittext设置的图标的大小还有记住密码的功能
    private void setEdit() {
        editText=findViewById(R.id.edittext1);
        editTextb=findViewById(R.id.edittext2);
        drawableb=getResources().getDrawable(R.drawable.icon_password);
        drawable=getResources().getDrawable(R.drawable.icon_id);
        drawable.setBounds(0,0,60,60);
        drawableb.setBounds(0,0,60,60);
        editTextb.setCompoundDrawables(drawableb,null,null,null);
        editText.setCompoundDrawables(drawable,null,null,null);
        //无论是要实现右下角字数计数器，还是登录功能实现，都需要对edittext进行输入内容的监听，不过重复的代码，
        //大家最好封装一下吧，别这样反反复复的写
        editText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //在文本改变之后记录用户账号
                if (mIsChecked){
                    if (sharedPreferences == null)
                        sharedPreferences = getApplication().getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("账号", editText.getText().toString());
                }
            }
        });
        editTextb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //在文本改变之后记录用户密码
                if (mIsChecked){
                    if (sharedPreferences == null)
                        sharedPreferences = getApplication().getSharedPreferences("config", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("密码", editTextb.getText().toString());
                }
            }
        });
        checkBox=findViewById(R.id.checkbox);
        //为记住密码的复选框设置监听,CompoundButton是一个具有选中和未选中状态的按钮，再被按下时改变状态
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果是选中状态
                mIsChecked=isChecked;
                if(isChecked){
                    /**这里用的sharedpreferance类，是一个轻量级的存储类，适合用于保存软件配置参数，
                     * 使用sharedpreferance存储数据，是用xml存放数据，如果希望存放的其他数据能够被
                     * 其他xml文件读和写，可以将getsharedpreferance中的MODE指定为可以指定
                     * Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE权限。*/
                    if(sharedPreferences==null)
                        sharedPreferences= getApplication().getSharedPreferences("config",MODE_PRIVATE);
                    //获取sharedpreferences的编辑对象
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    //存储数据
                    // Log.d("11111", String.valueOf(editText.getText()));
                    editor.putString("账号",editText.getText().toString());
                    editor.putString("密码",editTextb.getText().toString());
                    editor.putBoolean("状态",isChecked);
                    /**进一步优化时当加入加密操作*/
                    //提交
                    editor.commit();
                }
            }
        });
    }

}


