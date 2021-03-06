package org.wall.mo.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wall.mo.base.interfaces.IAttachActivity;
import org.wall.mo.base.interfaces.IFragment;
import org.wall.mo.utils.BuildConfig;
import org.wall.mo.utils.ILifecycleObserver;
import org.wall.mo.utils.StringUtils;
import org.wall.mo.utils.log.WLog;
import org.wall.mo.utils.network.NetStateChangeObserver;
import org.wall.mo.utils.network.NetStateChangeReceiver;
import org.wall.mo.utils.network.NetworkType;

/**
 * Copyright (C), 2018-2019
 * Author: ziqimo
 * Date: 2019/5/29 下午4:50
 * Description: ${DESCRIPTION}
 * History: 基础类 列出相关的声明周期方法
 * <p>
 * 后面尽量考虑AbsDataBindingV4Fragment来实现逻辑
 * 后面尽量考虑AbsDataBindingV4Fragment来实现逻辑
 * 后面尽量考虑AbsDataBindingV4Fragment来实现逻辑
 *
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
@Deprecated
public abstract class AbsV4Fragment extends Fragment implements IFragment,
        NetStateChangeObserver,
        ILifecycleObserver {

    public final static String TAG = AbsV4Fragment.class.getSimpleName();

    protected Context mContext;

    protected IAttachActivity mAttachActivity;

    protected View mRootView;

    protected Handler mHandler = null;

    private NetStateChangeReceiver mNetStateChangeReceiver;

    public Handler getHandler() {
        return mHandler;
    }

    /**
     * 例子
     *
     * @param args
     * @return
     */
    public static Fragment newInstance(AbsV4Fragment absV4Fragment, Bundle args) {
        Fragment fragment = absV4Fragment;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onAttach mContext is " + (context != null ? context.getClass().getSimpleName() : "--"));
        }
        this.mContext = context;
        if (context instanceof IAttachActivity) {
            mAttachActivity = (IAttachActivity) context;
        } else {
            onAbsV4Attach(context);
        }
    }

    protected abstract void onAbsV4Attach(Context context);

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onAttachFragment");
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".setUserVisibleHint isVisibleToUser is " + isVisibleToUser);
        }
//        LogUtils.i(TAG,getName() + "  isResumed() " + isResumed());
//        LogUtils.i(TAG,getName() + "  isAdded() " + isAdded());
//        LogUtils.i(TAG, getName() + "  setUserVisibleHint getParentFragment != null  " + (getParentFragment() != null));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onHiddenChanged hidden is " + hidden);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onCreate");
        }
        //创建一个handler
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    handleSubMessage(msg);
                }
            };
        }

        /**
         * https://blog.csdn.net/airk000/article/details/38557605
         * 保持Fragment
         */
        setRetainInstance(setCurRetainInstance());
    }


    protected boolean setCurRetainInstance() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onCreateView savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        }
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            if (mRootView == null) {
                mRootView = inflater.inflate(layoutId, container, false);
            }
            /**
             *
             ————————————————
             版权声明：本文为CSDN博主「ljtyzhr」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
             原文链接：https://blog.csdn.net/ljtyzhr/article/details/40736525
             */
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            //在这里findViewById
            initView(mRootView, savedInstanceState);
            return mRootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    /**
     * 实现一个findViewById的方法
     *
     * @param idRes
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int idRes) {
        if (idRes == View.NO_ID) {
            return null;
        }
        return mRootView.findViewById(idRes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onViewCreated savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onActivityCreated savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        }
        /**
         * 废弃这2个方法
         */
        initData();
        initClick();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onStart");
        }
        if (mNetStateChangeReceiver == null) {
            mNetStateChangeReceiver = new NetStateChangeReceiver();
            mNetStateChangeReceiver.setNetStateChangeObserver(this);
            NetStateChangeReceiver.registerReceiver(getCurActivity(), mNetStateChangeReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onResume");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onPause");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onPause");
        }
        if (mNetStateChangeReceiver != null) {
            mNetStateChangeReceiver.setNetStateChangeObserver(null);
            NetStateChangeReceiver.unRegisterReceiver(getCurActivity(), mNetStateChangeReceiver);
            mNetStateChangeReceiver = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onDestroyView");
        }
    }

    @Override
    public void onDestroy() {
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onDestroy");
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        mRootView = null;
        mAttachActivity = null;
        mContext = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onDetach");
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onSaveInstanceState outState is " + StringUtils.isNULL(outState));
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onViewStateRestored savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onConfigurationChanged");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onRequestPermissionsResult");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onActivityResult");
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + ".onLowMemory");
        }
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + "》》》startActivity options -- 开始 --");
        }
        if (startActivitySelfCheck(intent)) {
            if (BuildConfig.DEBUG) {

                WLog.i(TAG, getName() + "》》》startActivity options -- 满足 --");
            }
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivity(intent, options);
        }
    }

    //控制startactivity 参考这个哥们https://www.jianshu.com/p/579f1f118161
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + "》》》startActivityForResult -- 开始 --");
        }
        if (startActivitySelfCheck(intent)) {
            if (BuildConfig.DEBUG) {
                WLog.i(TAG, getName() + "》》》startActivityForResult -- 满足 --");
            }
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private String mActivityJumpTag;

    private long mActivityJumpTime;

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    protected boolean startActivitySelfCheck(Intent intent) {
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + "》》》startActivitySelfCheck -- 开始 --");
        }
        // 默认检查通过
        boolean result = true;
        if (BuildConfig.DEBUG) {
            WLog.i(TAG, getName() + "》》》startActivitySelfCheck -- intent --" + (intent == null));
        }
        if (intent == null) {
            return result;
        }
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
            if (BuildConfig.DEBUG) {
                WLog.i(TAG, getName() + "》》》startActivitySelfCheck -- 显式跳转 --" + tag);
            }
        } else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
            if (BuildConfig.DEBUG) {
                WLog.i(TAG, getName() + "》》》startActivitySelfCheck -- 隐式跳转 --" + tag);
            }
        } else {
            return result;
        }
        if (tag.equals(mActivityJumpTag) && (mActivityJumpTime >= SystemClock.uptimeMillis() - 500)) {
            // 检查不通过
            result = false;
            if (BuildConfig.DEBUG) {
                WLog.i(TAG, getName() + "》》》startActivitySelfCheck -- result --" + result);
            }
        }
        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mActivityJumpTime = SystemClock.uptimeMillis();
        return result;
    }

    /**
     * 获取onAttach的context
     *
     * @return
     */
    public Context getCurActivity() {
        return mContext;
    }

    public String getName() {
        return getClass().getSimpleName();
    }


    /**
     * 默认返回-1
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化view
     *
     * @param rootView
     * @param savedInstanceState
     */
    public abstract void initView(View rootView, Bundle savedInstanceState);

    /**
     * 废弃这2个方法
     */
    @Deprecated
    public void initData() {

    }

    /**
     * 废弃这2个方法
     */
    @Deprecated
    public void initClick() {

    }


    @Override
    public void onNetConnected(NetworkType networkType) {
        WLog.i(TAG, getName() + ".onNetConnected.networkType:" + networkType);
    }

    @Override
    public void onNetDisconnected() {
        WLog.i(TAG, getName() + ".onNetDisconnected");
    }

    public abstract void handleSubMessage(Message msg);

    @Override
    public void onLifeClear() {
        ILifecycleObserver.InnerClass.clear(this);
    }
}
