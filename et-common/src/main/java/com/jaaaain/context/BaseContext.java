package com.jaaaain.context;

public class BaseContext {

    // 定义一个 ThreadLocal 变量，用来保存线程本地的 userId
    private static ThreadLocal<Long> currentIdThreadLocal = new ThreadLocal<>();

    // 定义一个 ThreadLocal 变量，用来保存线程本地的 role
    private static ThreadLocal<Integer> currentRoleThreadLocal = new ThreadLocal<>();

    // 将 userId 设置到当前线程的 ThreadLocal 变量中
    public static void setCurrentId(Long id) {
        currentIdThreadLocal.set(id);
    }

    // 从当前线程的 ThreadLocal 变量中获取保存的 userId
    public static Long getCurrentId() {
        return currentIdThreadLocal.get();
    }

    // 移除当前线程中的 ThreadLocal 变量，释放内存，防止内存泄漏
    public static void removeCurrentId() {
        currentIdThreadLocal.remove();
    }

    // 将 role 设置到当前线程的 ThreadLocal 变量中
    public static void setCurrentRole(Integer role) {
        currentRoleThreadLocal.set(role);
    }

    // 从当前线程的 ThreadLocal 变量中获取保存的 role
    public static Integer getCurrentRole() {
        return currentRoleThreadLocal.get();
    }

    // 移除当前线程中的 role，释放内存，防止内存泄漏
    public static void removeCurrentRole() {
        currentRoleThreadLocal.remove();
    }
}