package com.alopezcia.quadtreerealm.models;

import com.alopezcia.quadtreerealm.app.QuadTreeTestApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class QuadTree extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String layerName;
    private int featCount;
    private QuadTreeNode rootNode;

    public QuadTree(){}

    public QuadTree(String layerName, int featCount, double minlat, double minlng, double maxlat, double maxlng)
    {
        this.id = QuadTreeTestApp.QuadTreeID.incrementAndGet();
        this.layerName = layerName;
        this.featCount = featCount;
        this.rootNode = new QuadTreeNode(new QuadTreeExtent(minlat, minlng, maxlat, maxlng));
    }

    public int getId() {
        return id;
    }

    public String getLayerName() {
        return layerName;
    }

    public QuadTreeNode getRootNode() {
        return rootNode;
    }

    public int getFeatCount() { return featCount; }

    public void addEntity(QuadTreeEntity entity)
    {
        this.rootNode.insert(entity);
    }
}
