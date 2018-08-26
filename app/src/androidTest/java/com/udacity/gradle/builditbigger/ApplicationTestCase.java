package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

public abstract class ApplicationTestCase<T extends Application> extends AndroidTestCase {

    Class<T> mApplicationClass;

    private Context mSystemContext;

    public ApplicationTestCase(Class<T> applicationClass) {
        mApplicationClass = applicationClass;
    }

    private T mApplication;
    private boolean mAttached = false;
    private boolean mCreated = false;

    /**
     * @return Returns the actual Application under test.
     */
    public T getApplication() {
        return mApplication;
    }

    /**
     * This will do the work to instantiate the Application under test.  After this, your test
     * code must also start and stop the Application.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // get the real context, before the individual tests have a chance to muck with it
        mSystemContext = getContext();
    }

    /**
     * Load and attach the application under test.
     */
    private void setupApplication() {
        mApplication = null;
        try {
            mApplication = (T) Instrumentation.newApplication(mApplicationClass, getContext());
        } catch (Exception e) {
            assertNotNull(mApplication);
        }
        mAttached = true;
    }

    /**
     * Start the Application under test, in the same way as if it was started by the system.
     * If you use this method to start the Application, it will automatically
     * be stopped by {@link #tearDown}.  If you wish to inject a specialized Context for your
     * test, by calling {@link AndroidTestCase#setContext(Context) setContext()},
     * you must do so  before calling this method.
     */
    final protected void createApplication() {
        assertFalse(mCreated);

        if (!mAttached) {
            setupApplication();
        }
        assertNotNull(mApplication);

        mApplication.onCreate();
        mCreated = true;
    }

    /**
     * This will make the necessary calls to terminate the Application under test (it will
     * call onTerminate().  Ordinarily this will be called automatically (by {@link #tearDown}, but
     * you can call it directly from your test in order to check for proper shutdown behaviors.
     */
    final protected void terminateApplication() {
        if (mCreated) {
            mApplication.onTerminate();
        }
    }

    /**
     * Shuts down the Application under test.  Also makes sure all resources are cleaned up and
     * garbage collected before moving on to the next
     * test.  Subclasses that override this method should make sure they call super.tearDown()
     * at the end of the overriding method.
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        terminateApplication();
        mApplication = null;

        // Scrub out members - protects against memory leaks in the case where someone
        // creates a non-static inner class (thus referencing the test case) and gives it to
        // someone else to hold onto
        scrubClass(ApplicationTestCase.class);

        super.tearDown();
    }

    /**
     * Return a real (not mocked or instrumented) system Context that can be used when generating
     * Mock or other Context objects for your Application under test.
     *
     * @return Returns a reference to a normal Context.
     */
    public Context getSystemContext() {
        return mSystemContext;
    }

    /**
     * This test simply confirms that the Application class can be instantiated properly.
     *
     * @throws Exception
     */
    final public void testApplicationTestCaseSetUpProperly() throws Exception {
        setupApplication();
        assertNotNull("Application class could not be instantiated successfully", mApplication);
    }
}

