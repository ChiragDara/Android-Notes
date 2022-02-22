package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = "AddNoteActivity";
    private EditText noteTitleEdittext;
    private EditText noteTextEditText;
    private String oldNoteTitle;
    private String oldNoteText;
    private long oldDate_Time;
    private NoteDAO oldNote;
    private boolean isNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitleEdittext = findViewById(R.id.title);
        noteTextEditText = findViewById(R.id.NotesText);
        // Getting intent
        Intent intent = getIntent();
        if (intent.hasExtra("EditNote")) {
            oldNote = (NoteDAO) intent.getSerializableExtra("EditNote");
            if (oldNote != null) {
                oldNoteTitle = oldNote.getTitle();
                oldNoteText = oldNote.getText();
                oldDate_Time = intent.getLongExtra("Time", 0);
                noteTitleEdittext.setText(oldNoteTitle);
                noteTextEditText.setText(oldNoteText);
            }
            isNew = false;
        } else {
            isNew = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_save) {
            addSaveActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addSaveActivity() {

        String noteTitle = noteTitleEdittext.getText().toString();
        String noteText = noteTextEditText.getText().toString();
        // Edit Old Note
        if (isNew == false) {
            if (noteTitle.trim().isEmpty()) {
                showTitleDialogBox();
                return;
            }
            oldNote.setTitle(noteTitle);
            oldNote.setText(noteText);
            oldNote.setLastUpdate(oldDate_Time);
            Intent intent = new Intent();
            intent.putExtra("EditNote", oldNote);
            setResult(2, intent);
            finish();
        } else { // Add New Note
            if (noteTitle.trim().isEmpty()) {
                showTitleDialogBox();
                return;
            }
            // Creating new Note
            NoteDAO newNote = new NoteDAO(noteTitle, noteText);
            Intent intent = new Intent();
            intent.putExtra("NewNote", newNote);
            setResult(1, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        String noteTitle = noteTitleEdittext.getText().toString();
        String noteText = noteTextEditText.getText().toString();

        if (isNew && (!noteTitle.trim().isEmpty())) {
            showSaveDialogBox();
        } else if (isNew && (!noteText.trim().isEmpty())) {
            showSaveDialogBox();
        } else if (!(oldNote == null) && !(noteTitle.equals(oldNote.getTitle()))) {
            showSaveDialogBox();
        } else if (!(oldNote == null) && !(noteText.equals(oldNote.getText()))) {
            showSaveDialogBox();
        } else {
            AddNoteActivity.super.onBackPressed();
            return;
        }
    }

    public void showTitleDialogBox() {
        Toast.makeText(this, "Note cannot be saved without title", Toast.LENGTH_LONG).show();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert) //set icon
                .setTitle(R.string.no_title)//set title
                .setMessage(R.string.no_title_message)//set message
                .setPositiveButton(R.string.dialog_box_yes, new DialogInterface.OnClickListener() { // Positive Button Action
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddNoteActivity.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton(R.string.dialog_box_no, new DialogInterface.OnClickListener() { // Negative Button Action
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                })
                .show();
    }

    public void showSaveDialogBox() {
        String noteTitle = noteTitleEdittext.getText().toString();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)//set icon
                .setTitle(R.string.save_note_dialogbox_title)//set title
                .setMessage(getString(R.string.save_note_dialogbox_message) + noteTitle + "' ?")//set message
                .setPositiveButton(R.string.dialog_box_yes, new DialogInterface.OnClickListener() {// Positive Button Action
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addSaveActivity();
                        finish();
                    }
                })
                .setNegativeButton(R.string.dialog_box_no, new DialogInterface.OnClickListener() {// Negative Button Action
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddNoteActivity.super.onBackPressed();
                        finish();
                    }
                })
                .show();
    }
}


