package com.cvte.note.acvivity;

import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.cvte.note.R;
import com.cvte.note.model.INoteModel;
import com.cvte.note.utils.DrawUtils;
import com.cvte.note.view.RichImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.X;
import static android.view.View.Y;

public class NoteMainActivity extends BaseActivity implements View.OnClickListener,INoteModel{
    RichImageView richImageView;
    private ImageButton penButton;
    private ImageButton xiangpiButton;
    private ImageButton lineButton;
    private ImageButton yuanButton;
    private ImageButton juxingButton;
    private ImageButton backButton;
    private ImageButton RealXiangPi;
    private PopupWindow popupWindow;
    private PopupWindow RightPopWindow;
    private Button openPopWindow;
    private ImageButton yanseButton;
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private String colorText[] = {"白色","藍色","綠色","黑色","灰色","紅色","黃色"};
    private int color[]={Color.WHITE,Color.BLUE,Color.GREEN,Color.BLACK,Color.GRAY,Color.RED,Color.YELLOW};
    private TextView showText;
    private SeekBar seekBar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_note_main);
        richImageView = (RichImageView) findViewById(R.id.MainImageView);
        richImageView.setXiangpi(this);
        openPopWindow = (Button) findViewById(R.id.open_pop_window);
        RealXiangPi = (ImageButton) findViewById(R.id.real_xiang_pi);
    }

    @Override
    protected void initData() {
    }


    @Override
    protected void initEvent() {
        openPopWindow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bi:
             //   showMessage(false,"bi");
                DrawUtils.getInstance().setSelectStatue(INoteModel.BI);
             //   richImageView.setColor(Color.RED);
                break;
            case R.id.xiangpi:
              //  showMessage(false,"xiangpi");
                DrawUtils.getInstance().setSelectStatue(INoteModel.XIANGPI);
                richImageView.setColor(Color.WHITE);
                RealXiangPi.setVisibility(View.VISIBLE);
                break;
            case R.id.open_pop_window:
                // TODO Auto-generated method stub
            //    showMessage(false,"asdasda");
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);
                break;
            case  R.id.line:
                DrawUtils.getInstance().setSelectStatue(INoteModel.XIAN);
                break;
            case R.id.yuan:
                //showMessage(false,"yuan");
                DrawUtils.getInstance().setSelectStatue(INoteModel.YUANXING);
                break;
            case R.id.juxing:
                showMessage(false,"yuan");
                DrawUtils.getInstance().setSelectStatue(INoteModel.JUXING);
                break;
            case R.id.yanse:
                getRightPopupWindow();
                RightPopWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case R.id.back:
              richImageView.clear();
                break;
        }
    }
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_window, null,
                false);
        penButton = (ImageButton) popupWindow_view.findViewById(R.id.bi);
        xiangpiButton = (ImageButton) popupWindow_view.findViewById(R.id.xiangpi);
        lineButton = (ImageButton) popupWindow_view.findViewById(R.id.line);
        yuanButton = (ImageButton) popupWindow_view.findViewById(R.id.yuan);
        juxingButton = (ImageButton) popupWindow_view.findViewById(R.id.juxing);
        yanseButton = (ImageButton)popupWindow_view.findViewById(R.id.yanse);
        backButton = (ImageButton) popupWindow_view.findViewById(R.id.back);
        backButton.setOnClickListener(this);
        juxingButton.setOnClickListener(this);
        yuanButton.setOnClickListener(this);
        lineButton.setOnClickListener(this);
        yanseButton.setOnClickListener(this);
        penButton.setOnClickListener(this);
        xiangpiButton.setOnClickListener(this);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 200, LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }
    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }
    protected void initRightPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_window_right, null,
                false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        RightPopWindow = new PopupWindow(popupWindow_view, 500, LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
       // RightPopWindow.setAnimationStyle(R.style.AnimationFade);
        showText = (TextView) popupWindow_view.findViewById(R.id.show_text);
        seekBar = (SeekBar) popupWindow_view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
              //  showText.setText("拖动停止");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
               // description.setText("开始拖动");
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
               // description.setText("当前进度："+progress+"%");
                showText.setText("當前大小為"+progress/2);
                showText.setTextSize(progress/2);
                richImageView.setStrokeWidth(progress/2);
            }
        });
        initGridView(popupWindow_view);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (RightPopWindow != null && RightPopWindow.isShowing()) {
                    RightPopWindow.dismiss();
                    RightPopWindow = null;
                }
                return false;
            }
        });
    }

    private void initGridView(View popupWindow_view) {
        gview = (GridView) popupWindow_view.findViewById(R.id.gview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"text"};
        int [] to = {R.id.item_color_text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item, from, to);
        //配置适配器
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showText.setTextColor(color[position]);
                view.setBackgroundColor(color[position]);
              //  richImageView.setColor(color[position]);
                DrawUtils.getInstance().setPaintColor(color[position]);
            }
        });
        gview.setAdapter(sim_adapter);
    }

    /***
     * 获取PopupWindow实例
     */
    private void getRightPopupWindow() {
        if (null != RightPopWindow) {
            RightPopWindow.dismiss();
            return;
        } else {
            initRightPopuptWindow();
        }
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<colorText.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", colorText[i]);
            data_list.add(map);
        }

        return data_list;
    }

    @Override
    public void SetXiangPiLocation(float x, float y,float startX,float startY) {
        int movingX = (int) x;
        int movingY = (int) y;
        // 相对于上一个点，手指在X和Y方向分别移动的距离
        int dx = movingX - (int)startX;
        int dy = movingY - (int)startY;
        // 获取TextView上一次上 下 左 右各边与父控件的距离
        int left = RealXiangPi.getLeft();
        int right = RealXiangPi.getRight();
        int top = RealXiangPi.getTop();
        int bottom = RealXiangPi.getBottom();
        // 设置本次TextView的上 下 左 右各边与父控件的距离
        RealXiangPi.layout( dx,  dy, dx, dy);
        // 本次移动的结尾作为下一次移动的开始
    }
}