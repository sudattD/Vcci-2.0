package sbnri.consumer.android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.core.app.ActivityCompat;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sbnri.consumer.android.R;
import sbnri.consumer.android.dialogue.ServifyDialog;
import sbnri.consumer.android.dialogue.ServifyDialogClick;

import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static androidx.core.content.PermissionChecker.checkSelfPermission;
import static sbnri.consumer.android.constants.Constants.REQUEST_PERMISSION_INDIVIDUAL;

public class PermissionUtils {

    private static RxPermissions rxPermissions;
    private static int permissionCount = 0;
    private static int requestCount = 0;
    private static boolean isOnResume = true;
    private static boolean isFirstRun = true;
    private static boolean isRun = false;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public static void checkPermissionAndRun(final Activity activity, final String permissionFor, final Runnable runnable) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissionFor).subscribe(permission -> {
            if (permission.granted) {
                runnable.run();
            } else if (permission.shouldShowRequestPermissionRationale) {
                showPermissionRationale(activity, permissionFor, runnable);
            } else {
                checkAndAskPermsRx(activity, permission.name, true);
            }
        });
    }


    public static boolean checkPermissionInOnResume(final Activity activity, final String permissionFor) {
        int permission = checkSelfPermission(activity, permissionFor);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            if (!shouldShowRequestPermissionRationale(activity, permissionFor)) {
                Logger.d("Not granted");
                if (Hawk.get(permissionFor, false)) {
                    checkAndAskPermsRx(activity, permissionFor, false);
                } else {
                    Logger.d("No request asking again");
                    Hawk.put(permissionFor, true);
                    ActivityCompat.requestPermissions(
                            activity,
                            new String[]{permissionFor},
                            REQUEST_PERMISSION_INDIVIDUAL
                    );
                }
            } else {
                permissionCount++;
                if (permissionCount == 3) {
                    showPermissionRationale(activity, permissionFor, activity.getString(R.string.permission_denied_count_3));
                } else if (permissionCount == 6) {
                    showPermissionRationale(activity, permissionFor, activity.getString(R.string.permission_denied_count_6));
                } else if (permissionCount == 7) {
                    activity.finishAffinity();
                    System.exit(0);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permissionFor}, REQUEST_PERMISSION_INDIVIDUAL);
                }
            }
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public static void checkPermissionAndRun(final Activity activity, final String permissionFor, final PermissionCallback callback) {
        rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissionFor).subscribe(permission -> {
            if (permission.granted) {
                callback.onPermissionGranted();
            } else if (permission.shouldShowRequestPermissionRationale) {
                callback.onPermissionDenied();
            } else {
                callback.onPermissionDeniedForever();
            }
        });
    }

    public static void checkPermissionsAndRun(final Activity activity, final String[] permissions, final Runnable runnable) {
        permissionCount = 0;
        requestCount = 0;
        rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissions).subscribe(new Observer<Permission>() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Permission permission) {
                requestCount++;
                if (permission.granted) {
                    permissionCount++;
                    if (permissionCount == permissions.length) {
                        runnable.run();
                    }
                } else if (permission.shouldShowRequestPermissionRationale) {
                    if (requestCount == permissions.length) {
                        showPermissionRationale(activity, permissions, runnable);
                    }
                } else {
                    checkAndAskPermsRx(activity, permission.name, true);
                }
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    static void checkAndAskPermsRx(final Activity activity, final String permissionFor, boolean isCancelable) {
        CharSequence permsGroupName = "";
        try {
            PackageManager pm = activity.getPackageManager();
            PermissionInfo permissionInfo = pm.getPermissionInfo(permissionFor, 0);
            PermissionGroupInfo groupInfo = pm.getPermissionGroupInfo(permissionInfo.group, 0);
            permsGroupName = groupInfo.loadLabel(pm);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.d(e.getLocalizedMessage());
        }

        Logger.d("Group Name " + permsGroupName + " Not granted");

        String msg = "";
        if (!TextUtils.isEmpty(permsGroupName)) {
            msg = String.format(activity.getString(R.string.open_permission_setting_for_best_app_experience), permsGroupName.toString());
        }
        ServifyDialog.with(activity).dismiss();
        ServifyDialog.with(activity).setTitle(activity.getString(R.string.change_permissions)).setDescription(msg)
                .setButtonOneText(activity.getString(R.string.go_to_settings))
                .setIsCancelable(false)
                .setClickListener(new ServifyDialogClick() {
                    @Override
                    protected void buttonOneClick(Dialog dialogInstance) {
                        ServifyDialog.with(activity).dismiss();
                        openSettingsActivity(activity);
                        isFirstRun = true;
                    }

                    @Override
                    protected void buttonTwoClick(Dialog dialogInstance) {
                        ServifyDialog.with(activity).dismiss();
                    }
                });
        if (isCancelable) {
            ServifyDialog.getInstance().setButtonTwoText(activity.getString(R.string.cancel));
        }
        ServifyDialog.getInstance().show();
    }

    @SuppressWarnings("WeakerAccess")
    static void openSettingsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void setIsOnResume(boolean isOnResume) {
        PermissionUtils.isOnResume = isOnResume;
    }

    public static void setIsFirstRun(boolean isFirstRun) {
        PermissionUtils.isFirstRun = isFirstRun;
    }

    public static void setIsRun(boolean isRun) {
        PermissionUtils.isRun = isRun;
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void checkPermissionsAndRunDiagnosis(final Activity activity, final String[] permissions, final Runnable runnable) {
        /*
         * Use this only for diagnosis
         * */
        if (permissions.length > 0) {
            permissionCount = 0;
            rxPermissions = new RxPermissions(activity);
            rxPermissions.requestEach(permissions).subscribe(new Observer<Permission>() {
                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Permission permission) {
                    permissionCount++;
                    if (!permission.granted) {
                        if (!permission.shouldShowRequestPermissionRationale) {
                            checkAndAskPermsRx(activity, permission.name, true);
                        }
                    }

                    if (permissionCount == permissions.length) {
                        runnable.run();
                    }
                }
            });

        } else {
            runnable.run();
        }
    }

    private static void showPermissionRationale(final Activity activity, final String permissionFor, final Runnable runnable) {
        if (isFirstRun) {
            Dialog dialog = ActivityUtils.getFlavourSpecificDialog(activity, R.layout.bottomsheet_single_action);
            TextView tvHeader = dialog.findViewById(R.id.tvBottomSheetTitle);
            tvHeader.setText(activity.getString(R.string.we_need_your_permissions));
            TextView tvDescription = dialog.findViewById(R.id.tvBottomSheetDescription);
            tvDescription.setText(activity.getString(R.string.permission_rational));
            Button btnNext = dialog.findViewById(R.id.btnDone);
            btnNext.setText(activity.getString(R.string.lets_do_it));
            btnNext.setOnClickListener(v -> {
                isFirstRun = false;
                dialog.dismiss();
                checkPermissionAndRun(activity, permissionFor, runnable);
            });

            dialog.show();
        } else {
            isFirstRun = true;
        }
    }

    @SuppressWarnings("WeakerAccess")
    static void showPermissionRationale(final Activity activity, final String[] permissions, final Runnable runnable) {
        if (isFirstRun) {
            Dialog dialog = ActivityUtils.getFlavourSpecificDialog(activity, R.layout.bottomsheet_single_action);
            TextView tvHeader = dialog.findViewById(R.id.tvBottomSheetTitle);
            tvHeader.setText(activity.getString(R.string.we_need_your_permissions));
            TextView tvDescription = dialog.findViewById(R.id.tvBottomSheetDescription);
            tvDescription.setText(activity.getString(R.string.permission_rational));
            Button btnNext = dialog.findViewById(R.id.btnDone);
            btnNext.setText(activity.getString(R.string.lets_do_it));
            btnNext.setOnClickListener(v -> {
                isFirstRun = false;
                dialog.dismiss();
                checkPermissionsAndRun(activity, permissions, runnable);
            });
            dialog.show();
        } else {
            isFirstRun = true;
        }
    }

    private static void showPermissionRationale(final Activity activity, final String permissionFor, String message) {
        Dialog dialog = ActivityUtils.getFlavourSpecificDialog(activity, R.layout.bottomsheet_single_action);
        TextView tvHeader = dialog.findViewById(R.id.tvBottomSheetTitle);
        tvHeader.setText(activity.getString(R.string.we_need_your_permissions));
        TextView tvDescription = dialog.findViewById(R.id.tvBottomSheetDescription);
        tvDescription.setText(message);
        Button btnNext = dialog.findViewById(R.id.btnDone);
        btnNext.setText(activity.getString(R.string.lets_do_it));
        btnNext.setOnClickListener(v -> {
            isFirstRun = false;
            dialog.dismiss();
            ActivityCompat.requestPermissions(activity, new String[]{permissionFor}, REQUEST_PERMISSION_INDIVIDUAL);
        });
        dialog.setOnDismissListener(bottomSheetLayout1 ->
                ActivityCompat.requestPermissions(activity, new String[]{permissionFor}, REQUEST_PERMISSION_INDIVIDUAL));
        dialog.show();
    }

    public interface PermissionCallback {
        void onPermissionGranted();

        void onPermissionDenied();

        void onPermissionDeniedForever();
    }
}
