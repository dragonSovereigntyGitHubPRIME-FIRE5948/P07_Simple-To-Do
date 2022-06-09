package sg.edu.rp.c346.id21040247.p07_problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    //Objects & Variables Initialisation
    EditText etInputString;
    Button btnAdd;
    Button btnClear;
    Button btnDelete;
    ListView lvToDoList;
    Spinner spnoption;

    TextView tvEmpty;

    ArrayList<String> alToDoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInputString = findViewById(R.id.editTextString);
        btnAdd = findViewById(R.id.button_Add);
        btnClear = findViewById(R.id.button_Clear);
        btnDelete = findViewById(R.id.button_delete);
        spnoption = findViewById(R.id.spinner);
        tvEmpty = findViewById(R.id.empty);

        lvToDoList = findViewById(R.id.listViewColor);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alToDoList);
        lvToDoList.setEmptyView(tvEmpty);

        spnoption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:
                        etInputString.setHint(getString(R.string.typeinnewtask));
                        btnDelete.setEnabled(false);
                        btnAdd.setEnabled(true);
                        //can set 2 views as well
                        //etInputString.setVisibility(View.GONE/VISIBLE);
                        etInputString.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case 1:
                        etInputString.setHint(getString(R.string.typeinindex));
                        btnAdd.setEnabled(false);
                        btnDelete.setEnabled(true);
                        etInputString.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = etInputString.getText().toString();

                if (newEntry.trim().length() < 1) {
                    Toast.makeText(getBaseContext(), getString(R.string.entertask), Toast.LENGTH_SHORT).show();
                }else {
                    alToDoList.add(newEntry);
                }
                lvToDoList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alToDoList.isEmpty() == false) {
                    alToDoList.clear();
                } else{
                    Toast.makeText(getBaseContext(), getString(R.string.taskempty), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String indexString = etInputString.getText().toString();

                if (indexString.trim().length() < 1) {
                    Toast.makeText(getBaseContext(), getString(R.string.enterindex), Toast.LENGTH_SHORT).show();
                } else if (alToDoList.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.notask), Toast.LENGTH_SHORT).show();
                } else {

                    //QNS:
                    int index = Integer.parseInt(indexString);
                    if(alToDoList.size() < index || index <= 0) {
                        Toast.makeText(getBaseContext(), getString(R.string.wrongindex), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        alToDoList.remove(index - 1);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        lvToDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             String toastMessage = alToDoList.get(i);
             Toast.makeText(getBaseContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }
        });



    }}