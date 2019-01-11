
#### [Xposed使用](https://github.com/rovo89/XposedBridge/wiki/Using-the-Xposed-Framework-API)

---

##### 分析技巧

利用dex2jar工具将dex反编译成jar

然后把jar复制到android studio中

就很方便利用ide的功能分析了

#### 利用Android_decode_v2.2.S等逆向工具

---

#### Xposed公共接口

```
    package：de.robv.android.xposed.IXposedHookZygoteInit       
    interface ：IXposedHookZygoteInit 　　//在Android系统启动时被调用，作用于初始的zygote进程，可用于实现应用于所有应用的hook

    package：de.robv.android.xposed.IXposedHookLoadPackage
    interface：IXposedHookLoadPackage　　//当指定应用被加载时被调用，一般用于hook特定应用的方法

    package：de.robv.android.xposed.IXposedHookInitPackageResources
    interface：IXposedHookInitPackageResources //指定应用的资源进行初始化时被调用，一般用于资源的替换
```

