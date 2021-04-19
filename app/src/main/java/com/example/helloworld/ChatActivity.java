package com.example.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import java.util.ArrayList;
import android.widget.BaseAdapter;
import android.widget.TextView;
import static com.example.helloworld.R.id.tv_receive;
import static com.example.helloworld.R.id.tv_send;

public class ChatActivity extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_chat, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ChatActivity.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}

public class TestActivity extends AppCompatActivity {


    private ArrayList<Msg> msgs;
    private EditText et_input;
    private MyAdapter myAdapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        et_input = (EditText) findViewById(R.id.et_input);

        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_input.getText().toString();
                if (!content.isEmpty()) {
                    msgs.add(new Msg(content, Msg.TYPE_SEND));
                    myAdapter.notifyDataSetChanged();
                    lv.setSelection(msgs.size() - 1);
                    et_input.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "请输入内容!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        msgs = new ArrayList<>();
        msgs.add(new Msg("hello", Msg.TYPE_RECEIVE));
        msgs.add(new Msg("who is that?", Msg.TYPE_SEND));
        msgs.add(new Msg("this is LiLei,nice to meet you!", Msg.TYPE_RECEIVE));

        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Msg getItem(int position) {
            return msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.listview_item, null);
                holder.tv_receive = (TextView) convertView.findViewById(tv_receive);
                holder.tv_send = (TextView) convertView.findViewById(tv_send);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Msg msg = getItem(position);
            if (msg.type == Msg.TYPE_RECEIVE) {
                holder.tv_receive.setVisibility(View.VISIBLE);
                holder.tv_send.setVisibility(View.GONE);
                holder.tv_receive.setText(msg.content);
            } else if (msg.type == Msg.TYPE_SEND) {
                holder.tv_send.setVisibility(View.VISIBLE);
                holder.tv_receive.setVisibility(View.GONE);
                holder.tv_send.setText(msg.content);
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_receive;
        TextView tv_send;
    }
}