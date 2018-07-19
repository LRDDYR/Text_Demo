package com.wlmq.profit.permission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import com.wlmq.profit.base.BaseActivity;
import com.wlmq.profit.utils.DialogUtils;
import com.wlmq.profit.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 权限管理类，需要配合Activity或Fragment的onRequestPermissionsResult方法使用。
 */

public class  PermissionUtil {
    private static PermissionUtil pManager = null;
    public static PermissionUtil getInstance(){
        if (pManager==null){
            pManager = new PermissionUtil();
        }
        return pManager;
    }
    public int requestCode = 2000;
    public HashMap<Integer,MethodParams> methods = new HashMap<>();
    /**
     * 检查权限授予情况，对于未授权的权限进行权限申请操作。
     * @param contextWrap 上下文对象
     * @param permissions 需要检查的权限数组
     * @param listener 异步执行接口，在接到权限申请结果后，执行接口方法。
     * @param objects 参数扩展
     * */
    public void checkPermission(ContextWrap contextWrap, String[] permissions, AsynMethodListener listener, Object... objects){
        //如果是6.0以下，跳过权限申请步骤
        if (Build.VERSION.SDK_INT < 23){
            listener.executeMethod(objects);
            return;
        }
        requestCode++;
        //检测权限状态
        ArrayList<String> pmlist = new ArrayList<>();
        for (String permission:permissions){
            int status = ContextCompat.checkSelfPermission(contextWrap.getActivity(), permission);
            if (status== PackageManager.PERMISSION_DENIED){
                pmlist.add(permission);
            }
            
            //判断是否需要 向用户解释，为什么要申请该权限，该方法只有在用户在上一次已经拒绝过你的这个权限申请才会调用。
            if(ActivityCompat.shouldShowRequestPermissionRationale(contextWrap.getActivity(), permission)){
                //Toast.makeText(contextWrap.getActivity(),"Need "+permission+" permission.", Toast.LENGTH_SHORT).show();
            }
        }
        if (pmlist.size()>0){
            //有未授予权限，进行权限申请操作
            MethodParams params = new MethodParams();
            params.setListener(listener);
            params.setObjects(objects);
            methods.put(requestCode,params);
            if (contextWrap.getFragment()!=null){
                contextWrap.getFragment().requestPermissions(pmlist.toArray(new String[pmlist.size()]), requestCode);
            }else{
                ActivityCompat.requestPermissions(contextWrap.getActivity(), pmlist.toArray(new String[pmlist.size()]), requestCode);
            }
        }else{
            //无未授予权限，执行后续操作
            listener.executeMethod(objects);
        }

    }
    /**
     * 处理权限申请结果，通过Activity或Fragment的onRequestPermissionsResult方法接收申请结果
     * @param activity Activity对象
     * @param requestCode 请求码
     * @param permissions 申请的权限列表
     * @param grantResults 权限授予结果（PackageManager.PERMISSION_DENIED：拒绝，PackageManager.PERMISSION_GRANTED：同意）
     * */
    public void handlePermissionsResult(final BaseActivity activity, int requestCode, String[] permissions, int[] grantResults){
        ArrayList<String> deniedList = new ArrayList<>();
        for (int i=0;i<grantResults.length;i++){
            if (grantResults[i]== PackageManager.PERMISSION_DENIED){
                deniedList.add(permissions[i]);
            }
        }
        if (deniedList.size()>0){
            //权限不通过，提醒去开启
            DialogUtils.showCustomAlertDialog(activity, "权限申请", "在设置-应用-权限开启储存空间权限，否则无法正常使用",
                    "去设置", "退出", new DialogUtils.onDialogClickListener() {
                        @Override
                        public void onPositive() {
                            Utils.goAppDetailSettingIntent(activity);
                        }

                        @Override
                        public void onNegative() {
                            android.os.Process.killProcess(android.os.Process
                                    .myPid());
                        }
                    });
        }else{
            //权限申请通过，执行后续操作
            MethodParams params = methods.get(requestCode);
            if (params.getListener()!=null){
                params.getListener().executeMethod(params.getObjects());
            }

        }
    }
    public interface AsynMethodListener{
        public void executeMethod(Object... objects);
    }
}
