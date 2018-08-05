package com.thomas.mirakle.dictionary;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class CustomAdaptor extends ArrayAdapter<Dict> {
    private Context mContext;
    TextToSpeech tts;
    private List<Dict> DictList; //= new ArrayList<>();
    public CustomAdaptor(@NonNull Context context, List<Dict> list) {
        super(context, 0,list);
        mContext = context;
        DictList = list;

        tts=new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.detail,parent,false);

        final Dict dict = DictList.get(position);

        TextView word_d = (TextView) listItem.findViewById(R.id.d_word);
        word_d.setText(dict.getWord());

        TextView word_type = (TextView) listItem.findViewById(R.id.d_type);
        word_type.setText(dict.getWord_type());

        TextView release = (TextView) listItem.findViewById(R.id.d_definition);
        release.setText(dict.word_definition);

        Button speech=(Button)listItem.findViewById(R.id.speech_btn);

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(dict.word,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        speech.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tts.speak(dict.word_definition,TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        return listItem;
    }
}
