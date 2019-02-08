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
import com.alopezcia.quadtreerealm.adapters.QuadTreeAdapter;
import com.alopezcia.quadtreerealm.app.QuadTreeTestApp;
import com.alopezcia.quadtreerealm.models.QuadTree;
import com.alopezcia.quadtreerealm.models.QuadTreeEntity;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<QuadTree>>, AdapterView.OnItemClickListener {
    private Realm realm;
    private FloatingActionButton fab;
    private ListView listView;
    private QuadTreeAdapter adapter;
    private RealmResults<QuadTree> quadTreeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        quadTreeList = realm.where(QuadTree.class).findAll();
        quadTreeList.addChangeListener(this);
        adapter = new QuadTreeAdapter(this, quadTreeList, R.layout.list_view_quadtree_item);
        listView = (ListView) findViewById(R.id.listViewQuadTree);
        listView.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fabAddQuadTree);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                showAlertForCreatingQuadTree("Last QuadTree ID: " + QuadTreeTestApp.QuadTreeID.get(), "Type a name for your new QuadTree");
            }
        });

    }

    /*** CRUD Actions ***/
    private  void createQuadTree(String layerName, int fc)
    {
        realm.beginTransaction();
        QuadTree qt = new QuadTree(layerName, fc, 38.305506165000054, -0.9338556609999387 , 38.67903290400005, -0.3090815899999484);
        realm.copyToRealm(qt);
        realm.commitTransaction();
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

    @Override
    public void onChange(RealmResults<QuadTree> quadTrees) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, NodeActivity.class);
        intent.putExtra("layerName", quadTreeList.get(position).getLayerName());
        intent.putExtra("id", quadTreeList.get(position).getRootNode().getId());
        startActivity(intent);
    }
}
