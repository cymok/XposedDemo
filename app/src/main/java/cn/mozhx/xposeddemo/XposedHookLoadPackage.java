package cn.mozhx.xposeddemo;

import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@SuppressWarnings("WeakerAccess")
public class XposedHookLoadPackage implements IXposedHookLoadPackage {
    public static final String HOOK_PKG = "com.tencent.mm";

    public static final String CLASS_NAME = "com.tencent.mm.app.WeChatSplashActivity";
    public static final String METHOD_NAME = "onCreate";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("handleLoadPackage:" + loadPackageParam.packageName);
        if (!TextUtils.equals(HOOK_PKG, loadPackageParam.packageName)) {
            return;
        }

        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx 开始hook");
        XposedHelpers.findAndHookMethod(
                CLASS_NAME,
                loadPackageParam.classLoader,
                METHOD_NAME,
                Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx beforeHookedMethod");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx afterHookedMethod");

                        Class<?> aClass = loadPackageParam.classLoader.loadClass(CLASS_NAME);
                        Method setContentView = aClass.getMethod("setContentView", int.class);
                        setContentView.invoke(aClass, R.layout.qy);

                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx done");
                    }
                });

//        XposedBridge.log("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx 开始hook");
//        XposedHelpers.findAndHookMethod(
//                CLASS_NAME,
//                loadPackageParam.classLoader,
//                METHOD_NAME,
//                Bundle.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx beforeHookedMethod");
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx afterHookedMethod");
//
//                        Activity activity = (Activity) param.thisObject;
//                        activity.setContentView(R.layout.qy);
//
//                        XposedBridge.log("xxxxxxxxxxxxxxxxxxxx done");
//                    }
//                });
    }
}
