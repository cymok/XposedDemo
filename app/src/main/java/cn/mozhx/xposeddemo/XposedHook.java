package cn.mozhx.xposeddemo;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@SuppressWarnings("WeakerAccess")
public class XposedHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String hookPkg = "com.tencent.mm";
        String className = "com.tencent.mm.ui.SplashImageView";

        String packageName = loadPackageParam.packageName;
        ClassLoader classLoader = loadPackageParam.classLoader;

        //检测模块是否激活 在应用自己的页面
        if (TextUtils.equals(BuildConfig.APPLICATION_ID, packageName)) {
            //om.xposed_wechat.xz.MainActivity   需要Hook类名
            //showIsXposedStart   需要Hook类名下面的方法名
            XposedBridge.log("xxxxxxxxxxxxxxxxxxxx isActivating:start");
            XposedHelpers.findAndHookMethod(
                    "cn.mozhx.xposeddemo.MainActivity",
                    classLoader,
                    "isActivating",
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            //强行改变方法的参数
                            param.args[0] = true;
                            XposedBridge.log("xxxxxxxxxxxxxxxxxxxx isActivating:beforeHookedMethod");
                            super.beforeHookedMethod(param);
                        }
                    });
        }

        XposedBridge.log("handleLoadPackage:" + loadPackageParam.packageName);
        if (!TextUtils.equals(hookPkg, loadPackageParam.packageName)) {
            return;
        }

        XC_MethodHook callback = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("xxxxxxxxxxxxxxxxxxxx beforeHookedMethod");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("xxxxxxxxxxxxxxxxxxxx afterHookedMethod");
                ImageView imageView = (ImageView) param.thisObject;
                imageView.setImageResource(R.drawable.qy);
                XposedBridge.log("xxxxxxxxxxxxxxxxxxxx finish");
            }
        };
        //Context paramContext, AttributeSet paramAttributeSet, int paramInt
        XposedHelpers.findAndHookConstructor(className, loadPackageParam.classLoader,
                Context.class,
                callback);
        XposedHelpers.findAndHookConstructor(className, loadPackageParam.classLoader,
                Context.class, AttributeSet.class,
                callback);
        XposedHelpers.findAndHookConstructor(className, loadPackageParam.classLoader,
                Context.class, AttributeSet.class, int.class,
                callback);

    }
}
