package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.hotbitmapgg.leisureread.LeisureReadApp;
import com.hotbitmapgg.leisureread.mvp.model.component.DaggerSectionsComponent;
import com.hotbitmapgg.leisureread.mvp.presenter.SectionsPresenter;
import com.hotbitmapgg.leisureread.mvp.presenter.sections.SectionsPresenterMoudle;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.ui.fragment.DailyFragment;
import com.hotbitmapgg.leisureread.ui.fragment.SectionsFragment;
import com.hotbitmapgg.leisureread.ui.fragment.ThemesDailyFragment;
import com.hotbitmapgg.leisureread.ui.fragment.UserInfoFragment;
import com.hotbitmapgg.rxzhihu.R;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 主界面
 */
public class MainActivity extends BaseAppCompatActivity {

  @Inject
  SectionsPresenter mSectionsPresenter;

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.bottom_navigation)
  AHBottomNavigation mAhBottomNavigation;

  private List<Fragment> fragments = new ArrayList<>();

  private int currentTabIndex;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    SectionsFragment sectionsFragment = SectionsFragment.newInstance();

    DaggerSectionsComponent.builder()
        .sectionsPresenterMoudle(new SectionsPresenterMoudle(sectionsFragment))
        .appComponent(LeisureReadApp.getAppContext().getAppComponent())
        .build()
        .inject(this);

    fragments.add(DailyFragment.newInstance());
    fragments.add(ThemesDailyFragment.newInstance());
    fragments.add(sectionsFragment);
    fragments.add(UserInfoFragment.newInstance());

    showFragment(fragments.get(0));
    initBottomNav();
  }


  @Override
  public int getLayoutId() {

    return R.layout.activity_main;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

  }


  @Override
  protected void attachBaseContext(Context newBase) {

    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }


  private void initBottomNav() {

    AHBottomNavigationItem item1 = new AHBottomNavigationItem("精选",
        R.drawable.ic_tab_strip_icon_feed_selected);
    AHBottomNavigationItem item2 = new AHBottomNavigationItem("发现",
        R.drawable.ic_tab_strip_icon_category_selected);
    AHBottomNavigationItem item3 = new AHBottomNavigationItem("专栏",
        R.drawable.ic_tab_strip_icon_follow_selected);
    AHBottomNavigationItem item4 = new AHBottomNavigationItem("我的",
        R.drawable.ic_tab_strip_icon_profile_selected);

    mAhBottomNavigation.addItem(item1);
    mAhBottomNavigation.addItem(item2);
    mAhBottomNavigation.addItem(item3);
    mAhBottomNavigation.addItem(item4);

    mAhBottomNavigation.setColored(false);
    mAhBottomNavigation.setForceTint(false);
    mAhBottomNavigation.setBehaviorTranslationEnabled(true);
    mAhBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    mAhBottomNavigation.setAccentColor(getResources().getColor(R.color.black_90));
    mAhBottomNavigation.setInactiveColor(getResources().getColor(R.color.nav_text_color_mormal));
    mAhBottomNavigation.setCurrentItem(0);
    mAhBottomNavigation.setDefaultBackgroundColor(
        getResources().getColor(R.color.bottom_tab_bar_color));

    mAhBottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {

      if (currentTabIndex != position) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments.get(currentTabIndex));
        if (!fragments.get(position).isAdded()) {
          trx.add(R.id.content, fragments.get(position));
        }
        trx.show(fragments.get(position)).commit();
      }
      currentTabIndex = position;

      return true;
    });
  }


  @Override
  public void initToolBar() {

    mToolbar.setTitle("");
    setSupportActionBar(mToolbar);
  }


  private void showFragment(Fragment fragment) {

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content, fragment)
        .commit();
  }
}
