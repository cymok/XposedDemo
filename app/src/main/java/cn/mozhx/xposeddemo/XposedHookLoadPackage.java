package cn.mozhx.xposeddemo;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedHookLoadPackage implements IXposedHookLoadPackage {
    public static final String HOOK_PKG = "com.tencent.mm";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("handleLoadPackage:" + loadPackageParam.packageName);
        if (!TextUtils.equals(HOOK_PKG, loadPackageParam.packageName)) {
            return;
        }

        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onCreate 开始hook");
        XposedHelpers.findAndHookMethod(
                HOOK_PKG + ".app.WeChatSplashActivity",
                loadPackageParam.classLoader,
                "onCreate",
                android.os.Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onCreate beforeHookedMethod");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onCreate afterHookedMethod");

                        Activity activity = (Activity) param.thisObject;
                        activity.setContentView(R.layout.qy);

                        Toast.makeText(activity, "onCreate 执行了", Toast.LENGTH_SHORT).show();

                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onCreate done");
                    }
                });

        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onStart 开始hook");
        XposedHelpers.findAndHookMethod(
                HOOK_PKG + ".app.WeChatSplashActivity",
                loadPackageParam.classLoader,
                "onStart",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onStart beforeHookedMethod");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onStart afterHookedMethod");

                        Activity activity = (Activity) param.thisObject;
                        activity.setContentView(R.layout.qy);

                        Toast.makeText(activity, "onStart 执行了", Toast.LENGTH_SHORT).show();

                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx onStart done");
                    }
                });
    }
}
