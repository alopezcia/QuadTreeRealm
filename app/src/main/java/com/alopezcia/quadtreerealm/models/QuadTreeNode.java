package com.alopezcia.quadtreerealm.models;

import com.alopezcia.quadtreerealm.app.QuadTreeTestApp;

import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class QuadTreeNode extends RealmObject {
    @PrimaryKey
    private int id;
    private QuadTreeExtent extent;
    private RealmList<QuadTreeNode> nodes;
    private RealmList<QuadTreeEntity> entitites;

    public QuadTreeNode(){}

    public QuadTreeNode( QuadTreeExtent extent )
    {
        this.id = QuadTreeTestApp.QuadTreeNodeID.incrementAndGet();
        this.extent = extent;
        this.entitites = new RealmList<QuadTreeEntity>();
    }

    public int getId() {
        return this.id;
    }

    public QuadTreeExtent getExtent() {
        return this.extent;
    }


    public RealmList<QuadTreeEntity> getEntitites() {
        return this.entitites;
    }

    public RealmList<QuadTreeNode> getNodes() { return this.nodes; }

    public void insert(QuadTreeEntity entity)
    {
        // If the entity is not contained in this quad, there's a problem
        if( !this.extent.contains(entity))
        {
            return;
        }

        // If the subnodes are null create them.
        if( this.nodes == null )
        {
            createSubNodes();
        }

        // For each subnode:
        // if node conatins the entity, add the item to that node and return
        // this recurses into the node that is just large enough to fit this entity
        for (int i=0; i<4; i++ )
        {
            QuadTreeNode subNode = this.nodes.get(i);
            if( subNode.getExtent().contains(entity))
            {
                subNode.insert(entity);
                return;
            }
        }

        // if we make it to here, either
        // 1) none of the subnodes completely contained the item, or
        // 2) we're at  the smallest subnode size allowed
        // add the item to this node's contents
        this.entitites.add(entity);
    }

    private void createSubNodes()
    {
        this.nodes = new RealmList<QuadTreeNode>();

        // the smallest subnode has an area
        if( (this.extent.getHeight()*this.extent.getWidth()) <= 10 )
            return;

        double halfWidth = this.extent.getWidth() / 2.0;
        double halfHeight = this.extent.getHeight() / 2.0;
        double minLat = this.extent.getMinLat();
        double minLng = this.extent.getMinLng();
        double maxLat = this.extent.getMaxLat();
        double maxLng = this.extent.getMaxLng();
        double medLat = minLat + halfHeight;
        double medLng = minLng + halfWidth;
        QuadTreeExtent e1 = new QuadTreeExtent(minLat, minLng, medLat, medLng );
        this.nodes.add( new QuadTreeNode(e1));
        QuadTreeExtent e2 = new QuadTreeExtent(medLat, minLng, maxLat, medLng );
        this.nodes.add( new QuadTreeNode(e2));
        QuadTreeExtent e3 = new QuadTreeExtent(minLat, medLng, medLat, maxLng );
        this.nodes.add( new QuadTreeNode(e3));
        QuadTreeExtent e4 = new QuadTreeExtent(medLat, medLng, maxLat, maxLng );
        this.nodes.add( new QuadTreeNode(e4));


    }

    public String getTitle()
    {
        return String.format(Locale.ENGLISH, "Nodo Id: %d - Area: %d Km2", this.id, this.extent.getAreaKm2());
    }
}
