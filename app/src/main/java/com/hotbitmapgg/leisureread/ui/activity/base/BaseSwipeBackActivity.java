package com.hotbitmapgg.leisureread.ui.activity.base;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import android.os.Bundle;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG BaseSwipeBackActivity基类
 */
public abstract class BaseSwipeBackActivity extends SwipeBackActivity {

  public SwipeBackLayout mSwipeBackLayout;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //设置布局内容
    setContentView(getLayoutId());
    //初始化黄油刀控件绑定框架
    ButterKnife.bind(this);
    //初始化侧滑返回layout
    mSwipeBackLayout = getSwipeBackLayout();
    mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    //初始化控件
    initViews(savedInstanceState);
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }


  public abstract int getLayoutId();

  public abstract void initViews(Bundle savedInstanceState);
}
