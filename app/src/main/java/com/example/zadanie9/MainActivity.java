package com.example.zadanie9;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView settingsListView;
    TextView editingLabelTextView;
    SeekBar valueSeekBar;
    TextView seekBarValueTextView;

    ArrayList<String> settingNames = new ArrayList<>();
    ArrayList<Integer> settingValues = new ArrayList<>();
    ArrayList<String> settingUnits = new ArrayList<>();
    ArrayList<String> displayItems = new ArrayList<>();

    ArrayAdapter<String> adapter;

    int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsListView = findViewById(R.id.settingsListView);
        editingLabelTextView = findViewById(R.id.editingLabelTextView);
        valueSeekBar = findViewById(R.id.valueSeekBar);
        seekBarValueTextView = findViewById(R.id.seekBarValueTextView);

        editingLabelTextView.setText("Wybierz opcję z listy powyżej");
        seekBarValueTextView.setText("Wartość: -");
        valueSeekBar.setEnabled(false);

        settingNames.add("Jasność Ekranu");
        settingValues.add(50);
        settingUnits.add("%");

        settingNames.add("Głośność Dźwięków");
        settingValues.add(80);
        settingUnits.add("%");

        settingNames.add("Czas Automatycznej Blokady");
        settingValues.add(30);
        settingUnits.add("s");

        for (int i = 0; i < settingNames.size(); i++) {
            String text = settingNames.get(i) + ": " + settingValues.get(i) + settingUnits.get(i);
            displayItems.add(text);
        }

        adapter = new ArrayAdapter<>(this, R.layout.list_item_setting, R.id.itemTextView, displayItems);
        settingsListView.setAdapter(adapter);

        settingsListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemPosition = position;

            String name = settingNames.get(position);
            int value = settingValues.get(position);

            editingLabelTextView.setText("Edytujesz: " + name);
            seekBarValueTextView.setText("Wartość: " + value);
            valueSeekBar.setProgress(value);
            valueSeekBar.setEnabled(true);
        });
        valueSeekBar.setMax(100);

        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                if (fromUser && selectedItemPosition != -1) {
                    seekBarValueTextView.setText("Wartość: " + value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (selectedItemPosition != -1) {
                    int value = seekBar.getProgress();
                    settingValues.set(selectedItemPosition, value);

                    String newText = settingNames.get(selectedItemPosition) + ": " + value + settingUnits.get(selectedItemPosition);
                    displayItems.set(selectedItemPosition, newText);

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
