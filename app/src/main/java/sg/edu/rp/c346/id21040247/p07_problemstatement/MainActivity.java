package sg.edu.rp.c346.id21040247.p07_problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    Spinner spOtions;

    LinearLayout loMain;
    View loEmpty;

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
        spOtions = findViewById(R.id.spinner);
        tvEmpty = findViewById(R.id.empty);

        lvToDoList = findViewById(R.id.listViewColor);

        loMain = findViewById(R.id.mainView);
        loEmpty = findViewById(R.id.emptyLayout);

        registerForContextMenu(lvToDoList);

        //ListView Adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.adapter_layout_themes, alToDoList);


        //view for empty arraylist
        lvToDoList.setEmptyView(loEmpty);


        spOtions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position) {
                    case 0:
                        etInputString.setHint(getString(R.string.typeinnewtask));
                        btnDelete.setEnabled(false);
                        btnDelete.setAlpha(0.8f);
                        btnAdd.setAlpha(1f);
                        btnAdd.setEnabled(true);
                        //can set 2 views as well
                        //etInputString.setVisibility(View.GONE/VISIBLE);
                        etInputString.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case 1:
                        etInputString.setHint(getString(R.string.typeinindex));
                        btnAdd.setEnabled(false);
                        btnAdd.setAlpha(.8f);
                        btnDelete.setAlpha(1f);
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
                } else {
                    alToDoList.add(newEntry);
                    //can put outside if else as well because of if statement, nothing is added anyways so no change will be made,
                    //for btnDelete and btnClear i put .notifyDataSetChanged outside if else
                    lvToDoList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //
                    Toast.makeText(getBaseContext(), getString(R.string.taskadded), Toast.LENGTH_SHORT).show();
                    //cannot invoke method using above variable due toString()
                    etInputString.getText().clear();
                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alToDoList.isEmpty() == false) {
                    alToDoList.clear();
                    Toast.makeText(getBaseContext(), getString(R.string.taskcleared), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.taskempty), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String indexString = etInputString.getText().toString();
                //int index = Integer.parseInt(indexString);

                //onclick button without any input, prevents crash from "" and null errors
                if (indexString.trim().length() < 1) {
                    Toast.makeText(getBaseContext(), getString(R.string.enterindex), Toast.LENGTH_SHORT).show();
                    //checks for empty arraylist when trying to remove
                } else if (alToDoList.isEmpty()) {
                    Toast.makeText(getBaseContext(), getString(R.string.notask), Toast.LENGTH_SHORT).show();
                    etInputString.getText().clear();
                } else {
                    int index = Integer.parseInt(indexString);
                    //QNS: when i put int index outside the else statement, it crashes when i press delete btn without any inputs, why? e.g. commented above
                    //checks for minimum and maximum index (boundary testing)
                    if (alToDoList.size() < index || index <= 0) {
                        Toast.makeText(getBaseContext(), getString(R.string.wrongindex), Toast.LENGTH_SHORT).show();
                        etInputString.getText().clear();
                        //triggers remove method if it passes above test
                    } else {
                        alToDoList.remove(index - 1);
                        etInputString.getText().clear();
                        Toast.makeText(getBaseContext(), getString(R.string.task) + "" + index + getString(R.string.remove), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        lvToDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = ((CheckedTextView) view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                //adapter.getItem(i) - obtain adapterView through its position i
            }
        });
    }

    //Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }

    //On Options Selected
    @Override
    public boolean onOptionsItemSelected(MenuItem themes) {

        int id = themes.getItemId();

        //loMain.setBackgroundColor(this.getResources().getColor(R.color.theme1pri));

        //Dark
        if (id == R.id.Dark) {
            loMain.setBackgroundColor(this.getResources().getColor(R.color.theme1pri));
            lvToDoList.setBackgroundColor(this.getResources().getColor(R.color.theme1sec));
            etInputString.setBackgroundColor(this.getResources().getColor(R.color.theme1tri));
            btnAdd.setBackgroundColor(this.getResources().getColor(R.color.theme1btn));
            tvEmpty.setTextColor(this.getResources().getColor(R.color.white));
            btnClear.setBackgroundColor(this.getResources().getColor(R.color.theme1btn));
            btnDelete.setBackgroundColor(this.getResources().getColor(R.color.theme1btn));
            spOtions.setBackgroundColor(this.getResources().getColor(R.color.theme1tri));
            return true;
            //Light
        } else if (id == R.id.Light) {
            loMain.setBackgroundColor(this.getResources().getColor(R.color.theme2pri));
            lvToDoList.setBackgroundColor(this.getResources().getColor(R.color.cream));
            etInputString.setBackgroundColor(this.getResources().getColor(R.color.theme2sec));
            tvEmpty.setTextColor(this.getResources().getColor(R.color.black));
            btnAdd.setBackgroundColor(this.getResources().getColor(R.color.theme2btn));
            btnClear.setBackgroundColor(this.getResources().getColor(R.color.theme2btn));
            btnDelete.setBackgroundColor(this.getResources().getColor(R.color.theme2btn));
            spOtions.setBackgroundColor(this.getResources().getColor(R.color.theme2sec));
            return true;
            //Pastel Pink
        } else if (id == R.id.CrazyBomb) {
            loMain.setBackgroundColor(this.getResources().getColor(R.color.theme3pri));
            lvToDoList.setBackgroundColor(this.getResources().getColor(R.color.theme3sec));
            etInputString.setBackgroundColor(this.getResources().getColor(R.color.theme3tri));
            btnAdd.setBackgroundColor(this.getResources().getColor(R.color.theme3btn));
            btnClear.setBackgroundColor(this.getResources().getColor(R.color.theme3btn));
            btnDelete.setBackgroundColor(this.getResources().getColor(R.color.theme3btn));
            tvEmpty.setTextColor(this.getResources().getColor(R.color.black));
            spOtions.setBackgroundColor(this.getResources().getColor(R.color.theme3tri));
            return true;
            //Galaxy
        } else if (id == R.id.Coral) {
            loMain.setBackgroundColor(this.getResources().getColor(R.color.theme4pri));
            lvToDoList.setBackgroundColor(this.getResources().getColor(R.color.theme4sec));
            etInputString.setBackgroundColor(this.getResources().getColor(R.color.theme4sec));
            btnAdd.setBackgroundColor(this.getResources().getColor(R.color.theme4tri));
            btnClear.setBackgroundColor(this.getResources().getColor(R.color.theme4tri));
            tvEmpty.setTextColor(this.getResources().getColor(R.color.white));
            btnDelete.setBackgroundColor(this.getResources().getColor(R.color.theme4tri));
            spOtions.setBackgroundColor(this.getResources().getColor(R.color.theme4sec));
            return true;
        }
        return super.onOptionsItemSelected(themes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, 0, 0, "Priority");
    }

    @Override
    public boolean onContextItemSelected(MenuItem option) {

        if (option.getItemId() == 0){
           //
        }
        return true;
    }
}