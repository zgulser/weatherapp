package com.ebay.codingexercise.apps.weatherinfo;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Zeki Gulser R&D B.V on 31/05/2018.
 */
public class RobolectricTestRunnerWithResources extends RobolectricTestRunner {

    public RobolectricTestRunnerWithResources(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        try {
            URL testClassesURL = new URL("file://" + RobolectricTestRunnerWithResources.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath());
            URL manifestURL = new URL(testClassesURL, "../../../../../../src/main/AndroidManifest.xml");
            URL resURL = new URL(testClassesURL, "../../../merged-not-compiled-resources/debug");
            URL assetsURL = new URL(testClassesURL, "../../../../../../src/test/assets");
            return new AndroidManifest(Fs.fromURL(manifestURL), Fs.fromURL(resURL), Fs.fromURL(assetsURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to load resources");
    }
}