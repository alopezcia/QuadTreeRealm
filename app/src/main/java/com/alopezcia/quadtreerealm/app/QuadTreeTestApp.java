package com.alopezcia.quadtreerealm.app;

import android.app.Application;

import com.alopezcia.quadtreerealm.models.QuadTree;
import com.alopezcia.quadtreerealm.models.QuadTreeEntity;
import com.alopezcia.quadtreerealm.models.QuadTreeExtent;
import com.alopezcia.quadtreerealm.models.QuadTreeNode;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class QuadTreeTestApp extends Application {
    public static AtomicInteger QuadTreeID = new AtomicInteger();
    public static AtomicInteger QuadTreeEntityID = new AtomicInteger();
    public static AtomicInteger QuadTreeExtentID = new AtomicInteger();
    public static AtomicInteger QuadTreeNodeID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        File[] filesfi = getExternalFilesDirs(null);
        if( filesfi.length > 0 ) {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .directory(filesfi[0])
                    .build();
            Realm.setDefaultConfiguration(config);
        }else
        {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
        }

        Realm realm = Realm.getDefaultInstance();

        QuadTreeID = getIdByTable(realm, QuadTree.class);
        QuadTreeEntityID = getIdByTable(realm, QuadTreeEntity.class);
        QuadTreeExtentID = getIdByTable(realm, QuadTreeExtent.class);
        QuadTreeNodeID = getIdByTable(realm, QuadTreeNode.class);
        realm.close();
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass)
    {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size()>0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }
}
