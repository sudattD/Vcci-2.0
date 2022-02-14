package sbnri.consumer.android.util;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import sbnri.consumer.android.R;

public class FragmentUtils {

    private FragmentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final int TYPE_ADD_FRAGMENT = 0x01;
    private static final int TYPE_REPLACE_FRAGMENT = 0x01 << 5;

    private static final String ARGS_TYPE = "args_type";
    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @return fragment
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int containerId) {
        return addFragment(fragmentManager, fragment, containerId, false);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isHide          是否显示
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int containerId,
                                       boolean isHide) {
        return addFragment(fragmentManager, fragment, containerId, isHide, true);
    }

    /**
     * 新增fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isHide          是否显示
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment addFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int containerId,
                                       boolean isHide,
                                       boolean isAddStack) {
        putArgs(fragment, new Args(TYPE_ADD_FRAGMENT, containerId, isHide, isAddStack));
        String name = fragment.getClass().getName();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment, name);
        if (isAddStack) {
            ft.addToBackStack(name);
        }
        ft.commit();
        return fragment;
    }

    /**
     * 替换fragment
     *
     * @param srcFragment  源fragment
     * @param destFragment 目标fragment
     * @param isAddStack   是否入回退栈
     * @return {@code null} 失败
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Fragment replaceFragment(@NonNull Fragment srcFragment,
                                           @NonNull Fragment destFragment,
                                           boolean isAddStack) {
        if (srcFragment.getArguments() == null) return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0) return null;
        return replaceFragment(srcFragment.getFragmentManager(), containerId, destFragment, isAddStack);
    }

    public static Fragment replaceFragment(@NonNull Fragment srcFragment,
                                           @NonNull Fragment destFragment,
                                           boolean isAddStack, String tag) {
        if (srcFragment.getArguments() == null) return null;
        int containerId = srcFragment.getArguments().getInt(ARGS_ID);
        if (containerId == 0) return null;
        return replaceFragment(srcFragment.getFragmentManager(), containerId, destFragment, isAddStack, tag);
    }

    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           int containerId,
                                           @NonNull Fragment fragment,
                                           boolean isAddStack, String tag) {
        putArgs(fragment, new Args(TYPE_REPLACE_FRAGMENT, containerId, false, isAddStack));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (isAddStack) {
            ft.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
            ft.addToBackStack(tag);
            ft.replace(containerId, fragment, tag);
            ft.commit();
            return fragment;
        }
        ft.replace(containerId, fragment, tag);
        ft.commit();
        return fragment;
    }

    public static Fragment replaceFragmentWithoutAnimation(@NonNull FragmentManager fragmentManager,
                                                           int containerId,
                                                           @NonNull Fragment fragment,
                                                           boolean isAddStack, String tag) {
        putArgs(fragment, new Args(TYPE_REPLACE_FRAGMENT, containerId, false, isAddStack));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (isAddStack) {
            ft.addToBackStack(tag);
            ft.replace(containerId, fragment, tag);
            ft.commit();
            return fragment;
        }
        ft.replace(containerId, fragment, tag);
        ft.commit();
        return fragment;
    }

    /**
     * 替换fragment
     *
     * @param fragmentManager fragment管理器
     * @param containerId     布局Id
     * @param fragment        fragment
     * @param isAddStack      是否入回退栈
     * @return fragment
     */
    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           int containerId,
                                           @NonNull Fragment fragment,
                                           boolean isAddStack) {
        putArgs(fragment, new Args(TYPE_REPLACE_FRAGMENT, containerId, false, isAddStack));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        if (isAddStack) {
            ft.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
            ft.addToBackStack(name);
            ft.replace(containerId, fragment, name);
            ft.commit();
            return fragment;
        }
        ft.replace(containerId, fragment, name);
        ft.commit();
        return fragment;
    }

    public static Fragment replaceFragmentWithoutAnimation(@NonNull FragmentManager fragmentManager,
                                                           int containerId,
                                                           @NonNull Fragment fragment,
                                                           boolean isAddStack) {
        putArgs(fragment, new Args(TYPE_REPLACE_FRAGMENT, containerId, false, isAddStack));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        if (isAddStack) {
            ft.addToBackStack(name);
            ft.replace(containerId, fragment, name);
            ft.commit();
            return fragment;
        }
        ft.replace(containerId, fragment, name);
        ft.commit();
        return fragment;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static Fragment replaceFragment(@NonNull FragmentManager fragmentManager,
                                           int containerId,
                                           @NonNull Fragment fragment,
                                           boolean isAddStack, int enterTransition, int exitTransition) {
        putArgs(fragment, new Args(TYPE_REPLACE_FRAGMENT, containerId, false, isAddStack));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        if (isAddStack) {
            ft.setCustomAnimations(enterTransition, 0, 0, enterTransition);
            ft.addToBackStack(name);
            ft.replace(containerId, fragment, name);
            ft.commit();
            return fragment;
        }
        ft.replace(containerId, fragment, name);
        ft.commit();
        return fragment;
    }

    public static void replaceFromRight(@NonNull FragmentManager fragmentManager,
                                        int containerId,
                                        @NonNull Fragment fragment,
                                        boolean isAddStack) {
        putArgs(fragment, new Args(TYPE_REPLACE_FRAGMENT, containerId, false, isAddStack));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        if (isAddStack) {
            ft.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
            ft.addToBackStack(name);
            ft.replace(containerId, fragment, name);
            ft.commit();
            return;
        }
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        ft.replace(containerId, fragment, name);
        ft.commit();
    }

    public static void replaceFromLeft(@NonNull FragmentManager fragmentManager,
                                       int containerId,
                                       @NonNull Fragment fragment,
                                       boolean isAddStack) {
        putArgs(fragment, new Args(TYPE_REPLACE_FRAGMENT, containerId, false, isAddStack));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        if (isAddStack) {
            ft.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
            ft.addToBackStack(name);
            ft.replace(containerId, fragment, name);
            ft.commit();
            return;
        }
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(containerId, fragment, name);
        ft.commit();
    }

    /**
     * 传参
     *
     * @param fragment fragment
     * @param args     参数
     */
    private static void putArgs(@NonNull Fragment fragment, Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_TYPE, args.type);
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
    }

    /**
     * 获取参数
     *
     * @param fragment fragment
     */
    private static Args getArgs(@NonNull Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null || bundle.getInt(ARGS_TYPE) == 0) return null;
        return new Args(bundle.getInt(ARGS_TYPE), bundle.getInt(ARGS_ID), bundle.getBoolean(ARGS_IS_HIDE), bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    static class Args {
        final int type;
        final int id;
        final boolean isHide;
        final boolean isAddStack;

        Args(int type, int id, boolean isHide, boolean isAddStack) {
            this.type = type;
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }
}
