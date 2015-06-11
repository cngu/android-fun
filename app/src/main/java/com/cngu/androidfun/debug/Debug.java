package com.cngu.androidfun.debug;

import com.cngu.androidfun.BuildConfig;

/**
 * This class defines global debug flags that may be used in multiple classes. The global flags will
 * never be set in a release build.
 *
 * <p>For callers that use their own local class-level debug flags, a convenience method
 * {@link Debug#isInDebugMode(boolean)} is provided to prevent logging in the release build.
 */
@SuppressWarnings("PointlessBooleanExpression")
public class Debug {
    /**
     * The running application is a debug build.
     */
    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static final boolean DEBUG_ACTIVIYTY_LIFECYCLE = DEBUG && true;
    public static final boolean DEBUG_FRAGMENT_LIFECYCLE  = DEBUG && true;

    /**
     * Used to determine if you can perform logging based on local debug flags (i.e. non-global
     * debug flags that are declared locally in your class and not in {@link Debug}).
     *
     * <p>This method should always be used when doing conditional logging if you want to prevent
     * logging in the release build.
     *
     * <pre>
     * {@code
     * public static final boolean MY_LOCAL_DEBUG_FLAG = true;
     * ...
     * if (Debug.isInDebugMode(MY_LOCAL_DEBUG_FLAG)) {
     *     // Begin logging
     * }
     * }
     * </pre>
     *
     * This is a convenience method and is equivalent to the following:
     * <pre>
     * {@code
     * public static final boolean MY_LOCAL_DEBUG_FLAG = true;
     * ...
     * if (BuildConfig.DEBUG && MY_LOCAL_DEBUG_FLAG)) {
     *     // Begin logging
     * }
     * }
     * </pre>
     *
     * @param debugFlag Local 'class-level' debug flag.
     * @return false if {@link BuildConfig#DEBUG} is false; otherwise {@code debugFlag} is returned.
     */
    public static boolean isInDebugMode(boolean debugFlag) {
        return DEBUG && debugFlag;
    }
}
