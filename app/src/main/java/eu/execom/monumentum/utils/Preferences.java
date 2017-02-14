package eu.execom.monumentum.utils;

import org.androidannotations.annotations.sharedpreferences.SharedPref;


@SharedPref(SharedPref.Scope.UNIQUE)
public interface Preferences {

    int id();
}