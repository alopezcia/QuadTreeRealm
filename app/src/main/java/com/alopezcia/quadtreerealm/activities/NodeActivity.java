package com.alopezcia.quadtreerealm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alopezcia.quadtreerealm.R;
import com.alopezcia.quadtreerealm.adapters.QuadTreeNodeAdapter;
import com.alopezcia.quadtreerealm.models.QuadTree;
import com.alopezcia.quadtreerealm.models.QuadTreeEntity;
import com.alopezcia.quadtreerealm.models.QuadTreeNode;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NodeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Realm realm;
    private FloatingActionButton fab;
    private ListView listView;
    private QuadTreeNodeAdapter adapter;
    private RealmList<QuadTreeNode> quadTreeNodes;

    private String layerName;
    private int parentID;
    private QuadTreeNode parentNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        realm = Realm.getDefaultInstance();

        if( getIntent().getExtras()!= null )
        {
            layerName = getIntent().getExtras().getString("layerName");
            parentID = getIntent().getExtras().getInt("id");
            parentNode = realm.where(QuadTreeNode.class).equalTo("id", parentID).findFirst();
            quadTreeNodes = parentNode.getNodes();
            this.setTitle(layerName + " " + parentNode.getTitle());

            fab = (FloatingActionButton) findViewById(R.id.fabAddQuadTreeNode);
            fab.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    // showAlertForCreatingQuadTreeEntity("New Entity", "Type data for your new Entity");
                    Intent intent = new Intent(NodeActivity.this, MapActivity.class);
                    intent.putExtra("idExtent", parentNode.getExtent().getId());
                    startActivity(intent);
                }
            });

            listView = (ListView) findViewById(R.id.listViewQuadTreeNode);
            adapter = new QuadTreeNodeAdapter(this, quadTreeNodes, R.layout.list_view_quadtreenode_item);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    /*** CRUD Actions ***/
    private void createQuadTreeEntity(int oid, double lat, double lng )
    {
        if( parentNode.getExtent().contains(lng, lat))
        {
            realm.beginTransaction();
            QuadTreeEntity entity = new QuadTreeEntity(lat, lng, oid);
            realm.copyToRealm(entity);
            parentNode.insert(entity);
            realm.commitTransaction();
        }else
        {
            Toast.makeText(getApplicationContext(), "Entity out of Node Extent", Toast.LENGTH_SHORT).show();
        }
    }

    /*** Dialogs ***/
    private void showAlertForCreatingQuadTreeEntity(String title, String message )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if( title != null ) builder.setTitle(title);
        if( message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_entity, null);
        builder.setView(viewInflated);

        final EditText inputLat = (EditText) viewInflated.findViewById(R.id.editTextLatitude);
        final EditText inputLng = (EditText) viewInflated.findViewById(R.id.editTextLongitude);
        final EditText inputOID = (EditText) viewInflated.findViewById(R.id.editTextObjectID);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String latitude = inputLat.getText().toString().trim();
                String longitude = inputLng.getText().toString().trim();
                String objectId = inputOID.getText().toString().trim();
                if( (latitude.length() > 0) && (longitude.length() > 0) && (objectId.length() > 0))
                {
                    int oid = Integer.parseInt(objectId);
                    double lat = Double.parseDouble(latitude);
                    double lng = Double.parseDouble(longitude);
                    createQuadTreeEntity(oid, lat, lng);
                }
                else
                    Toast.makeText(getApplicationContext(), "Lat, Lng and ObjectID  are required to created new Entity", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(NodeActivity.this, NodeActivity.class);
        intent.putExtra("layerName", layerName);
        intent.putExtra("id", quadTreeNodes.get(position).getId());
        startActivity(intent);
    }
}
