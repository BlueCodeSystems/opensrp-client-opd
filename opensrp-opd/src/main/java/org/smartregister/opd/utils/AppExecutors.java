package org.smartregister.opd.utils;

/**
 * Created by keyman on 12/11/18.
 */

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind webservice requests).
 */
public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    private final Executor diskIO;

    private final Executor networkIO;

    private final Executor mainThread;

    public AppExecutors() {
        this(new DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT), new MainThreadExecutor());
    }

    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler;
        private final boolean direct;

        MainThreadExecutor() {
            Handler handler = null;
            boolean runDirect = false;
            try {
                Looper looper = Looper.getMainLooper();
                if (looper != null) {
                    handler = new Handler(looper);
                } else {
                    runDirect = true;
                }
            } catch (RuntimeException e) {
                // In plain unit tests there is no Android main looper; fall back to direct execution
                runDirect = true;
            }
            this.mainThreadHandler = handler;
            this.direct = runDirect;
        }

        @Override
        public void execute(@NonNull Runnable command) {
            if (direct || mainThreadHandler == null) {
                command.run();
            } else {
                mainThreadHandler.post(command);
            }
        }
    }

    /**
     * Executor that runs a task on a new background thread.
     */
    private static class DiskIOThreadExecutor implements Executor {

        private final Executor mDiskIO;

        public DiskIOThreadExecutor() {
            mDiskIO = Executors.newSingleThreadExecutor();
        }

        @Override
        public void execute(@NonNull Runnable command) {
            mDiskIO.execute(command);
        }
    }
}
