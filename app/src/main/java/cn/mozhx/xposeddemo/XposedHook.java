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
        String HOOK_PKG = "com.tencent.mm";
        String CLASS_NAME = "com.tencent.mm.ui.SplashImageView";

        XposedBridge.log("handleLoadPackage:" + loadPackageParam.packageName);
        if (!TextUtils.equals(HOOK_PKG, loadPackageParam.packageName)) {
            return;
        }

        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx hook start");
        XC_MethodHook callback = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("xxxxxxxxxxxxxxxxxxxx hook prepare");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                ImageView imageView = (ImageView) param.thisObject;
                imageView.setImageResource(R.drawable.qy);
                XposedBridge.log("xxxxxxxxxxxxxxxxxxxx finish");
            }
        };
        //Context paramContext, AttributeSet paramAttributeSet, int paramInt
        XposedHelpers.findAndHookConstructor(CLASS_NAME, loadPackageParam.classLoader,
                Context.class,
                callback);
        XposedHelpers.findAndHookConstructor(CLASS_NAME, loadPackageParam.classLoader,
                Context.class, AttributeSet.class,
                callback);
        XposedHelpers.findAndHookConstructor(CLASS_NAME, loadPackageParam.classLoader,
                Context.class, AttributeSet.class, int.class,
                callback);

    }
}
