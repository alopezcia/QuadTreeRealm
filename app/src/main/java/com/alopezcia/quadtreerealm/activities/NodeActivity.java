package com.alopezcia.quadtreerealm.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alopezcia.quadtreerealm.R;
import com.alopezcia.quadtreerealm.adapters.QuadTreeNodeAdapter;
import com.alopezcia.quadtreerealm.models.QuadTree;
import com.alopezcia.quadtreerealm.models.QuadTreeNode;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NodeActivity extends AppCompatActivity {
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
            listView = (ListView) findViewById(R.id.listViewQuadTreeNode);

            adapter = new QuadTreeNodeAdapter(this, quadTreeNodes, R.layout.list_view_quadtreenode_item);
            listView.setAdapter(adapter);


        }
    }

    /*** Dialogs ***/
    private void showAlertForCreatingQuadTree(String title, String message )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if( title != null ) builder.setTitle(title);
        if( message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_quadtree, null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewQuadTree);
        final EditText inputFeat = (EditText) viewInflated.findViewById(R.id.editTextFeatCount);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String featCount = inputFeat.getText().toString().trim();
                String layerName = input.getText().toString().trim();
                if( (layerName.length() > 0) && (featCount.length() > 0))
                {
                    int fc = Integer.parseInt(featCount);
                    createQuadTree(layerName, fc);
                }
                else
                    Toast.makeText(getApplicationContext(), "The name is required to created new QuadTree", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
