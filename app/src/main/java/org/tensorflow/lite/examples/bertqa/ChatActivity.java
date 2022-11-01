package org.tensorflow.lite.examples.bertqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    protected static final ArrayList<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Declare Design Elements
        Button sendButton = (Button) findViewById(R.id.sendbutton);
        EditText chatText = (EditText) findViewById(R.id.chatText);
        ListView list = (ListView) findViewById(R.id.chatList);

        //Declare a chat adapter and assign it to the chat list
        ChatAdapter messageAdapter = new ChatAdapter( this );
        list.setAdapter (messageAdapter);

        //Send button on click listener
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If there is text to send to the chat bot
                if(! String.valueOf(chatText.getText()).isEmpty()) {
                    //add it to the chat list
                    messages.add(String.valueOf(chatText.getText()));
                    messageAdapter.notifyDataSetChanged();

                    //Empty the message text field
                    chatText.setText("");

                    //add the response to the chat list
                    messages.add(getString(R.string.defaultResponse));
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });

        this.addHistory();
    }

    protected void addHistory(){
        SharedPreferences dh_sp = getSharedPreferences("history_sp", MODE_PRIVATE);
        Date currentTime = Calendar.getInstance().getTime();
        SharedPreferences.Editor dh_sp_edit = dh_sp.edit();

        dh_sp_edit.putString(currentTime.toString(), "Diagnosis Description");
        dh_sp_edit.commit();
    }


    public class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        //returns the size of messages
        public int getCount(){
            return messages.size();
        }
        //get the string at the position argument
        public String getItem(int position){
            return messages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            //Declare the layout inflater
            LayoutInflater inflater = ChatActivity.this.getLayoutInflater();

            //set result to null
            View result = null ;


            if(position%2 == 0) {
                //if the message is coming form the user setup incoming chat appropriately
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                TextView message = (TextView) result.findViewById(R.id.message_text);
                message.setText(   getItem(position)  );

            }else {
                //if the message is coming form the chat bot setup outgoing chat appropriately
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                TextView message = (TextView) result.findViewById(R.id.message_text);
                message.setText(   getItem(position)  );
            }

            return result;

        }
    }
}